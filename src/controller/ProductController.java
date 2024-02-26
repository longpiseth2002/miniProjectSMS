package controller;

import dao.ProductDaoImpl;
import model.Product;
import views.BoxBorder;
import views.InterfaceViews;

import java.time.LocalDate;
import java.util.*;
import java.util.InputMismatchException;

public class ProductController implements BoxBorder {
    private Scanner scanner;
    private ProductDaoImpl productDaoImpl;
//    static List<Integer> list = new ArrayList<>();
    static List<Product> productList = new ArrayList<>(Arrays.asList(
        new Product(1,"Coca",0.5,20.0,LocalDate.now()),
        new Product(2,"Sting",2.0,20.0,LocalDate.now()),
        new Product(3,"Carabao",1.0,20.0,LocalDate.now())

));
    Product product;


    public ProductController() {
        scanner = new Scanner(System.in);
        productDaoImpl = new ProductDaoImpl();
    }



    // Default set row
    int setRow = 5;





//    private static void add(int n) {
//        for (int i = 0; i < n; i++) {
//            list.add(i);
//        }
//    }


//    static {
//        add(2353646);
//    }

    public void display() {
        productDaoImpl.display(productList, setRow, scanner);
    }

    public void write(){
        Integer proId = productList.size() + 1;
        LocalDate importAt = LocalDate.now();
        System.out.print("Enter product name: " );
        String proName = scanner.nextLine();
        System.out.print("Enter product Unit Price: " );
        Double unitPrice = scanner.nextDouble();
        System.out.print("Enter product Qty: " );
        Double qty = scanner.nextDouble();
        boolean isContinue = true;
        while(isContinue){
            System.out.print(" ℹ️ Are you sure to create a new product? [Y/N] : ");
            scanner.nextLine();
            String ans = scanner.nextLine();
            if(ans.equalsIgnoreCase("y")){
                productDaoImpl.write(new Product(proId,proName,unitPrice,qty,importAt),productList);
                System.out.println("   ✅ Product has been created successfully");
                isContinue = false;
            }else if(ans.equalsIgnoreCase("n")){
                System.out.println("   🏠 Back to Menu...");
                isContinue = false;
            }else{
                System.out.println("    ❌ Invalid Option");
            }
        }
    }

    public void read(){
        System.out.print("Enter Product Id: ");
        Integer proId = scanner.nextInt();
        Product product = productDaoImpl.read(proId,productList);
        if(product!=null){
            System.out.println("Product Detail of CODE[" + product.getId() + "]");
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Unit Price: " + product.getUnitPrice());
            System.out.println("Product QTY: " + product.getQty());
            System.out.println("Product Imported Date : " + product.getImportAt());
        }
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

}
