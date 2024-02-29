package controller;

import dao.BackUpFileProcessImpl;
import dao.BackgroundProcessImpl;
import dao.ProductDaoImpl;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import views.BoxBorder;
import views.InterfaceViews;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductController implements BoxBorder {
    private Scanner scanner;
    private ProductDaoImpl productDaoImpl;
    private BackUpFileProcessImpl backUpFileProcessImpl;
    private BackgroundProcessImpl backgroundProcess = new BackgroundProcessImpl();

    private int setRow = 5;
    boolean isContinue = true;
    private int proId;
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static List<Product> productList = Collections.synchronizedList(new ArrayList<>());


    public static List<Product> products() {
        return productList;
    }

    public ProductController() {
        scanner = new Scanner(System.in);
        productDaoImpl = new ProductDaoImpl();
        backUpFileProcessImpl = new BackUpFileProcessImpl();

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
            Integer qty = scanner.nextInt();
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
            System.out.println(e.getMessage());
            scanner.nextLine();
        }
    }


    public void read() {
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


    void clickEnter() {
        System.out.print("Click [ ENTER ] to Continue Operation ...");
        scanner.nextLine();
    }

    private String getValidProductName() {
        String newName = null;
        do {
            System.out.print("Enter new product name: ");
            newName = scanner.nextLine().trim();
            if (newName.matches(".*\\d.*")) {
                System.out.println(" ❌ Invalid name, Enter letter only.");
            }
        } while (newName.matches(".*\\d.*"));
        return newName;
    }

    private double getValidUnitPrice() {
        double newPrice = 0;
        while (true) {
            try {
                System.out.print("Enter new product Unit Price: ");
                newPrice = Double.parseDouble(scanner.nextLine());
                break; // Break out of the price input loop if the input is valid
            } catch (NumberFormatException e) {
                System.out.println(" ❌ Invalid price, Enter a valid price.");
            }
        }
        return newPrice;
    }

    private int getValidQuantity() {
        String newName = null;
        int newQty = 0;
        while (true) {
            try {
                System.out.print("Enter new product Qty: ");
                newQty = Integer.parseInt(scanner.nextLine());
                break; // Break out of the quantity input loop if the input is valid
            } catch (NumberFormatException e) {
                System.out.println(" ❌ Invalid quantity, Enter a valid number.");
            }
        }
        return newQty;
    }

    private void editSingleElement() {
        String newName = null;
        double newPrice = 0;
        int newQty = 0;
        String op;
        while (true) {
            try {
                System.out.println("\nWhich element of the product do you want to EDIT?");
                System.out.println("\t [ N ]. EDIT NAME");
                System.out.println("\t [ P ]. EDIT UNIT PRICE");
                System.out.println("\t [ Q ]. EDIT QUANTITY");
                System.out.println("\t [ B ]. Back \n");
                System.out.print("⏩⏩ Enter Your Choice : ");
                op = scanner.nextLine().toUpperCase();

                if (op.equals("B") || op.equals("b")) {
                    break; // Break out of the while loop if 'B' is chosen
                }

                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId() == proId) {
                        if (op.equals("N") || op.equals("n")) {
                            newName = getValidProductName();
                        } else if (op.equals("P") || op.equals("p")) {
                            newPrice = getValidUnitPrice();
                        } else if (op.equals("Q") || op.equals("q")) {
                            newQty = getValidQuantity();
                        } else {
                            System.out.println("### Please ENTER [ N || P || Q || B ]");
                            clickEnter();
                            break; // Break out of the for loop if an invalid option is chosen
                        }

                        System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                        String ans = scanner.nextLine();

                        if (ans.equalsIgnoreCase("y")) {
                            if (newName != null) {
                                for (char j : newName.toCharArray()) {
                                    if (Character.isDigit(j)) {
                                        throw new RuntimeException("Invalid name, contains digits.");
                                    }
                                }
                                productList.get(i).setName(newName);
                                System.out.println("✅ NAME of this product was edited successfully.");
                            }

                            if (newPrice != 0) {
                                productList.get(i).setUnitPrice(newPrice);
                                System.out.println("✅ UNIT PRICE of this product was edited successfully.");
                            }

                            if (newQty != 0) {
                                productList.get(i).setQty(newQty);
                                System.out.println("✅ QUANTITY of this product was edited successfully.");
                            }

                            System.out.println();
                            backgroundProcess.writeToFile(productList.get(proId), "edit");
                        } else {
                            System.out.println("❌ The process of editing was canceled.");
                        }

                        clickEnter();
                        break; // Break out of the for loop after the editing process
                    }
                }
            } catch (Exception e) {
                scanner.nextLine();
            }
        }
    }

    private void editMultiElement() {
        String newName = null;
        double newPrice = 0;
        int newQty = 0;
        try {
            while (true) {
                System.out.println("Multiple operations for EDITING product...");
                System.out.println("### Instruction [ N , P ] || [ N , Q ] || [ P , Q ] || ... ");
                System.out.print("Enter multiple operations : ");
                String operations = scanner.nextLine().toUpperCase();
                String[] ops = operations.split(",");
                Set<String> uniqueOps = new HashSet<>();

                if (ops.length != 2) {
                    System.out.println("❌ Invalid. \n");
                    clickEnter();
                    System.out.println();
                    continue;
                }

                for (String op : ops) {
                    op = op.trim();
                    if (!uniqueOps.add(op)) {
                        System.out.println(" ❌ Duplicate operation: " + op);
                        return;
                    }
                }
                for (String op : ops) {
                    op = op.trim();

                    if (op.equals("B") || op.equals("b")) {
                        break; // Break out of the while loop if 'B' is chosen
                    }

                    switch (op) {
                        case "N": {
                            newName = getValidProductName();
                            break;
                        }
                        case "P": {
                            newPrice = getValidUnitPrice();
                            break;
                        }
                        case "Q": {
                            newQty = getValidQuantity();
                            break;
                        }
                        case "B": {
                            break;
                        }
                    }
                }

                // Confirm the update for all elements
                System.out.print("ℹ️ Are you sure to update all elements of this product? [Y/N] : ");
                String ans = scanner.nextLine();
                if (!ans.equalsIgnoreCase("y")) {
                    System.out.println("❌ The process of editing was canceled.");
                    return;
                }

                // Update product with the new values
                if (newName != null) {
                    productList.get(proId).setName(newName);
                }
                if (newPrice != 0) {
                    productList.get(proId).setUnitPrice(newPrice);
                }
                if (newQty != 0) {
                    productList.get(proId).setQty(newQty);
                }

                System.out.println("✅ This product has been edited successfully.\n");
                backgroundProcess.writeToFile(productList.get(proId), "edit");
                clickEnter();
                break;
            }
        } catch (Exception e) {
            System.out.println(" ❌ Invalid Input");
            System.out.println(e.getMessage());
            scanner.nextLine();
        }
    }

    private void editAllElement() {
        String newName = null;
        double newPrice = 0;
        int newQty = 0;
        boolean editSuccessful = false;
        try {
            while (true) {
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId().equals(proId)) {
                        newName = getValidProductName();
                        newPrice = getValidUnitPrice();
                        newQty = getValidQuantity();

                        System.out.print("ℹ️ Are you sure to update this product? [Y/N] : ");
                        String ans = scanner.nextLine();
                        if (ans.equalsIgnoreCase("y")) {
                            productList.get(i).setName(newName);
                            productList.get(i).setUnitPrice(newPrice);
                            productList.get(i).setQty(newQty);
                            backgroundProcess.writeToFile(productList.get(proId), "edit");
                            System.out.println("✅ This product has been edited successfully.");
                            clickEnter();
                            editSuccessful = true;
                            break;
                        } else {
                            System.out.println("❌ The process of editing was canceled.");
                            editSuccessful = true;
                            clickEnter();
                            break;
                        }
                    }
                }
                if (editSuccessful) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(" ❌ Invalid Input");
        }
    }

    public void editProduct() {
        while (true) {
            try {
                System.out.print("Enter Product ID: ");
                proId = Integer.parseInt(scanner.nextLine());
                Product product = productDaoImpl.read(proId, productList);
                if (product != null) {
                    System.out.println("Product Detail of CODE[" + product.getId() + "]");
                    InterfaceViews.readDetail(product);
                    break;
                } else {
                    System.out.println(" ❌ Invalid ID");
                }
            } catch (NumberFormatException e) {
                System.out.println(" ❌ Invalid ID, Enter a valid ID.");
            }
        }

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
                        editSingleElement();
                        break;
                    }
                    case "ME": {
                        editMultiElement();
                        break;
                    }
                    case "AE": {
                        editAllElement();
                        break;
                    }
                    case "B": {
                        break;
                    }
                    default: {
                        System.out.println("❌ Invalid Option!!");
                        clickEnter();
                    }
                }
            } while (!choice.equals("B"));

        } catch (Exception e) {
            System.out.println("❌ Invalid Input");
            System.out.println("Enter the correct format ...");
            scanner.nextLine();
        }
    }

    public void deleteById() {
        System.out.print("Enter Product Id To Delete : ");
        int proId = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == proId) {
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

    public void BackUpFile() throws IOException {
        String source = "src/AllFile/dataFile.txt";
        String target = "src/backupfiles";
        isContinue = true;
        while (isContinue) {
            System.out.print("ℹ️ Are you sure to back up the file ? [Y/N] : ");
            String ans = scanner.nextLine();
            if (ans.equalsIgnoreCase("y")) {
                backUpFileProcessImpl.performBackup(source, target);
                isContinue = false;
            } else if (ans.equalsIgnoreCase("n")) {
                System.out.println("🏠 Back to Menu...");
                isContinue = false;
            } else {
                System.out.println(" ❌ Invalid Option");
            }
        }
    }
}
