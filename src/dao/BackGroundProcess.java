package dao;

import java.io.*;
import java.nio.charset.CoderResult;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import model.Product;
public class BackGroundProcess implements IBackGroundProccess{
    private static AtomicInteger currenSize=new AtomicInteger(0);
    @Override
    public void readFromFile() {

    }

    @Override
    public void writeToFile(Product product,List<Product> list, String transectionFile) {
        long start=System.nanoTime();
        for(int i=0;i<1000000;i++){
            list.add(new Product(10,"h",10.2,5.5, LocalDate.now()));
        }
        Thread thread1=new Thread(()->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data3.txt"))) {
                StringBuilder batch = new StringBuilder();
                int count = 0;
                int batchSize=10000;
                for (Product obj : list) {
                    currenSize.incrementAndGet();
                    batch.append(obj.getId()).append(",").append(obj.getName()).append(System.lineSeparator());
                    count++;
                    if (count == batchSize || obj.equals(list.get(list.size() - 1))) {
                        writer.write(batch.toString());
                        batch.setLength(0); // Clear the batch
                        count = 0; // Reset the counter
                    }
                }
                System.out.println("\nData written to file successfully.");
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });
        Thread thread2=new Thread(()->{
            loadingProgress(1);
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end=System.nanoTime();
        System.out.println("\ntime = "+(end-start)/1000000+"ms\n");
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
    public void loadingProgress(int totaSize) {
        System.out.println("Loading");
        int numberToRead=1000000;
        numberToRead=readTotalSize("totalSize.txt");
        String stDigit= Integer.toString(numberToRead);
        int digit=stDigit.length();
        int divi=(digit>3)?(int)Math.pow(10,digit-3):1;
        int remain =numberToRead%divi;
        while (currenSize.get() != numberToRead) {
            if (currenSize.get()  % divi == remain) {
                System.out.printf("\r[ %.2f%% ] %s", currenSize.get()  / (numberToRead / 100f), "#".repeat((int) (currenSize.get() / (numberToRead / 100f))));
                System.out.flush();
            }
        }
        System.out.printf("\r[ %.2f%% ] %s", 100f, "#".repeat(100));
    }
    private int readTotalSize(String fileName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("1000000");
        }catch (Exception e){

        }
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

}
