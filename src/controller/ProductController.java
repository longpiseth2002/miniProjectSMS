package controller;

import dao.ProductDaoImpl;
import model.Product;
import views.BoxBorder;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductController implements BoxBorder {
    private Scanner scanner;
    private ProductDaoImpl productDaoImpl;
    private List<Product> products;

    public ProductController() {
        scanner = new Scanner(System.in);
        productDaoImpl = new ProductDaoImpl();
        products = new ArrayList<>();
    }

    // Default number of rows
    int setRow = 5;

//    public void display() {
//        productDaoImpl.display(products, setRow, scanner);
//    }

    public void setNumberRow() {
        int inputRow;
        System.out.println(HORIZONTAL_CONNECTOR_BORDER.repeat(100));
        do {
            try {
                System.out.print("⏩ ENTER NUMBER OF ROW: ");
                inputRow = scanner.nextInt();
                if (inputRow > 0) {
                    productDaoImpl.setUpRow(inputRow, setRow);
                    setRow = inputRow;
                    break;
                } else {
                    System.out.println(red + "   ❌ NUMBERS OF ROWS MUST BE GREATER THAN 0!!!!!" + reset);
                }
            } catch (InputMismatchException e) {
                System.out.println(red + "   ❌ PLEASE ENTER A VALID INTEGER VALUE!!!!!" + reset);
                scanner.nextLine();
            }
        } while (true);
    }

    public void write() {
        System.out.print("Enter the number of products: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("Entering details for product " + (i + 1) + ":");
            Product product = productDaoImpl.write(scanner);
            products.add(product);
        }
    }


    @Override
    public String toString() {
        return "ProductController{" +
                "products=" + products +
                '}';
    }

    public void display(){
        for(Product p : products){
            System.out.println(p);
        }
    }
}
