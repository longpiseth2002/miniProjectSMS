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
    private int setRow = 5;
    private Product product;
    boolean isContinue = true;
    private static List<Product> productList = new ArrayList<>();
    int proId;

    public static List<Product> products() {
        return productList;
    }

    public ProductController() {
        scanner = new Scanner(System.in);
        productDaoImpl = new ProductDaoImpl();
        product = new Product();
    }


    public void display() {
        productDaoImpl.display(productList, setRow, scanner);
    }

    public void write() {
        Integer proId = productList.size() + 1;

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
                    productDaoImpl.write(new Product(proName, unitPrice, qty), productList, "write");
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
            proId = Integer.parseInt(scanner.nextLine());
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

    public void deleteById() {
        System.out.print("Enter Product Id To Delete : ");
        int proId = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(proId)) {
                productDaoImpl.read(proId, productList);
                System.out.print("ℹ️ Are you sure to delete a  product? [Y/N] : ");
                String op = scanner.nextLine();
                if (op.equals("y")) {
                    productDaoImpl.deleteById(proId, productList);
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

    private void editSingleElement(int proId) {
        String op;
        System.out.println("\nWhich element of product you want to EDIT?");
        System.out.println("[ N ]. EDIT NAME");
        System.out.println("[ P ]. EDIT UNIT PRICE");
        System.out.println("[ Q ]. EDIT QUANTITY\n");
        System.out.print("⏩⏩ Enter Your Choice : ");
        op = scanner.nextLine().toUpperCase();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == proId) {
                if (op.equals("N") || op.equals("n")) {
                    System.out.print("Enter new product name: ");
                    String newName = scanner.nextLine().trim();
                    System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                    String ans = scanner.nextLine();
                    if (ans.equalsIgnoreCase("y")) {
                        for (char j : newName.toCharArray()) {
                            if (Character.isDigit(j)) {
                                try {
                                    throw new Exception();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        productList.get(i).setName(newName);
                        System.out.println("✅ NAME of this product was edited successfully.");
                    } else {
                        System.out.println("❌ The process of editing was canceled.");
                    }
                }

                if (op.equals("P") || op.equals("p")) {
                    System.out.print("Enter new product Unit Price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                    String ans = scanner.nextLine();
                    if (ans.equalsIgnoreCase("y")) {
                        productList.get(i).setUnitPrice(newPrice);
                        System.out.println("✅ UNIT PRICE of this product was edited successfully.");
                    } else {
                        System.out.println("❌ The process of editing was canceled.");
                    }
                }

                if (op.equals("Q") || op.equals("q")) {
                    System.out.print("Enter new product Qty: ");
                    double newQty = Double.parseDouble(scanner.nextLine());
                    System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                    String ans = scanner.nextLine();
                    if (ans.equalsIgnoreCase("y")) {
                        productList.get(i).setQty(newQty);
                        System.out.println("✅ QUANTITY of this product was edited successfully.");
                    } else {
                        System.out.println("❌ The process of editing was canceled.");
                    }
                }
                System.out.print("\nClick [ ENTER ] to Continue Operation ...");
                scanner.nextLine();
                break;
            }
        }
    }

    private void editMultiElement(int proId) {
        System.out.println("Multiple operation for EDITING product...");
        System.out.println("### Instruction [ N , Q ]");
        System.out.print("Enter multiple operations : ");
        String operations = scanner.nextLine().toUpperCase();
        String[] ops = operations.split(",");
        Set<String> uniqueOps = new HashSet<>();

        if (ops.length != 2) {
            System.out.println(" ❌ Invalid number of operations. Please provide exactly two operations.");
            return;
        }
        for (String op : ops) {
            op = op.trim();
            if (!uniqueOps.add(op)) {
                System.out.println(" ❌ Duplicate operation: " + op);
                return;
            }
            switch (op) {
                case "N": {
                    System.out.print("Enter new product name: ");
                    String newName = scanner.nextLine().trim();
                    System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                    String ans = scanner.nextLine();
                    if (ans.equalsIgnoreCase("y")) {
                        for (char j : newName.toCharArray()) {
                            if (Character.isDigit(j)) {
                                try {
                                    throw new Exception();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        productList.get(proId).setName(newName);
                    } else {
                        System.out.println("❌ The process of editing was canceled.");
                        return;
                    }
                    break;
                }
                case "P": {
                    System.out.print("Enter new product Unit Price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                    String ans = scanner.nextLine();
                    if (ans.equalsIgnoreCase("y")) {
                        productList.get(proId).setUnitPrice(newPrice);
                    } else {
                        System.out.println("❌ The process of editing was canceled.");
                        return;
                    }
                    break;
                }
                case "Q": {
                    System.out.print("Enter new product Qty: ");
                    double newQty = Double.parseDouble(scanner.nextLine());
                    System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                    String ans = scanner.nextLine();
                    if (ans.equalsIgnoreCase("y")) {
                        productList.get(proId).setQty(newQty);
                    } else {
                        System.out.println("❌ The process of editing was canceled.");
                        return;
                    }
                    break;
                }
                default:
                    System.out.println(" ❌ Invalid operation: " + op);
            }
        }

        System.out.println("✅ This product has been edited successfully.");
        System.out.print("\nClick [ ENTER ] to Continue Operation ...");
        scanner.nextLine();
    }

    private void editAllElement(int proId) throws Exception {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(proId)) {
                System.out.print("Enter new product name: ");
                String newName = scanner.nextLine().trim();
                for (char j : newName.toCharArray()) {
                    if (Character.isDigit(j)) {
                        throw new Exception();
                    }
                }
                System.out.print("Enter new product Unit Price: ");
                double newPrice = Double.parseDouble(scanner.nextLine());
                productList.get(i).setName(newName);
                System.out.print("Enter new product Qty: ");
                double newQty = Double.parseDouble(scanner.nextLine());

                System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                String ans = scanner.nextLine();
                if (ans.equalsIgnoreCase("y")) {
                    productList.get(i).setName(newName);
                    productList.get(i).setUnitPrice(newPrice);
                    productList.get(i).setQty(newQty);
                    System.out.println("✅ This product has been edited successfully.");
                } else {
                    System.out.println("❌ The process of editing was canceled.");
                }
                System.out.print("\nClick [ ENTER ] to Continue Operation ...");
                scanner.nextLine();
            }
        }
    }

    public void editProduct() {
        read();
        try {
            String choice;
            do {
                System.out.println("\nWhich Operation of EDIT You Want to Use?");
                System.out.println("[ SE ]. EDIT Single Element");
                System.out.println("[ ME ]. EDIT Multiple Elements");
                System.out.println("[ AE ]. EDIT All Elements");
                System.out.println("[ B ]. Back to The MENU\n");
                System.out.print("⏩⏩ Enter Your Choice : ");
                choice = scanner.nextLine().toUpperCase();

                switch (choice) {
                    case "SE": {
                        editSingleElement(proId);
                        break;
                    }
                    case "ME": {
                        editMultiElement(proId);
                        break;
                    }
                    case "AE": {
                        editAllElement(proId);
                        break;
                    }

                    case "B": {
                        break;
                    }
                }
            } while (!choice.equals("B"));

        } catch (Exception e) {

            System.out.println(" ❌ Invalid Input");
            System.out.println("Enter the correct format ...");
            scanner.nextLine();
        }
    }

    public void searchByName() {
        Table table = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);
        System.out.print("Enter product name: ");
        String proName = scanner.nextLine();

        List<Product> matchingProducts = productDaoImpl.selectByName(productList, proName);
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
                scanner.nextLine();
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
