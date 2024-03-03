package dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import model.Product;
import views.BoxBorder;
import dao.BackgroundProcess;

public class BackgroundProcessImpl implements BackgroundProcess , BoxBorder {

    private static AtomicInteger currenSize = new AtomicInteger(0);
    private static AtomicInteger AtotalSize = new AtomicInteger(0);
    private static BackgroundProcessImpl instance;
    private static Map<String, LocalDate> dateMap = new HashMap<>();
    private static Product product=new Product();
    public static Product product(){
        return product;
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
    public static LocalDate convertToDate(String dateString) {
        if (dateMap.containsKey(dateString)) {
            return dateMap.get(dateString);
        } else {
            LocalDate localDate = LocalDate.parse(dateString);
            dateMap.put(dateString, localDate);
            return localDate;
        }
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

    @Override
    public void loadingProgress(int totalSize, String fileName, String status) throws IOException {
        System.out.println("LOADING...");
        AtotalSize.set(totalSize);
        int numberToRead = status.equalsIgnoreCase("startcommit") ? (int) countLines(fileName) : AtotalSize.get();
        //progress only have data in list or file
        if(numberToRead>0){
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
            System.out.printf(blue + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]"+reset, currenSize.get(), numberToRead, "\u001B[35m\u2588".repeat(100), 100f);
        }
    }
    @Override
    public  void readFromFile(List<Product> list, String dataFile, String status) {
        try {
            Thread thread1 = new Thread(() -> {
                try (Stream<String> lines = Files.lines(Paths.get(dataFile))) {
                    lines.forEach(line -> {
                        String[] parts = split(line, ',');
                        list.add(new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), convertToDate(parts[4])));
                        currenSize.incrementAndGet();
                    });
                    if(list.isEmpty()){
                        System.out.println("❌NO DATA..!");
                    }
                } catch (IOException e) {
                    System.out.println("❌NO DATA..!");
                }
            });
            Thread thread2 = new Thread(() -> {
                try {
                    loadingProgress((int)countLines(dataFile), dataFile, status);
                } catch (IOException e) {
                    System.out.println("\uD83D\uDCDBDATA FILE NOT FOUND");
                }
            });
            thread1.start();
            if(Files.exists(Paths.get(dataFile)) && !dataFile.isEmpty()){
                if(!status.equalsIgnoreCase("commitYes")){
                    thread2.start();
                }
            }
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
            }
            if (currenSize.get() != -1)
                if(Files.exists(Paths.get(dataFile))&& !list.isEmpty()&&!status.equalsIgnoreCase("commitYes")){
                    System.out.println(blue + "\nCompleted." + reset);
                }else{
                    System.out.println("\n"+reset);
                }
            currenSize.set(0);
        }catch (OutOfMemoryError e){
            System.out.println("OUT OF MEMORY");
            clearFile(dataFile);
        }

    }
    @Override
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
    public void writeToFile(Product product, String status) {
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
                obj.writeSizeToFile(list.size(), "src/allFile/totalSize.txt");
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
    public void writeSizeToFile(int last, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(last + "");
            writer.flush();
        } catch (Exception e) {

        }
    }
    @Override
    public String commit(List<Product> productList, String tranSectionFile, String dataFile, String operation, Scanner input) throws IOException {
        String opera = operation.equalsIgnoreCase("random") ? "[Y/B/C]" : "[Y/N/C]";
        System.out.println(blue + "COMMIT ALL TO CHANGE [ Y ] "+reset);
        if (operation.equalsIgnoreCase("random")){
            System.out.println("BACK TO MENU         [ B ]");
        }else{
            System.out.println(darkYellow + "COMMIT LATER         [ N ]" + reset);
        }
        System.out.println(red +"CANCEL ALL CHANGE    [ C ]"+reset);
        String op = null;
        do {
            System.out.print("ARE YOU SURE TO COMMIT ? " + opera + " : ");
            op = input.nextLine().trim();
            if (operation.equalsIgnoreCase("random") && !op.equalsIgnoreCase("n")) continue;
        } while (!(op.equalsIgnoreCase("y") || op.equalsIgnoreCase("c") || (op.equalsIgnoreCase("n") && !operation.equalsIgnoreCase("random"))||(op.equalsIgnoreCase("b"))&&operation.equalsIgnoreCase("random")));

        if(op.equalsIgnoreCase("y")||(op.equalsIgnoreCase("n")&&operation.equalsIgnoreCase("startCommit"))){
            if(op.equalsIgnoreCase("y")) {
                productList.clear();
                readFromFile(productList,dataFile,"commitYes");
            }else{
                readFromFile(productList,dataFile,"commitNo");
            }
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
            if(!productList.isEmpty()){
                int lastId=productList.get(productList.size()-1).getId();
                product.setLastAssignedId( lastId);
                writeSizeToFile(lastId,"src/allFile/lastId.txt");
            }
        }else{
            System.out.println(darkYellow+"\uD83D\uDCE2COMMIT LATER"+reset);
        }
        return op;
    }
    @Override
    public boolean commitCheck(String fileTransection, Scanner input) throws IOException {
        Path path = Paths.get(fileTransection);
        if(Files.exists(path)&&Files.size(path)!=0){
            System.out.println(darkYellow+"\uD83D\uDCE2THERE ARE MANY RECORD HAVE CHANGE AND NOT COMMIT YET ...!!!! ");
            return true;
        }else return false;
    }
    @Override
    public void setListSize(int listSize) {
        AtotalSize.set(listSize);
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
                    System.out.println(i + ". " + relativePath);
                    storeFile.add(file.getPath());
                    last = i;
                    i++;
                }
            }
        }
        if (storeFile.isEmpty()) {
            System.out.println(" NO FILES AVAILABLE FOR RESTORATION");
            System.out.println(" 🏠 BACK TO APPLICATION MENU...");
        }
        while (true) {
            try {
                System.out.print("CHOICE FILE TO RESTORE (OR 'B' TO LEAVE) : ");
                String choice = scanner.nextLine().trim();
                if (choice.equalsIgnoreCase("b")) {
                    System.out.println("LEAVING FILE RESTORATION.");
                    return;
                }
                int option = Integer.parseInt(choice);
                if (option < start || option > last) {
                    throw new IndexOutOfBoundsException();
                }
                String filePath = storeFile.get(option - 1);
                System.out.println("FILE PATH : " + filePath.toUpperCase());
                System.out.print("ARE YOU SURE TO RESTORE FILE [Y/N] : ");
                String confirm = scanner.nextLine().toUpperCase();
                if (!confirm.equals("Y")) {
                    System.out.println( red + "   ❌ FILE RESTORATION CANCELED.\n\n" + reset);
                    return;
                }
                // Thread 1
                Thread thread1 = new Thread(() -> {
                    String lastLine=null;
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                         BufferedWriter writer = new BufferedWriter(new FileWriter(dataSource))) {

                        String line;
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();
                            lastLine = line;
                        }

                    } catch (IOException e) {
                        System.out.println(red + "   ❌ ERROR OCCURRED WHILE RESTORING FILE: " + e.getMessage().toUpperCase() + reset);
                    }
                    if(lastLine != null) {
                        String[] lastLineArr = split(lastLine, ',');
                        writeSizeToFile(Integer.parseInt(lastLineArr[0]), "src/allFile/lastId.txt");
                        product.setLastAssignedId(Integer.parseInt(lastLineArr[0]));
                    }
                });

                Thread thread2 = new Thread(() -> {
                    products.clear();
                    readFromFile(products, filePath, "START");
                    System.out.println(blue + "RESTORE THE FILE SUCCESSFULLY\n\n" + reset);
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
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println(red + "   ❌ INVALID INPUT. PLEASE ENTER A VALID NUMBER." + reset);
                scanner.nextLine(); // Consume the invalid input
            } catch (IndexOutOfBoundsException e) {
                System.out.println(red + "   ❌ CHOICE OUT OF BOUNDS. PLEASE ENTER A NUMBER WITHIN THE RANGE " + "[" + start + " TO " + last + "]" + reset);
            }
        }
    }

}
