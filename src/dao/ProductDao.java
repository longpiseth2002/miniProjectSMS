package dao;


import model.Product;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public interface ProductDao {
    void display(List<Integer> list , int numberOfRow , Scanner input);
    Product insert(Product product);
    List<Product> select();
    Optional<Product> selectById(Integer id);
    Product updateById(Product product);
    Product deleteById(Integer id);
    List<Product> selectByName(String name);
    void setUpRow(int numberOfRow,int setRow);
}
