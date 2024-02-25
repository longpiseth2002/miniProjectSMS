package dao;

import model.Product;

import java.util.List;
import java.util.Scanner;

public interface IBackGroundProccess {
    public void readFromFile();
    public void writeToFile(Product product,List<Product> list,String transectionFile);

    public boolean commitCheck(String fileTransection, String fileData, Scanner input);
    public void loadingProgress(int totalLine);
}
