package controller;

import dao.BackgroundProcessImpl;
import model.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BackgroundProcessController {
    private Scanner scanner;
    private BackgroundProcessImpl backgroundProcess;
    private List<Product> productslist;
    private Product product ;

    public BackgroundProcessController(){
        backgroundProcess = BackgroundProcessImpl.createObject();
        scanner = new Scanner(System.in);
        productslist = ProductController.products();
        product = new Product();
    }


    public void random() throws IOException {
        backgroundProcess.random(productslist,"src/allFile/dataFile.txt",scanner);
    }
    public void start() throws IOException {
        String op=null;
        if (backgroundProcess.commitCheck("src/allFile/TransectionFile.txt",scanner)){
           op= backgroundProcess.commit(productslist,"src/allFile/TransectionFile.txt","src/allFile/dataFile.txt","start",scanner);
        }
        if(op==null){
            backgroundProcess.readFromFile(productslist,"src/allFile/dataFile.txt","start");
        }
    }
    public void commit() throws IOException {
        if (backgroundProcess.commitCheck("src/allFile/TransectionFile.txt",scanner)){
            backgroundProcess.commit(productslist,"src/allFile/TransectionFile.txt","src/allFile/dataFile.txt","commit",scanner);
        }else
            System.out.println("There are nothing to commit..!");
        }

    public void restore(){
        backgroundProcess.restore(productslist,"src/allFile/dataFile.txt",scanner);
    }
}