package dao;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import model.Product;
public class BackGroundProcess implements IBackGroundProccess{
    @Override
    public void readFromFile() {

    }

    @Override
    public void writeToFile(Product product,List<Product> list, String transectionFile) {
        Thread thread1=new Thread(()->{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data3.txt"))) {
                StringBuilder batch = new StringBuilder();
                int count = 0;
                int batchSize=10000;
                for (Product obj : list) {
                    batch.append(obj.getId()).append(",").append(obj.getName()).append(System.lineSeparator());
                    count++;
                    if (count == batchSize || obj.equals(list.get(list.size() - 1))) {
                        writer.write(batch.toString());
                        batch.setLength(0); // Clear the batch
                        count = 0; // Reset the counter
                    }
                }
                System.out.println("Data written to file successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread thread2=new Thread(()->{

        });
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
    public void loadingProgress(List<Product> list) {
        int numberToRead=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(""))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numberToRead=Integer.parseInt(reader.readLine());
            }
        } catch (IOException ignored) {
        }
        int l=list.size();
        System.out.println("Loading");
        String stDigit= Integer.toString(numberToRead);
        int digit=stDigit.length();
        int divi=(digit>3)?(int)Math.pow(10,digit-3):1;
        int remain =numberToRead%divi;
        int i = 0;
        while (l != numberToRead) {
            if ((l) % divi == remain) {
                System.out.printf("\r[ %.2f%% ] %s", l / (numberToRead / 100f), "#".repeat((int) (l / (numberToRead / 100f))));
                System.out.flush();
            }
        }
        System.out.printf("\r[ %.2f%% ] %s", 100f, "#".repeat(100));
    }
}
