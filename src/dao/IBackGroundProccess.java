package dao;

import model.Product;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public interface
IBackGroundProccess {
    public void readFromFile(List<Product> list,String datFile);
    public void writeToFile(List<Product> list,String transectionFile,String status);

    public boolean commitCheck(String fileTransection, String fileData, Scanner input);
    public void loadingProgress(int totalSize,String status);
    public void commit(List<Product> list,String tranSectionFile,String datFile);
    public void ramdomRead(String fileName,Scanner input);
    public void randomWrite(String filename,Scanner input);
    public void setListSize(int listSize);
}
