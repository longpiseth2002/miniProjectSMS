package model;

import java.time.LocalDate;

public class Product {

    private Integer id;
    private String name;
    private Double unitPrice;
    private Double qty;
    private LocalDate importAt;
    private static Integer lastAssignedId = 0;


    public Product() {
        this.id = ++lastAssignedId;
    }

    public Product(String name, Double unitPrice, Double qty, LocalDate importAt) {
        this.id = ++lastAssignedId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.importAt = importAt;
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

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public LocalDate getImportAt() {
        return importAt;
    }

    public void setImportAt(LocalDate importAt) {
        this.importAt = importAt;
    }




}
