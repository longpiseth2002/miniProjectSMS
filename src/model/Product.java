package model;

import java.time.LocalDate;

public class Product {

    private Integer id;
    private String name;
    private Double unitPrice;
    private Integer qty;
    private LocalDate importAt;
    private static Integer lastAssignedId = 0;


    public Product() {
        this.id = lastAssignedId++;
    }

    public Product(String name, Double unitPrice, Integer qty) {
        this.id = ++lastAssignedId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.importAt = LocalDate.now();
    }

    public Product(int id, String name, Double unitPrice, Integer qty, LocalDate date) {
        this.id=id;
        this.name=name;
        this.unitPrice=unitPrice;
        this.qty=qty;
        this.importAt=date;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", importAt=" + importAt +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public LocalDate getImportAt() {
        return importAt;
    }

    public void setImportAt(LocalDate importAt) {
        this.importAt = importAt;
    }




}
