package controller;

import dao.BackgroundProcessImpl;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class BackgroundProcessController {
    private BackgroundProcessImpl backgroundProcess;
    private List<Product> products;
    private Product product ;

    public BackgroundProcessController(){
        backgroundProcess = new BackgroundProcessImpl();
        products = new ArrayList<>();
        product = new Product();
    }


    public void writeToFile(){
        backgroundProcess.writeToFile(product,products);
    }
}
