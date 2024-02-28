package dao;


import model.Product;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public interface ProductDao {

    void display(List<Product> productList , int numberOfRow , Scanner input);
    void write(Product product,List<Product> productList,String Status);
    Product read(Integer proId, List<Product> productList);
    Optional<Product> selectById(Integer id,List<Product> productList);
    Product updateById(Product product);
    Product deleteById(Integer id , List<Product> products);
    List<Product> searchByName(List<Product> products ,String name);
    void setUpRow(int numberOfRow,int setRow);
}
