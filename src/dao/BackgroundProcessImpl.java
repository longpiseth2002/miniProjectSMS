package dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import model.Product;
import views.BoxBorder;

public class BackgroundProcessImpl implements BackgroundProcess , BoxBorder {
    private static AtomicInteger currenSize=new AtomicInteger(0);
    private static AtomicInteger AtotalSize=new AtomicInteger(0);

    private static BackgroundProcessImpl instance;

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
                System.out.printf(red + "\r[ %d/%d ]", currenSize.get(), numberToRead);
                System.out.printf(" %s%s", "\u001B[31m\u2588".repeat(repeatNumber), "\u001B[37m\u2592".repeat(100 - repeatNumber));
                System.out.printf(red + " [ %.2f%% ]", currenSize.get() / (numberToRead / 100f));
                System.out.flush();
            }
        }
        System.out.printf(blue + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", currenSize.get(), numberToRead, "\u001B[35m\u2588".repeat(100), 100f);
    }

    @Override
    public void commit( String tranSectionFile, String dataFile, Scanner input) throws FileNotFoundException {
        List<Product> listData=new ArrayList<>();
        readFromFile(listData,dataFile,"start");
        int change=0;
        try(BufferedReader reader=new BufferedReader(new FileReader(tranSectionFile))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String status=parts[5];
                if(status.equalsIgnoreCase("write")){
                    listData.add(new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), LocalDate.parse(parts[4])));
                }
                else if(status.equalsIgnoreCase("delete")){
                    int idToDelete=Integer.parseInt(parts[0]);
                    listData.removeIf(product -> product.getId() == idToDelete);
                }
                else if(status.equalsIgnoreCase("update")){
                    int idToUpdate = Integer.parseInt(parts[0].trim());
                    for (Product product : listData) {
                        if (product.getId() == idToUpdate) {
                            product.setName(parts[1].trim());
                            product.setQty(Integer.parseInt(parts[2]));
                            product.setUnitPrice(Double.parseDouble(parts[3]));
                            break;
                        }
                    }
                }
                change++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("There are "+change+" record change");
        System.out.println("Commit all change: y");
        System.out.println(red +"Cancel all change: c");
        System.out.println(darkYellow +"Commit later     : n"+reset);
        String op=null;
        do {
            System.out.print("Are you sure to commit?[y/c/n]");
            op=input.nextLine();
        }while (!(op.equalsIgnoreCase("y")||op.equalsIgnoreCase("n")||op.equalsIgnoreCase("c")));
        if(op.equalsIgnoreCase("y")){
            writeToFile(listData,"src/allFile/dataFile.txt");
            listData.clear();
            clearFile(tranSectionFile);
        }else if(op.equalsIgnoreCase("n")){
            listData.clear();
            return;
        }else{
            clearFile(tranSectionFile);
            listData.clear();
        }
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

    @Override
    public void randomRead(List<Product> list, String fileName) {

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
    public void readFromFile(List<Product> list, String dataFile, String status) {
        long start = System.nanoTime();
        Thread thread1 = new Thread(() -> {
            try (Stream<String> lines = Files.lines(Paths.get(dataFile))) {
                lines.forEach(line -> {
                    String[] parts = split(line,',');
                    list.add(new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), LocalDate.parse(parts[4])));
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
            System.out.println(blue + "\nCompleted.");
        System.out.println(reset + "\ntime = " + (end - start) / 1000000 + "ms\n");
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
            System.out.println(blue +"\nData written to file successfully.");
        System.out.println(reset +"\ntime = "+(end-start)/1000000+"ms\n");
        currenSize.set(0);
    }

    @Override
    public void writeToFile(List<Product> list,String fileName){
        long start=System.nanoTime();
        BackgroundProcessImpl obj =  BackgroundProcessImpl.createObject();
        Thread thread1=new Thread(()->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                StringBuilder batch = new StringBuilder();
                int count = 0;
                int batchSize=1000;
                for (int i = 0; i < list.size(); i++) {
                    currenSize.incrementAndGet();
                    batch.append(list.get(i).getId())
                            .append(",")
                            .append(list.get(i).getName())
                            .append(",")
                            .append(list.get(i).getUnitPrice())
                            .append(",")
                            .append(list.get(i).getQty())
                            .append(",")
                            .append(list.get(i).getImportAt())
                            .append(System.lineSeparator());
                    count++;
                    if (count == batchSize || i==list.size()-1) {
                        writer.write(batch.toString());
                        batch.setLength(0); // Clear the batch
                        count = 0; // Reset the counter
                    }
                }
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
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }
        long end=System.nanoTime();
        if(currenSize.get()!=-1)
            System.out.println(blue + "\nData written to file successfully.");
        System.out.println(reset + "\ntime = "+(end-start)/1000000+"ms\n");
        currenSize.set(0);
    }
    @Override
    public boolean commitCheck(String fileTransection, Scanner input) throws IOException {
        Path path = Paths.get(fileTransection);
        if(Files.exists(path)&&Files.size(path)!=0){
            System.out.println("There are many record have change and not commit yet..!");
            do {
                System.out.print("Check and commit?[y/n]: ");
                String commit=input.nextLine();
                if(commit.equalsIgnoreCase("y")) return true;
                else if(commit.equalsIgnoreCase("n")) {
                    clearFile(fileTransection);
                    return false;
                }
            }while (true);
        }
        return false;
    }

    @Override
    public void randomRead(List<Product> list, String fileName, Scanner input) {
        list.clear();
    }

    @Override
    public void randomWrite(String filename, Scanner input) {
        int n = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("ENTER NUMBERS OF FILE : ");
                n = Integer.parseInt(input.nextLine()); // Read the whole line to handle non-integer inputs
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println(red + "   ❌  INVALID INPUT . PLEASE ENTER A VALID INTEGER !!!! " + reset);
            }
        }

        writeTotalSize(n, "src/allFile/totalSize.txt");

        long start = System.nanoTime();
        BackgroundProcessImpl obj = BackgroundProcessImpl.createObject();
        String date = String.valueOf(LocalDate.now());

        int finalN = n;
        Thread thread1 = new Thread(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (int i = 0; i < finalN; i++) {
                    currenSize.incrementAndGet();
                    String line = i + "," + "CSTAD" + "," + 10.5 + "," + 5 + "," + date + "\n";
                    writer.write(line);
                }
                obj.writeTotalSize(finalN, "src/allFile/totalSize.txt");
                System.out.println("\nDATA WRITTEN TO FILE SUCCESSFULLY ." + reset);
            } catch (IOException e) {
                System.out.println( red + "   ❌  ERROR WRITTEN DATA TO FILE : " + e.getMessage() + reset);
            }
        });

        int n1 = n;
        Thread thread2 = new Thread(() -> {
            try {
                obj.loadingProgress(n1, "", "");
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
            // Handle InterruptedException
            e.printStackTrace();
        }

        long end = System.nanoTime();
        System.out.println( blue + "TIME TAKEN :  " + (end - start) / 1000000 + "ms" + reset);
        currenSize.set(0);
        System.out.println("\n");
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
    public static long countLines(String filename) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return lines.count();
        }
    }

    public static int countLines(String filename, String status) {
        int numberToRead = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numberToRead = Integer.parseInt(line);
            }
        } catch (IOException | OutOfMemoryError ignored) {

        }
        return numberToRead;
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

                    String relativePath = file.getPath().replaceFirst("^src/backupfiles" , "");
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
                System.out.println( red + "   ❌ INVALID INPUT. PLEASE ENTER A VALID NUMBER." + reset);
                scanner.nextLine(); // Consume the invalid input
            } catch (IndexOutOfBoundsException e) {
                System.out.println(red + "   ❌ CHOICE OUT OF BOUNDS. PLEASE ENTER A NUMBER WITHIN THE RANGE " + "[" + start + " TO "+ last + "]" + reset);
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }}