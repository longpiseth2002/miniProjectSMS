package controller;

import dao.BackgroundProcessImpl;
import dao.Random;
import model.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import views.BoxBorder;

import static views.BoxBorder.*;

public class BackgroundProcessController {
    private Scanner scanner;
    private BackgroundProcessImpl backgroundProcess;
    private List<Product> productslist;
    private Product product ;
    private Random random;

    public BackgroundProcessController(){
        backgroundProcess = BackgroundProcessImpl.createObject();
        scanner = new Scanner(System.in);
        productslist = ProductController.products();
        product = new Product();
        random=Random.createObject();
    }
    // Specific behavior depends on `random.random` implementation (unknown)
    // Potentially generates random product data and interacts with files
    public void random() throws IOException {
        random.random(productslist,"src/allFile/dataFile.txt",scanner);
    }
    public void start() throws IOException {
        String op=null;// Variable to store operation outcome
        //check commit if file transaction exist
        if(Files.exists(Paths.get("src/allFile/TransectionFile.txt"))){
            if (backgroundProcess.commitCheck("src/allFile/TransectionFile.txt",scanner)){
                // If commit check in background process passes
                op= backgroundProcess.commit(productslist,"src/allFile/TransectionFile.txt","src/allFile/dataFile.txt","startcommit",scanner);
                // Perform commit operation
            }
        }
        //direct read from dataFile if don't have action to commit
        if(op==null){
            backgroundProcess.readFromFile(productslist,"src/allFile/dataFile.txt","startcommit");
        }
        //get lastId for operation
        if(!productslist.isEmpty()){
            int lastId=productslist.get(productslist.size()-1).getId();
            product.setLastAssignedId( lastId);
            backgroundProcess.writeSizeToFile(lastId,"src/allFile/lastId.txt");
        }
    }
    public void commit() throws IOException {
        if (backgroundProcess.commitCheck("src/allFile/TransectionFile.txt",scanner)){
            // If commit check in background process passes
            backgroundProcess.commit(productslist,"src/allFile/TransectionFile.txt","src/allFile/dataFile.txt","commit",scanner);
            // Perform commit operation
        }else
            System.out.println(darkYellow +"\uD83D\uDCE2THERE ARE NOTHING TO COMMIT ...."+reset);
        // Inform user of no commit
        }
    public void restore(){
        backgroundProcess.restore(productslist,"src/allFile/dataFile.txt",scanner);
    }
    // Restore product data
}