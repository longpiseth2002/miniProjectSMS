package dao;

import com.sun.source.tree.BreakTree;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import views.BoxBorder;


import java.util.*;

public class ProductDaoImpl implements ProductDao, BoxBorder {
    private static boolean ORDER = true;
    private static BackgroundProcessImpl process = BackgroundProcessImpl.createObject();
    private static boolean SORT = false;

    public static void setSORT(boolean sort) {
        SORT = sort;
    }
    //a feature to display all product and include the navigation page feature
    @Override
    public void display(List<Product> productList, int numberOfRow, Scanner input) {
        if (!productList.isEmpty()) {
            if (SORT) {
                productList.sort(Comparator.comparingInt(Product::getId));
                SORT = false;
            }
            int numberOfAllData = productList.size();
            int remain = numberOfAllData % numberOfRow;
            int numberOfPage = remain == 0 ? numberOfAllData / numberOfRow : numberOfAllData / numberOfRow + 1;

            changeOrder:
            while (true) {
                int numberOfCurrentPage = 1;
                int numberOfRowStart;
                int numberOfRowEnd;
                if (ORDER) {
                    numberOfRowStart = 0;
                    numberOfRowEnd = Math.min(numberOfRow, numberOfAllData);
                } else {
                    numberOfRowStart = numberOfAllData - 1;
                    numberOfRowEnd = Math.max(numberOfAllData - numberOfRow, 0);
                }
                boolean stepCheck = true;
                System.out.println(HORIZONTAL_CONNECTOR_BORDER.repeat(53) + "  DISPLAY INFORMATION OF PRODUCTS  " + HORIZONTAL_CONNECTOR_BORDER.repeat(53));
                System.out.println();
                do {
                    if (stepCheck) {
                        Table table = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
                        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
                        table.setColumnWidth(0, 25, 30);
                        table.setColumnWidth(1, 25, 30);
                        table.setColumnWidth(2, 25, 30);
                        table.setColumnWidth(3, 25, 30);
                        table.setColumnWidth(4, 25, 30);

                        table.addCell(darkRed + "  CODE ", cellStyle);
                        table.addCell(darkRed + "  NAME ", cellStyle);
                        table.addCell(darkRed + "  UNIT PRICE ", cellStyle);
                        table.addCell(darkRed + "  QTY ", cellStyle);
                        table.addCell(darkRed + "  IMPORTED AT ", cellStyle);

                        if (ORDER) {
                            // Display from index 0 to end
                            for (int i = numberOfRowStart; i < numberOfRowEnd; i++) {
                                table.addCell(darkBlue + "CSTAD-" + productList.get(i).getId() + "", cellStyle);
                                table.addCell(darkBlue + productList.get(i).getName(), cellStyle);
                                table.addCell(darkBlue + productList.get(i).getUnitPrice() + "", cellStyle);
                                table.addCell(darkBlue + productList.get(i).getQty() + "", cellStyle);
                                table.addCell(darkBlue + productList.get(i).getImportAt() + "", cellStyle);
                            }
                        } else {

                            for (int i = numberOfRowStart; i >= numberOfRowEnd; i--) {
                                table.addCell(darkBlue + "CSTAD-" + productList.get(i).getId() + "", cellStyle);
                                table.addCell(darkBlue + productList.get(i).getName(), cellStyle);
                                table.addCell(darkBlue + productList.get(i).getUnitPrice() + "", cellStyle);
                                table.addCell(darkBlue + productList.get(i).getQty() + "", cellStyle);
                                table.addCell(darkBlue + productList.get(i).getImportAt() + "", cellStyle);
                            }
                        }

                        System.out.println(table.render());
                        System.out.println(HORIZONTAL_CONNECTOR_BORDER.repeat(141));
                        String textBlock = darkMagenta + """
                            PAGE %d OF %d                                                                                                 TOTAL RECORD : %d
                            PAGE NAVIGATION                [ [O]:ORDER || [N]:NEXT || [P]:PREVIOUS || [G]:GOTO || [L]:LAST || [F]:FIRST || [B]:BACK TO APPLICATION MENU ]
                            """ + reset;

                        System.out.print(String.format(textBlock, numberOfCurrentPage, numberOfPage, numberOfAllData));
                        System.out.println(HORIZONTAL_CONNECTOR_BORDER.repeat(141));
                    }

                    System.out.print("⏩ NAVIGATION PAGE : ");
                    String op = input.nextLine().toUpperCase();
                    System.out.println("\n");
                    if (ORDER) {
                        // Display from index 0 to end
                        switch (op) {
                            case "N" -> {
                                if (numberOfRowEnd < numberOfAllData) {
                                    numberOfCurrentPage++;
                                    numberOfRowStart += numberOfRow;
                                    if (remain == 0)
                                        numberOfRowEnd += numberOfRow;
                                    else {
                                        if (numberOfCurrentPage != numberOfPage) numberOfRowEnd += numberOfRow;
                                        else {
                                            numberOfRowEnd += remain;
                                        }
                                    }
                                    stepCheck = true;
                                } else {
                                    stepCheck = false;
                                    System.out.println(yellow + " ⚠️ ALREADY ON LAST PAGE.\n PLEASE ENTER ANOTHER OPTION [ [F]:FIRST || [P]:PREVIOUS ]." + reset);
                                }
                            }
                            case "P" -> {
                                if (numberOfRowStart > 0) {
                                    numberOfRowStart -= numberOfRow;
                                    if (remain == 0)
                                        numberOfRowEnd -= numberOfRow;
                                    else {
                                        if (numberOfCurrentPage != numberOfPage) {
                                            numberOfRowEnd -= numberOfRow;
                                        } else {
                                            numberOfRowEnd -= remain;
                                        }
                                    }
                                    numberOfCurrentPage--;
                                    stepCheck = true;
                                } else {
                                    stepCheck = false;
                                    System.out.println(yellow + " ⚠️ ALREADY ON FIRST PAGE.\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ [F]:FIRST || [P]:PREVIOUS ]." + reset);
                                }
                            }
                            case "F" -> {
                                if (numberOfCurrentPage != 1) {
                                    numberOfCurrentPage = 1;
                                    numberOfRowStart = 0;
                                    numberOfRowEnd = numberOfRow;
                                    stepCheck = true;
                                } else {
                                    stepCheck = false;
                                    System.out.println(yellow + " ⚠️ ALREADY ON FIRST PAGE.\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ [F]:FIRST || [P]:PREVIOUS ]." + reset);
                                }
                            }
                            case "L" -> {
                                if (numberOfCurrentPage != numberOfPage) {
                                    numberOfCurrentPage = numberOfPage;
                                    numberOfRowStart = remain == 0 ? numberOfAllData - numberOfRow : numberOfAllData - remain;
                                    numberOfRowEnd = numberOfAllData;
                                    stepCheck = true;
                                } else {
                                    stepCheck = false;
                                    System.out.println(yellow + " ⚠️ ALREADY ON LAST PAGE.\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ [N]:NEXT || [L]:LAST ]." + reset);
                                }
                            }
                            case "G" -> {
                                System.out.print("⏩⏩ ENTER PAGE NUMBERS [1 TO " + numberOfPage + "] : ");
                                do {
                                    try {
                                        int pageNumber = input.nextInt();
                                        if (pageNumber != numberOfCurrentPage && pageNumber >= 1 && pageNumber <= numberOfPage) {
                                            numberOfCurrentPage = pageNumber;
                                            numberOfRowStart = (pageNumber - 1) * numberOfRow;
                                            numberOfRowEnd = numberOfRowStart + ((remain == 0) ? numberOfRow : (numberOfCurrentPage == numberOfPage) ? remain : numberOfRow);
                                            stepCheck = true;
                                        } else {
                                            stepCheck = false;
                                            if(pageNumber == numberOfPage){
                                                System.out.println(yellow + " ⚠️ ALREADY ON PAGE " + pageNumber + ".\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ PAGE " + numberOfPage + " ]." + reset);

                                            }
                                            if(pageNumber == 1){
                                                System.out.println(yellow + " ⚠️ ALREADY ON PAGE " + pageNumber + ".\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ PAGE " + pageNumber + " ]." + reset);
                                            }
                                        }
                                        break;
                                    } catch (Exception e) {
                                        System.out.println(yellow + " ⚠️ INCORRECT INPUT. PLEASE ENTER A VALID NUMBER." + reset);
                                        System.out.print("ENTER PAGE NUMBER [1 TO " + numberOfPage + "] : ");
                                    }
                                    input.nextLine();
                                } while (true);
                            }
                            case "B" -> {
                                System.out.println(" 🏠 BACK TO APPLICATION MENU...\n\n");
                                return;
                            }
                            case "O" -> {
                                ORDER = false;
                                stepCheck = true;
                                continue changeOrder;
                            }
                            default -> {
                                System.out.println(yellow + "  ⚠️INVALID INPUT !!!! PLEASE ENTER AGAIN .\n      YOU CAN SELECT THESE OPTIONS\n     [ N ] -> NEXT\n     [ P ] -> PREVIOUS\n     [ G ] -> GOTO\n     [ L ] -> LAST\n     [ F ] -> FIRST\n     [ B ] -> BACK TO APPLICATION MENU \n" + reset);
                                stepCheck = false;
                            }
                        }
                    } else {
                        switch (op) {
                            case "N" -> {
                                if (numberOfRowEnd > 0) {
                                    numberOfCurrentPage++;
                                    numberOfRowStart -= numberOfRow;
                                    if (remain == 0)
                                        numberOfRowEnd -= numberOfRow;
                                    else {
                                        if (numberOfCurrentPage != numberOfPage) numberOfRowEnd -= numberOfRow;
                                        else {
                                            numberOfRowEnd -= remain;
                                        }
                                    }
                                    stepCheck = true;
                                } else {
                                    stepCheck = false;
                                    System.out.println(yellow + " ⚠️ ALREADY ON LAST PAGE.\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ [N]:NEXT || [L]:LAST ]."+ reset);
                                }
                            }
                            case "P" -> {
                                if (numberOfRowStart < numberOfAllData - 1) {
                                    numberOfRowStart += numberOfRow;
                                    if (remain == 0)
                                        numberOfRowEnd += numberOfRow;
                                    else {
                                        if (numberOfCurrentPage != numberOfPage) {
                                            numberOfRowEnd += numberOfRow;
                                        } else {
                                            numberOfRowEnd += remain;
                                        }
                                    }
                                    numberOfCurrentPage--;
                                    stepCheck = true;
                                } else {
                                    stepCheck = false;
                                    System.out.println(yellow + " ⚠️ ALREADY ON FIRST PAGE.\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ [F]:FIRST || [P]:PREVIOUS ]."+ reset);
                                }
                            }
                            case "F" -> {
                                if (numberOfCurrentPage != 1) {
                                    numberOfCurrentPage = 1;
                                    numberOfRowStart = numberOfAllData - 1;
                                    numberOfRowEnd = numberOfAllData - numberOfRow;
                                    stepCheck = true;
                                } else {
                                    stepCheck = false;
                                    System.out.println(yellow + " ⚠️ ALREADY ON FIRST PAGE. \n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ [F]:FIRST || [P]:PREVIOUS ]." + reset);
                                }
                            }
                            case "L" -> {
                                if (numberOfCurrentPage != numberOfPage) {
                                    numberOfCurrentPage = numberOfPage;
                                    numberOfRowStart = numberOfAllData - (numberOfRow * (numberOfPage - 1)) - 1;
                                    numberOfRowEnd = 0;
                                    stepCheck = true;
                                } else {
                                    stepCheck = false;
                                    System.out.println(yellow + " ⚠️ ALREADY ON LAST PAGE.\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ [N]:NEXT || [L]:LAST ]." + reset);
                                }
                            }
                            case "G" -> {
                                System.out.print("⏩⏩ ENTER PAGE NUMBER [1 TO " + numberOfPage + "] : ");
                                do {
                                    try {
                                        int pageNumber = input.nextInt();
                                        if (pageNumber != numberOfCurrentPage && pageNumber >= 1 && pageNumber <= numberOfPage) {
                                            numberOfCurrentPage = pageNumber;
                                            numberOfRowStart = numberOfAllData - ((numberOfRow * (numberOfCurrentPage - 1))) - 1;
                                            numberOfRowEnd = numberOfRowStart - ((remain == 0) ? numberOfRow : (numberOfCurrentPage == numberOfPage) ? remain : numberOfRow) + 1;
                                            stepCheck = true;
                                        } else {
                                            stepCheck = false;
                                            if(pageNumber == numberOfPage){
                                                System.out.println(yellow + " ⚠️ ALREADY ON PAGE " + pageNumber + ".\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ PAGE " + numberOfPage + " ]." + reset);

                                            }
                                            if(pageNumber == 1){
                                                System.out.println(yellow + " ⚠️ ALREADY ON PAGE " + pageNumber + ".\n PLEASE ENTER ANOTHER OPTION DIFFERENCE [ PAGE " + pageNumber + " ]." + reset);
                                            }
                                        }
                                        break;
                                    } catch (Exception e) {
                                        System.out.println( yellow + " ⚠️ INCORRECT INPUT. PLEASE ENTER A VALID NUMBER." + reset);
                                        System.out.print("ENTER PAGE NUMBER (1 TO " + numberOfPage + "): ");
                                    }
                                    input.nextLine();
                                } while (true);
                            }
                            case "B" -> {
                                System.out.println(" 🏠 BACK TO APPLICATION MENU...\n\n");
                                return;
                            }
                            case "O" -> {
                                ORDER = true;
                                stepCheck = true;
                                continue changeOrder;
                            }
                            default -> {
                                System.out.println(yellow + "  ⚠️INVALID INPUT !!!! PLEASE ENTER AGAIN .\n      YOU CAN SELECT THESE OPTIONS\n     [ N ] -> NEXT\n     [ P ] -> PREVIOUS\n     [ G ] -> GOTO\n     [ L ] -> LAST\n     [ F ] -> FIRST\n     [ B ] -> BACK TO APPLICATION MENU \n" + reset);
                                stepCheck = false;
                            }
                        }
                    }
                    if (op.equalsIgnoreCase("G")) input.nextLine();
                } while (true);
            }
        } else {
            System.out.println(yellow + " ⚠️ NO DATA TO DISPLAY" + reset);
        }
    }

