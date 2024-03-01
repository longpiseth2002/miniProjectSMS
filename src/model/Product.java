package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Product {

    private int id;
    private String name;
    private double unitPrice;
    private int qty;
    private LocalDate importAt;
    private static int lastAssignedId = 0;


    public void setLastAssignedId(int lastAssignedId) {
        Product.lastAssignedId = lastAssignedId;
    }

    public Product() {
    }


    public Product(String name, double unitPrice, int qty) {
        this.id = ++lastAssignedId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.importAt = LocalDate.now();
    }


    public Product(int id, String name, double unitPrice, int qty, LocalDate date) {
        this.id=id;
        this.name=name;
        this.unitPrice=unitPrice;
        this.qty=qty;
        this.importAt=date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public LocalDate getImportAt() {
        return importAt;
    }

    public void setImportAt(LocalDate importAt) {
        this.importAt = importAt;
    }




}
