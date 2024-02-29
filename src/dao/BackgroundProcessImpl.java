package dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import model.Product;
import views.Colors;

import static java.awt.Color.blue;
import static java.awt.Color.red;

public class BackgroundProcessImpl implements BackgroundProcess{
    private static AtomicInteger currenSize=new AtomicInteger(0);
    private static AtomicInteger AtotalSize=new AtomicInteger(0);

    private static BackgroundProcessImpl instance;
    private static Map<String, LocalDate> dateMap = new HashMap<>();

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
        System.out.println("Loading...");
        AtotalSize.set(totalSize);
        int numberToRead = status.equalsIgnoreCase("start") ? (int) countLines(fileName) : AtotalSize.get();
        String stDigit = Integer.toString(numberToRead);
        int digit = stDigit.length();
        int divi = (digit > 3) ? (int) Math.pow(10, digit - 3) : 1;
        int remain = numberToRead % divi;
        int repeatNumber = 0;
        while (currenSize.get() != numberToRead) {
            if (currenSize.get() % divi == remain) {
                repeatNumber = (int) (currenSize.get() / (numberToRead / 100f));
                System.out.printf(Colors.red() + "\r[ %d/%d ]", currenSize.get(), numberToRead);
                System.out.printf(" %s%s", "\u001B[31m\u2588".repeat(repeatNumber), "\u001B[37m\u2592".repeat(100 - repeatNumber));
                System.out.printf(Colors.red() + " [ %.2f%% ]", currenSize.get() / (numberToRead / 100f));
                System.out.flush();
            }
        }
        System.out.printf(Colors.blue() + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", currenSize.get(), numberToRead, "\u001B[35m\u2588".repeat(100), 100f);
    }

    @Override
    public String commit(List<Product> productList, String tranSectionFile, String dataFile,String operation, Scanner input) throws IOException {
        String opera=operation.equalsIgnoreCase("random")?"[y/c]":"[y/c/n]";
        System.out.println("Commit all change: y");
        System.out.println(Colors.red()+"Cancel all change: c"+Colors.reset());
        if(!operation.equalsIgnoreCase("random"))
            System.out.println(Colors.darkYellow()+"Commit later     : n"+Colors.reset());

        String op=null;
        do {
            System.out.print("Are you sure to commit?"+opera+": ");
            op=input.nextLine().trim();
            if(operation.equalsIgnoreCase("random")&&!op.equalsIgnoreCase("n")) continue;
        }while (!(op.equalsIgnoreCase("y")||op.equalsIgnoreCase("c")||(op.equalsIgnoreCase("n")&&!operation.equalsIgnoreCase("random"))));

        //List<Product> listData=new ArrayList<>();

            if(op.equalsIgnoreCase("y")){
                productList.clear();
                readFromFile(productList,dataFile,"start");
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
                        else if(status.equalsIgnoreCase("update")){
                            int idToUpdate = Integer.parseInt(parts[0].trim());
                            for (Product product : productList) {
                                if (product.getId() == idToUpdate) {
                                    product.setName(parts[1].trim());
                                    product.setQty(Integer.parseInt(parts[2]));
                                    product.setUnitPrice(Double.parseDouble(parts[3]));
                                    break;
                                }
                            }
                        }
                    }
                    clearFile(tranSectionFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                    writeToFile(productList,"src/allFile/dataFile.txt");
            }else if(op.equalsIgnoreCase("c")){
                    clearFile(tranSectionFile);
                    productList.clear();
                    readFromFile(productList,"src/allFile/dataFile.txt",operation);

            }else{
                if(operation.equalsIgnoreCase("start")){
                    readFromFile(productList,dataFile,"start");
                }
            }

        return op;
    }
    @Override
    public Boolean clearFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
            System.out.println("File cleared successfully.");
        } catch (IOException e) {
            //e.printStackTrace();
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
            j = line.indexOf(delimiter, i); // rest of substrings
        }

        temp[wordCount++] = line.substring(i); // last substring

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

