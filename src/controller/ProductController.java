package controller;

import dao.BackUpFileProcessImpl;
import dao.ProductDaoImpl;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import views.BoxBorder;
import views.InterfaceViews;

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

    private int setRow = 5;
    boolean isContinue = true;
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static List<Product> productList = Collections.synchronizedList(new ArrayList<>());


    public static List<Product> products(){
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

        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("ENTER PRODUCT NAME: ");
                String proName = scanner.nextLine().trim();
                for (char i : proName.toCharArray()) {
                    if (Character.isDigit(i)) {
                        throw new Exception("PRODUCT NAME CANNOT CONTAIN DIGITS.");
                    }
                }

                System.out.print("ENTER PRODUCT UNIT PRICE: ");
                Double unitPrice = scanner.nextDouble();

                System.out.print("ENTER PRODUCT QTY: ");
                Integer qty = scanner.nextInt();

                scanner.nextLine();

                isContinue = true;
                while(isContinue){
                    System.out.print("ℹ️ ARE YOU SURE TO CREATE A NEW PRODUCT? [Y/N] : ");
                    String ans = scanner.nextLine();
                    if(ans.equalsIgnoreCase("Y")){
                        productDaoImpl.write(new Product(proName,unitPrice,qty),productList,"write");
                        System.out.println("✅ PRODUCT HAS BEEN CREATED SUCCESSFULLY");
                        isContinue = false;
                    } else if(ans.equalsIgnoreCase("N")){
                        System.out.println(" 🏠 BACK TO APPLICATION MENU ...");
                        isContinue = false;
                    } else {
                        System.out.println(red + " ❌ INVALID OPTION " + reset);
                    }
                }

                isValidInput = true;
            } catch (Exception e) {
                System.out.println(red + " ❌ INVALID INPUT . PLEASE ENTER AGAIN !!!! " + reset);
                scanner.nextLine();
            }
        }
    }


    public void read() {
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("ENTER PRODUCT ID: ");
                int proId = Integer.parseInt(scanner.nextLine());
                Product product = productDaoImpl.read(proId, productList);
                if (product != null) {
                    System.out.println("PRODUCT DETAIL OF CODE[" + product.getId() + "]");
                    InterfaceViews.readDetail(product);
                    isValidInput = true;
                } else {
                    System.out.println( red + " ❌ PRODUCT NOT FOUND" + reset);
                }
            } catch (NumberFormatException e) {
                System.out.println(red + " ❌ INVALID FORMAT" + reset);
            }

            if (!isValidInput) {
                System.out.print("ℹ️ ENTER 'Y' TO ENTER PRODUCT ID AGAIN, OR PRESS ANY KEY TO BACK TO APPLICATION MENU: ");
                String choice = scanner.nextLine().toLowerCase();
                if (!choice.equalsIgnoreCase("Y")) {
                    System.out.println(" 🏠 BACK TO APPLICATION MENU...");
                    isValidInput = true;
                }
            }
        }
    }



    public void deleteById() {
        while (true) {
            try {
                System.out.print("ENTER PRODUCT ID TO DELETE: ");
                int proId = Integer.parseInt(scanner.nextLine());

                boolean found = false;
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId() == proId) {
                        found = true;
                        Product product = productDaoImpl.read(proId, productList);
                        InterfaceViews.readDetail(product);
                        System.out.print("ℹ️ ARE YOU SURE TO DELETE A PRODUCT? [Y/N] : ");
                        String op = scanner.nextLine();
                        if (op.equalsIgnoreCase("y")) {
                            productDaoImpl.deleteById(proId, productList);
                            System.out.println("✅ PRODUCT HAS BEEN DELETED SUCCESSFULLY");
                        } else if (op.equalsIgnoreCase("n")) {
                            System.out.println("🏠 BACK TO MENU...");
                        } else {
                            System.out.println(red + " ❌ INVALID OPTION" + reset);
                        }
                        break;
                    }
                }

                if (!found) {
                    System.out.println(red + " ❌ PRODUCT NOT FOUND" + reset);
                    System.out.print("ℹ️ ENTER 'Y' TO ENTER PRODUCT ID AGAIN, OR PRESS ANY KEY TO BACK TO APPLICATION MENU: ");
                    String choice = scanner.nextLine();
                    if (!choice.equalsIgnoreCase("Y")) {
                        System.out.println("🏠 BACK TO APPLICATION MENU...");
                        break;
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(red + " ❌ INVALID FORMAT" + reset);
                System.out.print("ℹ️ ENTER 'Y' TO ENTER PRODUCT ID AGAIN, OR PRESS ANY KEY TO BACK TO APPLICATION MENU: ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("y")) {
                    System.out.println(" 🏠 BACK TO APPLICATION MENU...");
                    break;
                }
            }
        }
    }



    public void searchByName() {
        Table table = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        System.out.print("ENTER PRODUCT NAME TO SEARCH: ");
        String proName = scanner.nextLine().trim();

        List<Product> matchingProducts = productDaoImpl.searchByName(productList, proName);
        if (!matchingProducts.isEmpty()) {
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            table.setColumnWidth(0, 10, 20); // Adjust column widths as needed
            table.setColumnWidth(1, 20, 40);
            table.setColumnWidth(2, 15, 25);
            table.setColumnWidth(3, 5, 10);
            table.setColumnWidth(4, 15, 25);

            table.addCell("CODE", cellStyle); // Remove extra spaces for table headers
            table.addCell("NAME", cellStyle);
            table.addCell("UNIT PRICE", cellStyle);
            table.addCell("QTY", cellStyle);
            table.addCell("IMPORTED AT", cellStyle);

            for (Product product : matchingProducts) {
                table.addCell(String.valueOf(product.getId()), cellStyle);
                table.addCell(product.getName(), cellStyle);
                table.addCell(String.valueOf(product.getUnitPrice()), cellStyle);
                table.addCell(String.valueOf(product.getQty()), cellStyle);
                table.addCell(String.valueOf(product.getImportAt()), cellStyle);
            }

            System.out.println(table.render());
        } else {
            System.out.println("PRODUCT: " + proName + " NOT FOUND");
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
