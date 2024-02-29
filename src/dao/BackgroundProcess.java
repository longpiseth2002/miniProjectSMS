package dao;

import model.Product;

import javax.security.sasl.SaslClient;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public interface BackgroundProcess {



    public void readFromFile(List<Product> list,String datFile,String status);
    public void writeToFile(Product product,String status);
    public void writeToFile(List<Product> list,String fileName);

    public boolean commitCheck(String fileTransection,  Scanner input) throws IOException;
    public void loadingProgress(int totalSize,String fileName,String status) throws IOException;
    public String commit(List<Product> productList,String tranSectionFile,String dataFile,String operation,Scanner input) throws IOException;

    public void random(List<Product> productList,String filename, Scanner input) throws IOException;
    public void setListSize(int listSize);
    public void restore (List<Product> products , String dataSource ,Scanner scanner);
    public Boolean clearFile(String filePath);
}
