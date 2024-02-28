package controller;

import dao.BackUpFileProcessImpl;
import dao.ProductDaoImpl;
import model.Product;
import views.BoxBorder;
import views.InterfaceViews;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.InputMismatchException;

public class ProductController implements BoxBorder {
    private Scanner scanner;
    private ProductDaoImpl productDaoImpl;
    private BackUpFileProcessImpl backUpFileProcessImpl;

    boolean isContinue = true;
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
        backUpFileProcessImpl = new BackUpFileProcessImpl();
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

        try{
            System.out.print("Enter product name: " );
            String proName = scanner.nextLine().trim();
            for (char i : proName.toCharArray()){
                if(Character.isDigit(i)){
                    throw new Exception();
                }
            }
            System.out.print("Enter product Unit Price: " );
            Double unitPrice = scanner.nextDouble();
            System.out.print("Enter product Qty: " );
            Double qty = scanner.nextDouble();
            scanner.nextLine();
            isContinue = true;
            while(isContinue){
                System.out.print("ℹ️ Are you sure to create a new product? [Y/N] : ");
                String ans = scanner.nextLine();
                if(ans.equalsIgnoreCase("y")){
                    productDaoImpl.write(new Product(proId,proName,unitPrice,qty,importAt),productList,"write");
                    System.out.println("✅ Product has been created successfully");
                    isContinue = false;
                }else if(ans.equalsIgnoreCase("n")){
                    System.out.println("🏠 Back to Menu...");
                    isContinue = false;
                }else{
                    System.out.println(" ❌ Invalid Option");
                }
            }
        }catch (Exception e){
            System.out.println(" ❌ Invalid Input");
            scanner.nextLine();
        }
    }

    public void read(){
            try {
                System.out.print("Enter Product Id: ");
                int proId = Integer.parseInt(scanner.nextLine());
                Product product = productDaoImpl.read(proId, productList);
                if (product != null) {
                    System.out.println("Product Detail of CODE[" + product.getId() + "]");
                    InterfaceViews.readDetail(product);
                } else {
                    System.out.println(" ❌ Invalid ID");
                }
            } catch (Exception e) {
                System.out.println(" ❌ Please enter number only.");
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

    public void BackUpFile(){
        String source = "src/AllFile/dataFile.txt";
        String target = "src/backupfiles";
        isContinue = true;
        while(isContinue){
            System.out.print("ℹ️ Are you sure to back up the file ? [Y/N] : ");
            String ans = scanner.nextLine();
            if(ans.equalsIgnoreCase("y")){
                backUpFileProcessImpl.performBackup(source,target);
                isContinue = false;
            }else if(ans.equalsIgnoreCase("n")){
                System.out.println("🏠 Back to Menu...");
                isContinue = false;
            }else{
                System.out.println(" ❌ Invalid Option");
            }
        }


    }
}
