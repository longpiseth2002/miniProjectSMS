package controller;

import dao.ProductDaoImpl;
import model.Product;
import views.BoxBorder;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class ProductController implements BoxBorder {
    private Scanner scanner;
    private ProductDaoImpl productDaoImpl;
    static List<Integer> list = new ArrayList<>();


    public ProductController() {
        scanner = new Scanner(System.in);
        productDaoImpl = new ProductDaoImpl();
    }



    // Default set row
    int setRow = 5;





    private static void add(int n) {
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
    }


    static {
        add(2353646);
    }

    public void display() {
        productDaoImpl.display(list, setRow, scanner);
    }


    public void setNumberRow() {
        int inputRow;
        System.out.println(HORIZONTAL_CONNECTOR_BORDER.repeat(100));
        do {
            try {
                System.out.print("⏩ ENTER NUMBER OF ROW : ");
                inputRow = scanner.nextInt();
                if (inputRow > 0) {
                    productDaoImpl.setUpRow(inputRow, setRow);
                    setRow=inputRow;
                    break;
                } else {
                    System.out.println(red + "   ❌ NUMBERS OF ROWS MUST BE GREATER THAN 0 !!!!! " + reset);
                }
            } catch (InputMismatchException e) {
                System.out.println(red +  "   ❌ PLEASE ENTER A VALID INTEGER VALUES !!!! " + reset);
                scanner.nextLine();
            }
        } while (true);
    }

    public void write(){
        productDaoImpl.write(scanner);
        List<Product> products = new ArrayList<>();
        products.add(products);

    }

}
