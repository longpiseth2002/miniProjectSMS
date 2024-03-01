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
import dao.BackgroundProcessImpl;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.InputMismatchException;
import java.util.ArrayList;
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
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("ENTER PRODUCT NAME: ");
                String proName = scanner.nextLine().trim();

                // Check if the name contains at least one letter
                boolean containsLetter = false;
                for (char i : proName.toCharArray()) {
                    if (Character.isLetter(i)) {
                        containsLetter = true;
                        break;
                    }
                }
                if (!containsLetter) {
                    throw new Exception("PRODUCT NAME MUST CONTAIN AT LEAST ONE LETTER.");
                }

                Double unitPrice = null;
                while (unitPrice == null) {
                    System.out.print("ENTER PRODUCT UNIT PRICE: ");
                    String unitPriceInput = scanner.nextLine();
                    try {
                        unitPrice = Double.parseDouble(unitPriceInput);
                    } catch (NumberFormatException e) {
                        System.out.println(red + " ❌ INVALID INPUT. PLEASE ENTER A VALID NUMBER. " + reset);
                    }
                }

                Integer qty = null;
                while (qty == null) {
                    System.out.print("ENTER PRODUCT QTY: ");
                    String qtyInput = scanner.nextLine();
                    try {
                        qty = Integer.parseInt(qtyInput);
                    } catch (NumberFormatException e) {
                        System.out.println(red + " ❌ INVALID INPUT. PLEASE ENTER A VALID NUMBER. " + reset);
                    }
                }

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
            } catch (NumberFormatException e) {
                System.out.println(red + " ❌ INVALID INPUT. PLEASE ENTER A VALID NUMBER. " + reset);
            } catch (Exception e) {
                System.out.println(red + " ❌ " + e.getMessage() + reset);
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
                    System.out.println( red + "   ❌ PRODUCT NOT FOUND" + reset);
                }
            } catch (NumberFormatException e) {
                System.out.println(red + "   ❌ INVALID FORMAT" + reset);
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




    void clickEnter() {
        System.out.print("CLICK [ ENTER ] TO CONTINUE OPERATION ...");
        scanner.nextLine();
    }

    private String getValidProductName() {
        String newName = null;
        do {
            System.out.print("ENTER NEW PRODUCT NAME: ");
            newName = scanner.nextLine().trim();
            if (newName.matches(".*\\d.*")) {
                System.out.println(red +"   ❌ INVALID NAME, ENTER LETTERS ONLY." + reset);
            }
        } while (newName.matches(".*\\d.*"));
        return newName;
    }

    private double getValidUnitPrice() {
        double newPrice = 0;
        while (true) {
            try {
                System.out.print("ENTER NEW PRODUCT UNIT PRICE: ");
                newPrice = Double.parseDouble(scanner.nextLine());
                break; // BREAK OUT OF THE PRICE INPUT LOOP IF THE INPUT IS VALID
            } catch (NumberFormatException e) {
                System.out.println(red + "   ❌ INVALID PRICE, ENTER A VALID PRICE." + reset);
            }
        }
        return newPrice;
    }

    private int getValidQuantity() {
        String newName = null;
        int newQty = 0;
        while (true) {
            try {
                System.out.print("ENTER NEW PRODUCT QTY: ");
                newQty = Integer.parseInt(scanner.nextLine());
                break; // BREAK OUT OF THE QUANTITY INPUT LOOP IF THE INPUT IS VALID
            } catch (NumberFormatException e) {
                System.out.println(red + "   ❌ INVALID QUANTITY, ENTER A VALID NUMBER." + reset);
            }
        }
        return newQty;
    }

    private void editSingleElement() {
        String newName = null;
        double newPrice = 0;
        int newQty = 0;
        String op;
        try {
            while (true) {
                System.out.println("\nWHICH ELEMENT OF THE PRODUCT DO YOU WANT TO EDIT?");
                System.out.println("\t [ N ]. EDIT NAME");
                System.out.println("\t [ P ]. EDIT UNIT PRICE");
                System.out.println("\t [ Q ]. EDIT QUANTITY");
                System.out.println("\t [ B ]. BACK \n");
                System.out.print("⏩⏩ ENTER YOUR CHOICE : ");
                op = scanner.nextLine().toUpperCase();

                if (op.equals("B")) {
                    break;
                }

                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId() == proId) {
                        if (op.equals("N")) {
                            newName = getValidProductName();
                        } else if (op.equals("P")) {
                            newPrice = getValidUnitPrice();
                        } else if (op.equals("Q")) {
                            newQty = getValidQuantity();
                        } else {
                            System.out.println("### PLEASE ENTER [ N || P || Q || B ]");
                            clickEnter();
                            break; // BREAK OUT OF THE FOR LOOP IF AN INVALID OPTION IS CHOSEN
                        }

                        System.out.print("ℹ️ ARE YOU SURE TO UPDATE THIS PRODUCT? [Y/N] : ");
                        String ans = scanner.nextLine();

                        if (ans.equalsIgnoreCase("Y")) {
                            if (newName != null) {
                                for (char j : newName.toCharArray()) {
                                    if (Character.isDigit(j)) {
                                        throw new RuntimeException("INVALID NAME, CONTAINS DIGITS.");
                                    }
                                }
                                productList.get(i).setName(newName);
                                System.out.println(" ✅ NAME OF THIS PRODUCT WAS EDITED SUCCESSFULLY.");
                            }

                            if (newPrice != 0) {
                                productList.get(i).setUnitPrice(newPrice);
                                System.out.println(" ✅ UNIT PRICE OF THIS PRODUCT WAS EDITED SUCCESSFULLY.");
                            }
                                if (newPrice != 0) {
                                    productList.get(i).setUnitPrice(newPrice);
                                    System.out.println("✅ UNIT PRICE of this product was edited successfully.");
                                }

                            if (newQty != 0) {
                                productList.get(i).setQty(newQty);
                                System.out.println(" ✅ QUANTITY OF THIS PRODUCT WAS EDITED SUCCESSFULLY.");
                            }

                            System.out.println();
                            backgroundProcess.writeToFile(productList.get(i), "edit");
                        } else {
                            System.out.println(red + "   ❌ THE PROCESS OF EDITING WAS CANCELED." + reset);
                        }

                        clickEnter();
                        break; // BREAK OUT OF THE FOR LOOP AFTER THE EDITING PROCESS
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(red + "   ❌ INVALID INPUT" + reset);
            System.out.println(e.getMessage());
            scanner.nextLine();
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

                boolean editingSuccessful = false;

                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getId() == proId) {
                        System.out.print("ℹ️ Are you sure to update all elements of this product? [Y/N] : ");
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
                                editingSuccessful = true;
                            }

                            if (newPrice != 0) {
                                productList.get(i).setUnitPrice(newPrice);
                                System.out.println("✅ UNIT PRICE of this product was edited successfully.");
                                editingSuccessful = true;
                            }

                            if (newQty != 0) {
                                productList.get(i).setQty(newQty);
                                System.out.println("✅ QUANTITY of this product was edited successfully.");
                                editingSuccessful = true;
                            }

                            if (editingSuccessful) {
                                System.out.println();
                                backgroundProcess.writeToFile(productList.get(i), "edit");
                            }

                            clickEnter();
                            break; // Break out of the for loop after the editing process
                        } else {
                            System.out.println("❌ The process of editing was canceled.");
                        }

                        clickEnter();
                        break; // Break out of the for loop after processing the current product
                    }
                }

                // Check if editing was successful and break out of the outer loop
                if (editingSuccessful) {
                    break;
                }

