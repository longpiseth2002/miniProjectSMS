package controller;

import dao.BackgroundProcessImpl;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BackgroundProcessController {
    private Scanner scanner;
    private BackgroundProcessImpl backgroundProcess;
    private List<Product> products;
    private Product product ;

    public BackgroundProcessController(){
        backgroundProcess = BackgroundProcessImpl.createObject();
        scanner = new Scanner(System.in);
        products = new ArrayList<>();
        product = new Product();
    }


    public void writeToFile(){
        backgroundProcess.writeToFile(products,"");
    }

    public void randomWrite(){
        backgroundProcess.randomWrite("test.txt",scanner);
    }
}
