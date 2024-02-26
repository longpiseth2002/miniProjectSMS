package dao;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import model.Product;
import views.Colors;

public class BackgroundProcessImpl implements BackgroundProcess{
    private static AtomicInteger currenSize=new AtomicInteger(0);
    private static AtomicInteger AtotalSize=new AtomicInteger(0);
    private  static BackgroundProcessImpl instance;
    @Override
    public void loadingProgress(int totalSize,String status) {
        System.out.println("Loading...");
        AtotalSize.set(totalSize);
        int numberToRead=status.equalsIgnoreCase("start")?readTotalSize("totalSize.txt"):AtotalSize.get();
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
    private int readTotalSize(String fileName){
        int numberToRead=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line=reader.readLine())!=null){
                numberToRead=Integer.parseInt(line);
            }
        } catch (IOException |OutOfMemoryError ignored) {

        }
        return numberToRead;
    }
    private void writeTotalSize(int totalSize,String fileName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(totalSize+"");
            writer.flush();
        }catch (Exception e){

        }
    }
    @Override
    public void readFromFile(List<Product> list, String datFile) {

    }

    @Override
    public void writeToFile(List<Product> list,String status) {
        long start=System.nanoTime();
        Thread thread1=new Thread(()->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/allFile/Transection.txt"))) {
                StringBuilder batch = new StringBuilder();
                int count = 0;
                int batchSize=10000;
                for (Product obj : list) {
                    currenSize.incrementAndGet();
                    batch.append(obj.getId())
                            .append(",")
                            .append(obj.getName())
                            .append(",")
                            .append(obj.getUnitPrice())
                            .append(",")
                            .append(obj.getQty())
                            .append(",")
                            .append(obj.getImportAt())
                            .append(",")
                            .append(status)
                            .append(System.lineSeparator());
                    count++;
                    if (count == batchSize || obj.equals(list.get(list.size()-1))) {
                        writer.write(batch.toString());
                        batch.setLength(0);
                        count = 0;
                    }
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
        Thread thread2=new Thread(()->{
            loadingProgress(list.size(),"");
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
    public boolean commitCheck(String fileTransection, String fileData, Scanner input) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileTransection))) {
            if(reader.readLine()!=null){
                do {
                    System.out.println("Do you want to commit[y/n]: ");
                    String commit=input.nextLine();
                    if(commit.equalsIgnoreCase("y")) return true;
                    else if(commit.equalsIgnoreCase("n")) return false;
                }while (true);
            }
        } catch (IOException ignored) {
        }
        return false;
    }

    @Override
    public void commit(List<Product> list, String tranSectionFile, String datFile) {

    }

    @Override
    public void ramdomRead(String fileName,Scanner input) {

    }

    @Override
    public void randomWrite(String filename,Scanner input) {
        System.out.print("Enter number of file: ");
        int n=input.nextInt();
        writeTotalSize(n,"totalSize.txt");
        long start=System.nanoTime();
        BackgroundProcessImpl obj =  BackgroundProcessImpl.CreateObject();
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
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
        Thread thread2=new Thread(()->{
            obj.loadingProgress(n,"");
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
    public static BackgroundProcessImpl CreateObject(){
        if(instance==null){
            return new BackgroundProcessImpl();
        }
        return instance;
    }
}
