package dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleToIntFunction;
import java.util.stream.Stream;

import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import views.BoxBorder;

import static views.BoxBorder.reset;

public class BackgroundProcessImpl implements BackgroundProcess, BoxBorder {
    private static AtomicInteger currenSize = new AtomicInteger(0);
    private static AtomicInteger AtotalSize = new AtomicInteger(0);
    private static BackgroundProcessImpl instance;
    private static Map<String, LocalDate> dateMap = new HashMap<>();
    private static Product product=new Product();

    public static LocalDate convertToDate(String dateString) {
        if (dateMap.containsKey(dateString)) {
            return dateMap.get(dateString);
        } else {
            LocalDate localDate = LocalDate.parse(dateString);
            dateMap.put(dateString, localDate);
            return localDate;
        }
    }

    @Override
    public void loadingProgress(int totalSize, String fileName, String status) throws IOException {
        System.out.println("LOADING...");
        AtotalSize.set(totalSize);
        int numberToRead = status.equalsIgnoreCase("START") ? (int) countLines(fileName) : AtotalSize.get();
        String stDigit = Integer.toString(numberToRead);
        int digit = stDigit.length();
        int divi = (digit > 3) ? (int) Math.pow(10, digit - 3) : 1;
        int remain = numberToRead % divi;
        int repeatNumber = 0;
        while (currenSize.get() != numberToRead) {
            if (currenSize.get() % divi == remain) {
                repeatNumber = (int) (currenSize.get() / (numberToRead / 100f));
                System.out.printf(red + "\r[ %d/%d ]", currenSize.get(), numberToRead);
                System.out.printf(" %s%s", "\u001B[31m\u2588".repeat(repeatNumber), "\u001B[37m\u2592".repeat(100 - repeatNumber));
                System.out.printf(red + " [ %.2f%% ]", currenSize.get() / (numberToRead / 100f));
                System.out.flush();
            }
        }
        System.out.printf(blue + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", currenSize.get(), numberToRead, "\u001B[35m\u2588".repeat(100), 100f);
    }

    @Override
    public String commit(List<Product> productList, String tranSectionFile, String dataFile, String operation, Scanner input) throws IOException {
        String opera = operation.equalsIgnoreCase("random") ? "[y/c]" : "[y/c/n]";
        System.out.println(blue + "Commit all change: y");
        System.out.println(red + "Cancel all change: c");
        if (!operation.equalsIgnoreCase("random"))
            System.out.println(darkYellow + "Commit later     : n" + reset);

        String op = null;
        do {
            System.out.print("Are you sure to commit?" + opera + ": ");
            op = input.nextLine().trim();
            System.out.println("\n");
            if (operation.equalsIgnoreCase("random") && !op.equalsIgnoreCase("n")) continue;
        } while (!(op.equalsIgnoreCase("y") || op.equalsIgnoreCase("c") || (op.equalsIgnoreCase("n") && !operation.equalsIgnoreCase("random"))));

        if(op.equalsIgnoreCase("y")||(op.equalsIgnoreCase("n")&&operation.equalsIgnoreCase("start"))){
            if(op.equalsIgnoreCase("y")) productList.clear();
            readFromFile(productList,dataFile,"startcommit");
            try(BufferedReader reader=new BufferedReader(new FileReader(tranSectionFile))){
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String status=parts[5];
                    if(status.equalsIgnoreCase("write")){
                        productList.add(new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), LocalDate.parse(parts[4])));
                    }
                    else if(status.equalsIgnoreCase("delete")){
                        int idToDelete=Integer.parseInt(parts[0]);
                        productList.removeIf(product -> product.getId() == idToDelete);
                    }
                    else if(status.equalsIgnoreCase("edit")){
                        int idToUpdate = Integer.parseInt(parts[0].trim());
                        for (Product product : productList) {
                            if (product.getId() == idToUpdate) {
                                product.setName(parts[1].trim());
                                product.setUnitPrice(Double.parseDouble(parts[2]));
                                product.setQty(Integer.parseInt(parts[3]));
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(op.equalsIgnoreCase("y")) {
                clearFile(tranSectionFile);
                writeToFile(productList, "src/allFile/dataFile.txt");
            }
        }else if(op.equalsIgnoreCase("c")){
            clearFile(tranSectionFile);
            productList.clear();
            readFromFile(productList,"src/allFile/dataFile.txt",operation);
        }
        return op;
    }

    @Override
    public Boolean clearFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static String[] split(final String line, final char delimiter) {
        CharSequence[] temp = new CharSequence[(line.length() / 2) + 1];
        int wordCount = 0;
        int i = 0;
        int j = line.indexOf(delimiter, 0); // first substring

        while (j >= 0) {
            temp[wordCount++] = line.substring(i, j);
            i = j + 1;
            j = line.indexOf(delimiter, i);
        }

        temp[wordCount++] = line.substring(i);

        String[] result = new String[wordCount];
        System.arraycopy(temp, 0, result, 0, wordCount);

        return result;
    }

    private void writeTotalSize(int totalSize, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(totalSize + "");
            writer.flush();
        } catch (Exception e) {

        }
    }
 public void writeIdToFile(int last, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(last + "");
            writer.flush();
        } catch (Exception e) {

        }
    }
    public int readFromFile(String fileName) throws FileNotFoundException {
        String lastLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (lastLine != null) {
            return Integer.parseInt(lastLine);
        } else {
            throw new IllegalStateException("File is empty");
        }
    }

    @Override
    public  void readFromFile(List<Product> list, String dataFile, String status) {
        Thread thread1 = new Thread(() -> {
            try (Stream<String> lines = Files.lines(Paths.get(dataFile))) {
                lines.forEach(line -> {
                    String[] parts = split(line, ',');
                    list.add(new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), convertToDate(parts[4])));
                    currenSize.incrementAndGet();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                loadingProgress(list.size(), dataFile, status);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        if(!status.equalsIgnoreCase("startcommit"))  thread2.start();
        try {
            thread1.join();
            if(!status.equalsIgnoreCase("startcommit"))
                thread2.join();
        } catch (InterruptedException e) {
        }
        if (currenSize.get() != -1)
            if(!status.equalsIgnoreCase("startcommit"))
                System.out.println(blue + "\nCompleted." + reset);
        currenSize.set(0);
    }

    @Override
    public void writeToFile(Product product, String status) {
        long start = System.nanoTime();
        Thread thread1 = new Thread(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/allFile/TransectionFile.txt",true))) {
                StringBuilder batch = new StringBuilder();
                currenSize.incrementAndGet();
                batch.append(product.getId())
                        .append(",")
                        .append(product.getName())
                        .append(",")
                        .append(product.getUnitPrice())
                        .append(",")
                        .append(product.getQty())
                        .append(",")
                        .append(product.getImportAt())
                        .append(",")
                        .append(status)
                        .append(System.lineSeparator());
                writer.write(batch.toString());
            } catch (IOException e) {
                e.printStackTrace();
                currenSize.set(-1);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                loadingProgress(1, "", "");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }
        long end = System.nanoTime();
        if (currenSize.get() != -1)
            System.out.println(blue + "\nDATA WRITTEN TO FILE SUCCESSFULLY.");
        System.out.println(reset + "\nTIME = " + (end - start) / 1000000 + "MS\n");
        currenSize.set(0);
    }

    @Override
    public void writeToFile(List<Product> list,String fileName){
        BackgroundProcessImpl obj =  BackgroundProcessImpl.createObject();
        Thread thread1=new Thread(()->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (int i = 0; i < list.size(); i++) {
                    String line = list.get(i).getId() + "," + list.get(i).getName() + "," + list.get(i).getUnitPrice() + "," + list.get(i).getQty() + "," + list.get(i).getImportAt() + "\n";
                    writer.write(line);
                    currenSize.getAndIncrement();
                }
                obj.writeTotalSize(list.size(), "src/allFile/totalSize.txt");
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                loadingProgress(list.size(), "", "");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }
        long end=System.nanoTime();
        if(currenSize.get()!=-1)
            System.out.println(blue+"\nCompleted." + reset);
        currenSize.set(0);
    }

    @Override
    public boolean commitCheck(String fileTransection, Scanner input) throws IOException {
        Path path = Paths.get(fileTransection);
        if(Files.exists(path)&&Files.size(path)!=0){
            System.out.println("There are many record have change and not commit yet..!");
            return true;
        }else return false;
    }

    @Override
    public void random(List<Product> productslist, String filename, Scanner input) throws IOException {
        Table table = new Table(3, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.SURROUND);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0,20,25);
        table.setColumnWidth(1,20,25);
        table.setColumnWidth(2,20,25);
        table.addCell(cyan + "W.Write",cellStyle);
        table.addCell(cyan + "R.Read",cellStyle);
        table.addCell(cyan + "B.Back" + reset,cellStyle);

        if (commitCheck("src/allFile/TransectionFile.txt", input)) {
            commit(productslist, "src/allFile/TransectionFile.txt", "src/allFile/dataFile.txt", "random", input);
        }

        outloop:
        do {
            String wrOption = null;
            do {
                System.out.println(table.render());
                System.out.print("Choose option: ");
                wrOption = input.nextLine();
            } while (!(wrOption.equalsIgnoreCase("w") || wrOption.equalsIgnoreCase("r") || wrOption.equalsIgnoreCase("b")));

            if (wrOption.equalsIgnoreCase("b")) {
                System.out.println(" 🏠 BACK TO APPLICATION MENU...\n\n");
                break outloop;
            }

            if (wrOption.equalsIgnoreCase("w")) {
                String lastId=null;
                String op = null;
                int n = 0;
                do {
                    try {
                        System.out.print("Enter number of file[1-30M]: ");
                        n = input.nextInt();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (n < 1 || n > 30000000);
                boolean apov = false;
                String stDigit = Integer.toString(n);
                int digit = stDigit.length();
                int divi = (digit > 3) ? (int) Math.pow(10, digit - 3) : 1;
                int remain = n % divi;
                int repeatNumber;
                input.nextLine();
                do {
                    System.out.println("(A):Append  ||  (O): Override");
                    System.out.print("Enter option: ");
                    op = input.nextLine();
                    apov = op.equalsIgnoreCase("a");
                } while (!(op.equalsIgnoreCase("a") || op.equalsIgnoreCase("o")));
                String wrirteCheck = null;
                Table table1 = new Table(1,BorderStyle.UNICODE_BOX_DOUBLE_BORDER,ShownBorders.SURROUND);
                table1.setColumnWidth(0,30,35);
                table1.addCell(cyan + "  S.Start writing");
                table1.addCell(cyan + "  B.Back" + reset);

                do {
                    System.out.println(table1.render());
                    System.out.print("Choose option: ");
                    wrirteCheck = input.nextLine();
                } while (!(wrirteCheck.equalsIgnoreCase("s") || wrirteCheck.equalsIgnoreCase("b")));
                if (wrirteCheck.equalsIgnoreCase("s")) {
                    long start = System.nanoTime();
                    Product obj;
                    obj = new Product("coca", 2.5, 12);
                    int first = (apov)?readFromFile("src/allFile/lastId.txt")+1:1;
                    String line = "," + obj.getName() + "," + obj.getUnitPrice() + "," + obj.getQty() + "," + obj.getImportAt() + "\n";
                    String date = String.valueOf(LocalDate.now());
                    int id=0;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, apov))) {
                        for (int i = 0; i < n; i++) {
                            id=first+i;
                            writer.write(id + line);
                            if (i % divi == remain) {
                                repeatNumber = (int) (i / (n / 100f));
                                System.out.printf("\r\u001B[31m[ %d/%d ] %s%s[ %.2f%% ]", i, n, "█".repeat(repeatNumber), "\u001B[37m▒".repeat(100 - repeatNumber), i / (n / 100f));
                            }
                        }
                        System.out.printf(blue + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", n, n, "\u001B[35m█".repeat(100), 100f);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    long end = System.nanoTime();
                    System.out.println(blue + "\nData written to file successfully.");
                    System.out.println(reset + "\ntime = " + (end - start) / 1000000 + "ms\n");
                    if(apov) {
                        product.setLastAssignedId(id);
                        writeIdToFile(id,"src/allFile/lastId.txt");
                    }else{
                        product.setLastAssignedId(n);
                        writeIdToFile(n,"src/allFile/lastId.txt");
                    }
                } else {
                    continue outloop;
                }

            } else {
                int n = (int) countLines(filename);
                String stDigit = Integer.toString(n);
                int digit = stDigit.length();
                int divi = (digit > 3) ? (int) Math.pow(10, digit - 3) : 1;
                int remain = n % divi;
                AtomicInteger repeatNumber = new AtomicInteger();
                Table table1 = new Table(1,BorderStyle.UNICODE_BOX_DOUBLE_BORDER,ShownBorders.SURROUND);
                table1.setColumnWidth(0,30,35);
                table1.addCell(cyan + "  S.Start reading");
                table1.addCell(cyan + "  B.Back" + reset);

                String startAndBack = null;
                do {
                    System.out.println(table1.render());
                    System.out.print("Choose option: ");
                    wrOption = input.nextLine();
                } while (!(wrOption.equalsIgnoreCase("s") || wrOption.equalsIgnoreCase("b")));
                if (wrOption.equalsIgnoreCase("s")) {
                    long start = System.nanoTime();
                    productslist.clear();
                    AtomicInteger i = new AtomicInteger();
                    try (Stream<String> lines = Files.lines(Paths.get(filename))) {
                        lines.parallel().forEach(line -> {
                            String[] parts = split(line, ',');
                            productslist.add(new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), convertToDate(parts[4])));
                            i.getAndIncrement();
                            if (i.get() % divi == remain) {
                                int progress = (i.get() >= n) ? 100 : (i.get() <= 0) ? 0 : (int) (i.get() / (n / 100f));
                                repeatNumber.set(progress);
                                System.out.printf("\r\u001B[31m[ %d/%d ] %s%s[ %.2f%% ]", i.get(), n, "█".repeat(repeatNumber.get()), "\u001B[37m▒".repeat(100 - repeatNumber.get()), i.get() / (n / 100f));
                            }
                        });

                        System.out.printf(blue + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", n, n, "\u001B[35m█".repeat(100), 100f);
                        System.out.println(reset);
                    }
                    long end = System.nanoTime();
                    System.out.println(blue + "\nComplete.");
                    System.out.println( "time = " + (end - start) / 1000000 + "ms\n" + reset);
                } else {
                    continue outloop;
                }
            }
        } while (true);
    }

    @Override
    public void setListSize(int listSize) {
        AtotalSize.set(listSize);
    }

    public BackgroundProcessImpl(){};
    public static BackgroundProcessImpl createObject(){
        if(instance==null){
            return new BackgroundProcessImpl();
        }
        return instance;
    }

    public static long countLines(String filename) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return lines.count();
        }
    }


    @Override
    public void restore(List<Product> products, String dataSource, Scanner scanner) {
        List<String> storeFile = new ArrayList<>();
        // the directory of the package
        File packageDir = new File("src/backupfiles");
        File[] files = packageDir.listFiles();
        int i = 1;
        int start = 1;
        int last = 0;
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String relativePath = file.getPath().replaceFirst("^src/backupfiles", "");
                    System.out.println(i + ". " + relativePath.toUpperCase());
                    storeFile.add(file.getPath());
                    last = i;
                    i++;

                }
            }
        }
        while (true) {
            try {
                System.out.print("CHOICE FILE TO RESTORE: ");
                int option = scanner.nextInt();
                String filePath = storeFile.get(option - 1);
                System.out.println("SELECTED FILE PATH : " + filePath.toUpperCase());
                // Thread 1
                Thread thread1 = new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                         BufferedWriter writer = new BufferedWriter(new FileWriter(dataSource))) {

                        String line;
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();
                        }

                    } catch (IOException e) {
                        System.out.println("ERROR OCCURRED WHILE RESTORING FILE: " + e.getMessage().toUpperCase());
                    }
                });

                Thread thread2 = new Thread(() -> {
                    products.clear();
                    readFromFile(products, filePath, "START");
                    System.out.println("  SUCCESSFULLY");
                });
                thread1.start();
                thread2.start();

                try {
                    thread1.join();
                    thread2.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(red + "   ❌ INVALID INPUT. PLEASE ENTER A VALID NUMBER." + reset);
                scanner.nextLine(); // Consume the invalid input
            } catch (IndexOutOfBoundsException e) {
                System.out.println(red + "   ❌ CHOICE OUT OF BOUNDS. PLEASE ENTER A NUMBER WITHIN THE RANGE " + "[" + start + " TO " + last + "]" + reset);
                scanner.nextLine(); // Consume the invalid input
            }
        }

    }


}
