package dao;

import model.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Random {
    private static AtomicInteger currenSize=new AtomicInteger(0);
    public void random() {

    }

    public static void main(String[] args) {
        Random obj=new Random();
        obj.random();
    }
}
