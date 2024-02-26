import controller.ProductController;
import model.Product;


import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {

        ProductController productController = new ProductController();
        productController.write();
        productController.display();

    }
}
