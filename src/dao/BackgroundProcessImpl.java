package dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import model.Product;
import views.Colors;

public class BackgroundProcessImpl implements BackgroundProcess{
    private static AtomicInteger currenSize=new AtomicInteger(0);
    private static AtomicInteger AtotalSize=new AtomicInteger(0);
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private  static BackgroundProcessImpl instance;
    @Override
    public void loadingProgress(int totalSize,String fileName,String status) throws IOException {
        System.out.println("Loading...");
        AtotalSize.set(totalSize);
        int numberToRead= status.equalsIgnoreCase("start")? (int) countLines(fileName) :AtotalSize.get();
        String stDigit= Integer.toString(numberToRead);
        int digit=stDigit.length();
        int divi=(digit>3)?(int)Math.pow(10,digit-3):1;
        int remain =numberToRead%divi;
        int repeatNumber=0;
        while (currenSize.get() != numberToRead) {
            if (currenSize.get()  % divi == remain) {
                repeatNumber=(int) (currenSize.get() / (numberToRead / 100f));
                System.out.printf(Colors.red()+"\r[ %d/%d ]", currenSize.get(),numberToRead);
                System.out.printf(" %s%s","\u001B[31m\u2588".repeat(repeatNumber),"\u001B[37m\u2592".repeat(100-repeatNumber));
                System.out.printf(Colors.red()+" [ %.2f%% ]", currenSize.get()  / (numberToRead / 100f));
                System.out.flush();
            }
        }
        System.out.printf(Colors.blue()+"\r[ %d/%d ] %s\u001B[34m [%.2f%% ]",currenSize.get(),numberToRead,"\u001B[35m\u2588".repeat(100), 100f);
    }

    @Override
    public void commit(List<Product> list, String tranSectionFile, String dataFile, Scanner input) throws FileNotFoundException {
        List<Product> listData=new ArrayList<>();
        readFromFile(listData,dataFile,"start");
        try(BufferedReader reader=new BufferedReader(new FileReader(tranSectionFile))){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void randomRead(List<Product> list, String fileName) {

    }

    public static String[] split(final String line, final char delimiter) {
        CharSequence[] temp = new CharSequence[(line.length() /  2) +  1];
        int wordCount =  0;
        int i =  0;
        int j = line.indexOf(delimiter,  0); // first substring

        while (j >=  0) {
            temp[wordCount++] = line.substring(i, j);
            i = j +  1;
            j = line.indexOf(delimiter, i); // rest of substrings
        }

        temp[wordCount++] = line.substring(i); // last substring

        String[] result = new String[wordCount];
        System.arraycopy(temp,  0, result,  0, wordCount);

        return result;
    }

    private void writeTotalSize(int totalSize,String fileName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(totalSize+"");
            writer.flush();
        }catch (Exception e){

        }
    }
    @Override
    public void readFromFile(List<Product> list, String dataFile,String status) {
        long start =System.nanoTime();
        Thread thread1=new Thread(()->{
            try (Stream<String> lines = Files.lines(Paths.get(dataFile))) {
                lines.forEach(line -> {
                    String[] parts = split(line,',');
                    list.add(new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), LocalDate.parse(parts[4], DATE_FORMATTER)));
                    currenSize.incrementAndGet();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread thread2=new Thread(()->{
            try {
                loadingProgress(list.size(),dataFile,status);
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
            System.out.println(Colors.blue()+"\nCompleted.");
        System.out.println(Colors.reset()+"\ntime = "+(end-start)/1000000+"ms\n");
        currenSize.set(0);
    }

    @Override
    public void writeToFile(Product product, List<Product> list,String status) {
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
        Thread thread2=new Thread(()->{
            try {
                loadingProgress(1,"","");
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
            System.out.println(Colors.blue()+"\nData written to file successfully.");
        System.out.println(Colors.reset()+"\ntime = "+(end-start)/1000000+"ms\n");
        currenSize.set(0);
    }
    @Override
    public void editToFile(Product product, List<Product> list,String status){
        long start = System.nanoTime();
        Thread thread1 = new Thread(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/allFile/TransectionFile.txt", false))) {
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
            System.out.println(Colors.blue() + "\nData written to file successfully.");
        System.out.println(Colors.reset() + "\ntime = " + (end - start) / 1000000 + "ms\n");
        currenSize.set(0);
    }
    @Override
    public boolean commitCheck(String fileTransection,Scanner input) throws IOException {
        Path path = Paths.get(fileTransection);
        if(Files.exists(path)&&Files.size(path)!=0){
            do {
                System.out.println("Do you want to commit[y/n]: ");
                String commit=input.nextLine();
                if(commit.equalsIgnoreCase("y")) return true;
                else if(commit.equalsIgnoreCase("n")) return false;
            }while (true);
        }
        return false;
    }

    @Override
    public void randomRead(List<Product> list, String fileName, Scanner input) {
        list.clear();
    }

    @Override
    public void randomWrite(String filename,Scanner input) {
        System.out.print("Enter number of file: ");
        int n=input.nextInt();
        writeTotalSize(n,"src/allFile/totalSize.txt");
        long start=System.nanoTime();
        BackgroundProcessImpl obj =  BackgroundProcessImpl.createObject();
        Thread thread1=new Thread(()->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                StringBuilder batch = new StringBuilder();
                int count = 0;
                int batchSize=1000;
                for (int i = 0; i < n; i++) {
                    currenSize.incrementAndGet();
                    batch.append(i)
                            .append(",")
                            .append("name")
                            .append(",")
                            .append(10.5)
                            .append(",")
                            .append(5.6)
                            .append(",")
                            .append("10/02/2002")
                            .append(System.lineSeparator());
                    count++;
                    if (count == batchSize || i==n-1) {
                        writer.write(batch.toString());
                        batch.setLength(0); // Clear the batch
                        count = 0; // Reset the counter
                    }
                }
                obj.writeTotalSize(n,"src/allFile/totalSize.txt");
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
        Thread thread2=new Thread(()->{
            try {
                obj.loadingProgress(n,"","");
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
        System.out.println(Colors.blue()+"\nData written to file successfully.");
        System.out.println(Colors.reset()+"\ntime = "+(end-start)/1000000+"ms\n");
        currenSize.set(0);
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
    public static int countLines(String filename,String status) {
        int numberToRead=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line=reader.readLine())!=null){
                numberToRead=Integer.parseInt(line);
            }
        } catch (IOException |OutOfMemoryError ignored) {

        }
        return numberToRead;
    }

    public static void main(String[] args) throws IOException {
        BackgroundProcessImpl obj=BackgroundProcessImpl.createObject();
        Scanner input =new Scanner(System.in);
        List<Product> list =new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        obj.randomWrite("src/allFile/RandomWriteTest.txt",input);
//        Product product=new Product(10,"hello",25.2,5.3,LocalDate.parse("10/02/2020",formatter));
//        obj.writeToFile(product,list,"");
        //obj.readFromFile(list,"src/allFile/RandomWriteTest.txt");
        //System.out.println( obj.commitCheck("src/allFile/TransectionFile.txt",input));
        long st=System.nanoTime();
        System.out.println(countLines("src/allFile/RandomWriteTest.txt"));
        long en=System.nanoTime();
        System.out.println((en-st)/1000000);
    }
}
