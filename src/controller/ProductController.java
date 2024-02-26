package controller;

import dao.BackgroundProcessImpl;
import dao.ProductDaoImpl;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import views.BoxBorder;
import views.InterfaceViews;

import java.time.LocalDate;
import java.util.*;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductController implements BoxBorder {
    private Scanner scanner;
    private ProductDaoImpl productDaoImpl;
    private List<Product> products;

    boolean isContinue = true;


    public ProductController() {
        scanner = new Scanner(System.in);
        productDaoImpl = new ProductDaoImpl();
        products = new ArrayList<>();
    }

    // Default number of rows
    int setRow = 5;


    public void display() {
        System.out.println(setRow);
        productDaoImpl.display(products, setRow, scanner);
    }

    public void write() {
        Integer proId = products.size() + 1;

        try {
            System.out.print("Enter product name: ");
            String proName = scanner.nextLine().trim();
            for (char i : proName.toCharArray()) {
                if (Character.isDigit(i)) {
                    throw new Exception();
                }
            }
            System.out.print("Enter product Unit Price: ");
            Double unitPrice = scanner.nextDouble();
            System.out.print("Enter product Qty: ");
            Double qty = scanner.nextDouble();
            scanner.nextLine();
            isContinue = true;
            while (isContinue) {
                System.out.print("ℹ️ Are you sure to create a new product? [Y/N] : ");
                String ans = scanner.nextLine();
                if (ans.equalsIgnoreCase("y")) {
                    productDaoImpl.write(new Product(proName, unitPrice, qty), products, "write");
                    System.out.println("✅ Product has been created successfully");
                    isContinue = false;
                } else if (ans.equalsIgnoreCase("n")) {
                    System.out.println("🏠 Back to Menu...");
                    isContinue = false;
                } else {
                    System.out.println(" ❌ Invalid Option");
                }
            }
        } catch (Exception e) {
            System.out.println(" ❌ Invalid Input");
            scanner.nextLine();
        }
    }

    public void read() {
        try {
            System.out.print("Enter Product Id: ");
            int proId = Integer.parseInt(scanner.nextLine());
            Product product = productDaoImpl.read(proId, products);
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

    public void deleteById() {
        System.out.print("Enter Product Id To Delete : ");
        int proId = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(proId)) {
                productDaoImpl.read(proId, products);
                System.out.print("ℹ️ Are you sure to delete a  product? [Y/N] : ");
                String op = scanner.nextLine();
                if (op.equals("y")) {
                    productDaoImpl.deleteById(proId, products);
                    System.out.println("✅ Product has been deleted successfully");

                } else if (op.equalsIgnoreCase("n")) {
                    System.out.println("🏠 Back to Menu...");
                    isContinue = false;
                } else {
                    System.out.println(" ❌ Invalid Option");
                }

            }
        }

    }
    public void searchByName() {
        Table table = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);
        System.out.print("Enter product name: ");
        String proName = scanner.nextLine();

        List<Product> matchingProducts = productDaoImpl.selectByName(products, proName);
        if (!matchingProducts.isEmpty()) {
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            table.setColumnWidth(0, 25, 30);
            table.setColumnWidth(1, 25, 30);
            table.setColumnWidth(2, 25, 30);
            table.setColumnWidth(3, 25, 30);
            table.setColumnWidth(4, 25, 30);

            table.addCell("  CODE ", cellStyle);
            table.addCell("  NAME ", cellStyle);
            table.addCell("  UNIT PRICE ", cellStyle);
            table.addCell("  QTY ", cellStyle);
            table.addCell("  IMPORTED AT ", cellStyle);

            for (Product product : matchingProducts) {
                table.addCell(String.valueOf(product.getId()), cellStyle);
                table.addCell(product.getName(), cellStyle);
                table.addCell(String.valueOf(product.getUnitPrice()), cellStyle);
                table.addCell(String.valueOf(product.getQty()), cellStyle);
                table.addCell(String.valueOf(product.getImportAt()), cellStyle);
            }

            System.out.println(table.render());
        } else {
            System.out.println("No products found with name: " + proName);
        }
    }


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

}