    //write new product feature
    @Override
    public void write(Product product, List<Product> productList, String status) {
        try {
            productList.add(product);
            process.writeToFile(product, status);
            process.writeSizeToFile(product.getId(), "src/allFile/lastId.txt");
        } catch (Exception e) {

        }
    }

    //read a single product by id
    @Override
    public Product read(int proId, List<Product> productList) {
        Optional<Product> product = selectById(proId, productList);
        Product foundProduct = null;
        if (product != null) {
            foundProduct = product.get();
        }
        return foundProduct;
    }

    //compare product id if the id is exist in the productlist
    @Override
    public Optional<Product> selectById(int id, List<Product> productList) {
        try {
            for (Product product : productList) {
                if (product.getId() == (id)) {
                    return Optional.of(product);
                }
            }
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
        return null;
    }


    //delete product by id
    @Override
    public Product deleteById(int id, List<Product> products) {
        Optional<Product> product = selectById(id, products);
        Product p = null;
        if (product != null) {
            p = product.get();
            process.writeToFile(p, "delete");
            products.removeIf(pro -> pro.getId() == id);
        }
        return p;
    }
    //search product by product name
    @Override
    public List<Product> searchByName(List<Product> products, String name) {
        List<Product> productList = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().contains(name)) {
                productList.add(p);
            }
        }
        return productList;
    }
    //set number of row to display product in DISPLAY feature
    public void setUpRow(int inputRow, int setRow) {

        if (inputRow >= 1) {
            setRow = inputRow;
            System.out.println(green + "   \uD83E\uDD16 SETTING ROW SUCCESSFULLY ✅ " + reset);
            System.out.println(HORIZONTAL_CONNECTOR_BORDER.repeat(150));
            System.out.println();
            return;
        }
    }
}