//                System.out.println("Debug: proId = " + proId + ", productList size = " + productList.size());
//                System.out.print("ℹ️ Are you sure to update all elements of this product? [Y/N] : ");
//                String ans = scanner.nextLine();
//                if (!ans.equalsIgnoreCase("y")) {
//                    System.out.println("❌ The process of editing was canceled.");
//                    return;
//                }
//
//                if (newName != null) {
//                    productList.get(proId).setName(newName);
//                }
//                if (newPrice != 0) {
//                    productList.get(proId).setUnitPrice(newPrice);
//                }
//                if (newQty != 0) {
//                    productList.get(proId).setQty(newQty);
//                }
//
//                System.out.println("✅ This product has been edited successfully.\n");
//                backgroundProcess.writeToFile(productList.get(i)), "edit");
//                clickEnter();
//                break;
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
                    if (productList.get(i).getId()==(proId)) {
                        newName = getValidProductName();
                        newPrice = getValidUnitPrice();
                        newQty = getValidQuantity();

                        System.out.print("ℹ️ ARE YOU SURE TO UPDATE THIS PRODUCT? [Y/N] : ");
                        String ans = scanner.nextLine();
                        if (ans.equalsIgnoreCase("Y")) {
                            productList.get(i).setName(newName);
                            productList.get(i).setUnitPrice(newPrice);
                            productList.get(i).setQty(newQty);
                            backgroundProcess.writeToFile(productList.get(i), "edit");
                            System.out.println(" ✅ THIS PRODUCT HAS BEEN EDITED SUCCESSFULLY.");
                            clickEnter();
                            editSuccessful = true;
                            break;
                        } else {
                            System.out.println(red + "   ❌ THE PROCESS OF EDITING WAS CANCELED." + reset);
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
            System.out.println(red + "   ❌ INVALID INPUT"+ reset);
        }
    }

    public void editProduct() {
        while (true){
            try {
                System.out.print("ENTER PRODUCT ID: ");
                proId = Integer.parseInt(scanner.nextLine());
                Product product = productDaoImpl.read(proId, productList);
                if (product != null) {
                    System.out.println("PRODUCT DETAIL OF CODE[" + product.getId() + "]");
                    InterfaceViews.readDetail(product);
                    break;
                } else {
                    System.out.println(red + "   ❌ INVALID ID" + reset);
                }
            } catch (NumberFormatException e) {
                System.out.println(red + "   ❌ INVALID ID, ENTER A VALID ID." + reset);
            }
        }

        try {
            String choice;
            do {
                System.out.println("\nWHICH OPERATION OF EDIT YOU WANT TO USE?");
                System.out.println("[ SE ]. EDIT SINGLE ELEMENT");
                System.out.println("[ ME ]. EDIT MULTIPLE ELEMENTS");
                System.out.println("[ AE ]. EDIT ALL ELEMENTS");
                System.out.println("[ B ]. BACK TO THE MENU\n");
                System.out.print("⏩⏩ ENTER YOUR CHOICE : ");
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
                    default:{
                        System.out.println( red + "   ❌ INVALID OPTION!!" + reset);
                        clickEnter();
                    }
                }
            } while (!choice.equals("B"));

        } catch (Exception e) {

            System.out.println(red + "   ❌ INVALID INPUT");
            System.out.println("ENTER THE CORRECT FORMAT ..." + reset);
            scanner.nextLine();
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
                            System.out.println(" ✅ PRODUCT HAS BEEN DELETED SUCCESSFULLY");
                        } else if (op.equalsIgnoreCase("n")) {
                            System.out.println(" 🏠 BACK TO MENU...");
                        } else {
                            System.out.println(red + "   ❌ INVALID OPTION" + reset);
                        }
                        break;
                    }
                }

                if (!found) {
                    System.out.println(red + "   ❌ PRODUCT NOT FOUND" + reset);
                    System.out.print("ℹ️ ENTER 'Y' TO ENTER PRODUCT ID AGAIN, OR PRESS ANY KEY TO BACK TO APPLICATION MENU: ");
                    String choice = scanner.nextLine();
                    if (!choice.equalsIgnoreCase("Y")) {
                        System.out.println(" 🏠 BACK TO APPLICATION MENU...");
                        break;
                    }
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(red + "   ❌ INVALID FORMAT" + reset);
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
            productDaoImpl.display(matchingProducts,setRow,scanner);
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
                String input = scanner.nextLine().trim();

                // Check if the input contains at least one letter
                boolean containsLetter = false;
                for (char c : input.toCharArray()) {
                    if (Character.isLetter(c)) {
                        containsLetter = true;
                        break;
                    }
                }

                if (!containsLetter) {
                    inputRow = Integer.parseInt(input);
                    if (inputRow > 0) {
                        productDaoImpl.setUpRow(inputRow, setRow);
                        setRow = inputRow;
                        break;
                    } else {
                        System.out.println(red + "   ❌ NUMBERS OF ROWS MUST BE GREATER THAN 0!!!!!" + reset);
                    }
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println(red + "   ❌ PLEASE ENTER A VALID INTEGER VALUE!!!!!" + reset);
            } catch (NumberFormatException e) {
                System.out.println(red + "   ❌ PLEASE ENTER A VALID INTEGER VALUE!!!!!" + reset);
            }
        } while (true);
    }

    public void BackUpFile() throws IOException {
        String source = "src/AllFile/dataFile.txt";
        String target = "src/backupfiles";
        isContinue = true;
        while(isContinue){
            System.out.print("ℹ️ ARE YOU SURE TO BACK UP THE FILE ? [Y/N] : ");
            String ans = scanner.nextLine();
            if(ans.equalsIgnoreCase("Y")){
                backUpFileProcessImpl.performBackup(source,target);
                isContinue = false;
            }else if(ans.equalsIgnoreCase("N")){
                System.out.println(" 🏠 BACK TO APPLICATION MENU...\n\n");
                isContinue = false;
            }else{
                System.out.println( red + "   ❌ INVALID OPTION" +reset);
            }
        }
    }


    public void exitProgram(){
        isContinue = true;
        while(isContinue){
            System.out.print("ℹ️ ARE YOU SURE TO EXIT THE PROGRAM ? [Y/N] : ");
            String ans = scanner.nextLine();
            if(ans.equalsIgnoreCase("Y")){
                System.out.println("\n  🙏 THANK YOU FOR USING OUR PROGRAM!\n\n\t╰┈➤ EXITING THE PROGRAM....");
                System.exit(0);
                isContinue = false;
            }else if(ans.equalsIgnoreCase("N")){
                System.out.println(" 🏠 BACK TO MENU APPLICATION...\n\n");
                isContinue = false;
            }else{
                System.out.println(red + "   ❌ INVALID OPTION" + reset);
            }
        }
    }

}
