package dao;


import model.Product;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public interface ProductDao {

    void display(List<Product> productList , int numberOfRow , Scanner input);
    void write(Product product,List<Product> productList,String Status);
    Product read(int proId, List<Product> productList);
    Optional<Product> selectById(int id,List<Product> productList);
    Product deleteById(int id , List<Product> products);
    List<Product> searchByName(List<Product> products ,String name);
    void setUpRow(int numberOfRow,int setRow);


}
