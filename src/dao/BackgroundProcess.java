package dao;

import model.Product;

import java.util.List;
import java.util.Scanner;

public interface BackgroundProcess {
    public void readFromFile(List<Product> list,String datFile);
    public void writeToFile(Product product,List<Product> list);

    public boolean commitCheck(String fileTransection, String fileData, Scanner input);
    public void loadingProgress(int totalLine);
    public void commit(List<Product> list,String tranSectionFile,String datFile);
}
