package dao;

import model.Product;

import java.time.LocalDate;
import java.util.List;

public class Random {
    public void random(List<Product> list){
        for(int i=0;i<1000000;i++){
            list.add(new Product(10,"h",10.2,5.5, LocalDate.now()));
        }
    }
}