    @Override
    public  void readFromFile(List<Product> list, String dataFile, String status) {
        long start = System.nanoTime();
        Thread thread1 = new Thread(() -> {
            try (Stream<String> lines = Files.lines(Paths.get(dataFile))) {
                lines.parallel().forEach(line -> {
                    String[] parts = split(line,',');
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

        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }

        long end = System.nanoTime();
        if (currenSize.get() != -1)
            System.out.println(Colors.blue() + "\nCompleted.");
        System.out.println(Colors.reset() + "\ntime = " + (end - start) / 1000000 + "ms\n");
        currenSize.set(0);
    }

    @Override
    public void writeToFile(Product product,String status) {
        long start=System.nanoTime();
        Thread thread1=new Thread(()->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/allFile/TransectionFile.txt"))) {
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
        long end=System.nanoTime();
        if(currenSize.get()!=-1)
            //System.out.println(Colors.blue()+"\nData written to file successfully.");
        System.out.println(Colors.reset()+"\ntime = "+(end-start)/1000000+"ms\n");
        currenSize.set(0);
    }

    @Override
    public void writeToFile(List<Product> list,String fileName){
        long start=System.nanoTime();
        BackgroundProcessImpl obj =  BackgroundProcessImpl.createObject();
        Thread thread1=new Thread(()->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (int i = 0; i < list.size(); i++) {
                    String line = list.get(i).getId() + "," + list.get(i).getName() + "," + list.get(i).getUnitPrice() + "," + list.get(i).getQty() + "," +list.get(i).getImportAt()+ "\n";
                    writer.write(line);
                }
//                StringBuilder batch = new StringBuilder();
//                int count = 0;
//                int batchSize=1000;
//                for (int i = 0; i < list.size(); i++) {
//                    currenSize.incrementAndGet();
//                    batch.append(list.get(i).getId())
//                            .append(",")
//                            .append(list.get(i).getName())
//                            .append(",")
//                            .append(list.get(i).getUnitPrice())
//                            .append(",")
//                            .append(list.get(i).getQty())
//                            .append(",")
//                            .append(list.get(i).getImportAt())
//                            .append(System.lineSeparator());
//                    count++;
//                    if (count == batchSize || i==list.size()-1) {
//                        writer.write(batch.toString());
//                        batch.setLength(0); // Clear the batch
//                        count = 0; // Reset the counter
//                    }
//                }
                obj.writeTotalSize(list.size(),"src/allFile/totalSize.txt");
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
        Thread thread2=new Thread(()->{
            try {
                loadingProgress(list.size(),"","");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        //thread2.start();
        try {
            thread1.join();
            //thread2.join();
        } catch (InterruptedException e) {
        }
        long end=System.nanoTime();
        if(currenSize.get()!=-1)
            System.out.println(Colors.blue()+"\nData written to file successfully.");
        System.out.println(Colors.reset()+"\ntime = "+(end-start)/1000000+"ms\n");
        currenSize.set(0);
    }
    @Override
    public boolean commitCheck(String fileTransection, Scanner input) throws IOException {
        Path path = Paths.get(fileTransection);
        if(Files.exists(path)&&Files.size(path)!=0){
            System.out.println("There are many record have change and not commit yet..!");
            return true;
//            do {
//                System.out.print("Check and commit?[y/n]: ");
//                String commit=input.nextLine();
//                if(commit.equalsIgnoreCase("y")) return true;
//                else if(commit.equalsIgnoreCase("n")) {
//                    clearFile(fileTransection);
//                    return false;
//                }
//            }while (true);
        }else return false;
    }


    @Override
    public void random(List<Product> productslist,String filename, Scanner input) throws IOException {
        if (commitCheck("src/allFile/TransectionFile.txt",input)){
            commit(productslist,"src/allFile/TransectionFile.txt","src/allFile/dataFile.txt","random",input);
        }
        System.out.println("1.Write");
        System.out.println("2.Read");
        System.out.print("Choose option: ");

        int n=0;
        do {
            try{
            System.out.print("Enter number of file[1-30M]: ");
            n = input.nextInt();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (n <1 || n > 30000000);
        String op = null;
        boolean apov=false;
        input.nextLine();
        do {
            try{
                System.out.println("(A):Append  ||  (O): Override");

                System.out.print("Enter option: ");
                op=input.nextLine();
                apov= op.equalsIgnoreCase("a");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (!(op.equalsIgnoreCase("a")||op.equalsIgnoreCase("o")));
        long start = System.nanoTime();
        String stDigit = Integer.toString(n);
        int digit = stDigit.length();
        int divi = (digit > 3) ? (int) Math.pow(10, digit - 3) : 1;
        int remain = n % divi;
        int repeatNumber ;
        String date= String.valueOf(LocalDate.now());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (int i = 0; i < n; i++) {
                    String line = i + "," + "CSTAD" + "," + 10.5 + "," + 5 + "," + date + "\n";
                    writer.write(line);
                    if (i % divi == remain) {
                        repeatNumber = (int) (i / (n/ 100f));
                        System.out.printf( "\r\u001B[31m[ %d/%d ] %s%s[ %.2f%% ]", i, n,"█".repeat(repeatNumber),"\u001B[37m▒".repeat(100 - repeatNumber), i / (n / 100f));
                     }
                }
                System.out.printf(Colors.blue() + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", n,n, "\u001B[35m█".repeat(100), 100f);
                //obj.writeTotalSize(n, "src/allFile/totalSize.txt");
            } catch (IOException e) {
            System.out.println(e.getMessage());
            }
        long end = System.nanoTime();
        System.out.println(Colors.blue() + "\nData written to file successfully.");
        System.out.println(Colors.reset() + "\ntime = " + (end - start) / 1000000 + "ms\n");
    }

    @Override
    public void setListSize(int listSize) {
        AtotalSize.set(listSize);
    }


    private  BackgroundProcessImpl(){};
    public static BackgroundProcessImpl createObject(){
        if(instance==null){
            return new BackgroundProcessImpl();
        }
        return instance;
    }
    public long countLines(String filename) throws IOException {
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
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String relativePath = file.getPath().replaceFirst("^src/backupfiles" , "");
                    System.out.println(i + ". " + relativePath);
                    storeFile.add(file.getPath());
                    i++;
                }
            }
        }
        System.out.print("Choice file to restore: ");
        int option = scanner.nextInt();
        String filePath = storeFile.get(option - 1);
        System.out.println("Selected file path : " + filePath);
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
                e.printStackTrace();
            }
        });


        Thread thread2 = new Thread(() -> {
            products.clear();
            readFromFile(products,filePath,"start");
            System.out.println("  Successfully");
        });
        thread1.start();
        thread2.start();


        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}