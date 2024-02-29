package dao;

import com.sun.source.tree.BreakTree;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import views.BoxBorder;



import java.util.*;

public class ProductDaoImpl implements ProductDao , BoxBorder {
    private static boolean  ORDER=true;
    private static BackgroundProcessImpl process = BackgroundProcessImpl.createObject();



    @Override
    public void display(List<Product> productList, int numberOfRow, Scanner input) {
        int numberOfAllData = productList.size();
        int remain = numberOfAllData % numberOfRow;
        int numberOfPage = remain == 0 ? numberOfAllData / numberOfRow : numberOfAllData / numberOfRow + 1;

        changeOrder:
        while (true){
            int numberOfCurrentPage = 1;
            int numberOfRowStart;
            int numberOfRowEnd;
            if(ORDER){
                numberOfRowStart = 0;
                numberOfRowEnd = Math.min(numberOfRow, numberOfAllData);
            }else{
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


                    table.addCell("  CODE ", cellStyle);
                    table.addCell("  NAME ", cellStyle);
                    table.addCell("  UNIT PRICE ", cellStyle);
                    table.addCell("  QTY ", cellStyle);
                    table.addCell("  IMPORTED AT ", cellStyle);
                    if(ORDER){
                        //Display from index 0 to end
                        for (int i = numberOfRowStart; i < numberOfRowEnd ; i++) {
                            table.addCell(String.valueOf(productList.get(i).getId()), cellStyle);
                            table.addCell(productList.get(i).getName(), cellStyle);
                            table.addCell(String.valueOf(productList.get(i).getUnitPrice()), cellStyle);
                            table.addCell(String.valueOf(productList.get(i).getQty()), cellStyle);
                            table.addCell(String.valueOf(productList.get(i).getImportAt()), cellStyle);

                        }
                    }
                    else{
                        //Display from index end to 0
                        for (int i = numberOfRowStart; i >= numberOfRowEnd; i--) {
                            table.addCell(String.valueOf(productList.get(i).getId()), cellStyle);
                            table.addCell(productList.get(i).getName(), cellStyle);
                            table.addCell(String.valueOf(productList.get(i).getUnitPrice()), cellStyle);
                            table.addCell(String.valueOf(productList.get(i).getQty()), cellStyle);
                            table.addCell(String.valueOf(productList.get(i).getImportAt()), cellStyle);
                        }
                    }

                    System.out.println(table.render());
                    System.out.println(HORIZONTAL_CONNECTOR_BORDER.repeat(140));
                    String textBlock = """
                        Page %d of %d                                                                                                 Total record:%d
                        Page Navigation                (O):order || (N):next || (P):Previous || (G):Goto || (L):last || (F):First || (B):BACK TO APPLICATION MENU
                        """;

                    System.out.print(String.format(textBlock, numberOfCurrentPage, numberOfPage, numberOfAllData));
                    System.out.println(HORIZONTAL_CONNECTOR_BORDER.repeat(140));
                }

                System.out.print("⏩ Navigation page : ");
                String op = input.nextLine().toUpperCase();
                System.out.println("\n");
                if(ORDER){
                    //Display from index 0 to end
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
                            }
                        }
                        case "G" -> {
                            System.out.print("⏩⏩ Enter page number(1 to " + numberOfPage + "): ");
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
                                    }
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Incorrect input");
                                    System.out.print("⏩⏩ Enter page number(1 to " + numberOfPage + "): ");
                                }
                                input.nextLine();
                            } while (true);
                        }
                        case "B" -> {
                            return;
                        }
                        case "O" -> {
                            ORDER=false;
                            stepCheck=true;
                            continue changeOrder;
                        }
                        default -> {
                            System.out.println("  ⚠️INVALID INPUT !!!! PLEASE ENTER AGAIN .\n      YOU CAN SELECT THESE OPTIONS\n      N -> next\n      P -> Previous\n      G -> Goto\n      L -> last\n      F -> First\n      B -> BACK TO APPLICATION MENU \n");
                            stepCheck = false;
                        }
                    }
                }else{
                    //Display from index end to 0
                    switch (op) {
                        case "N" -> {
                            if (numberOfRowEnd >0) {
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
                            }
                        }
                        case "P" -> {

                            if (numberOfRowStart <numberOfAllData-1) {
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
                            }
                        }
                        case "F" -> {
                            if (numberOfCurrentPage != 1) {
                                numberOfCurrentPage = 1;
                                numberOfRowStart = numberOfAllData-1;
                                numberOfRowEnd = numberOfAllData - numberOfRow;
                                stepCheck = true;
                            } else {
                                stepCheck = false;
                            }
                        }
                        case "L" -> {
                            if (numberOfCurrentPage != numberOfPage) {
                                numberOfCurrentPage = numberOfPage;
                                numberOfRowStart = numberOfAllData-(numberOfRow*(numberOfPage-1))-1;
                                numberOfRowEnd =0;
                                stepCheck = true;
                            } else {
                                stepCheck = false;
                            }
                        }
                        case "G" -> {
                            System.out.print("⏩⏩ Enter page number(1 to " + numberOfPage + "): ");
                            do {
                                try {
                                    int pageNumber = input.nextInt();
                                    if (pageNumber != numberOfCurrentPage && pageNumber >= 1 && pageNumber <= numberOfPage) {
                                        numberOfCurrentPage = pageNumber;
                                        numberOfRowStart =numberOfAllData-((numberOfRow * (numberOfCurrentPage - 1)))-1;
                                        numberOfRowEnd = numberOfRowStart - ((remain == 0) ? numberOfRow : (numberOfCurrentPage == numberOfPage) ? remain : numberOfRow)+1;

                                        stepCheck = true;
                                    } else {
                                        stepCheck = false;
                                    }
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Incorrect input");
                                    System.out.print("⏩⏩ Enter page number(1 to " + numberOfPage + "): ");
                                }
                                input.nextLine();
                            } while (true);
                        }
                        case "B" -> {
                            return;
                        }
                        case "O" -> {
                            ORDER=true;
                            stepCheck=true;
                            continue changeOrder;
                        }
                        default -> {
                            System.out.println("  ⚠️INVALID INPUT !!!! PLEASE ENTER AGAIN .\n      YOU CAN SELECT THESE OPTIONS\n      N -> next\n      P -> Previous\n      G -> Goto\n      L -> last\n      F -> First\n      B -> BACK TO APPLICATION MENU \n");
                            stepCheck = false;
                        }
                    }
                }
                if (op.equalsIgnoreCase("G")) input.nextLine();
            } while (true);
        }
    }

    @Override
    public void write(Product product,List<Product> productList,String status) {
        productList.add(product);
        process.writeToFile(product,status);
    }


    @Override
    public Product read(Integer proId, List<Product> productList) {
        Optional<Product> product = selectById(proId,productList);
        Product foundProduct = null;
        if(product!=null){
            foundProduct = product.get();
        }
        return foundProduct;
    }


    @Override
    public Optional<Product> selectById(Integer id,List<Product> productList) {
        try{
            for(Product product : productList){
                if(product.getId().equals(id)){
                    return Optional.of(product);
                }
            }
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
        return null;
    }



    @Override
    public Product updateById(Product product) {
        return null;
    }

    @Override
    public Product deleteById(Integer id , List<Product> products ) {
        Optional<Product> product = selectById(id,products);
        Product p = null;
        if(product!=null){
            p = product.get();
            products.removeIf(pro -> pro.getId() == id);
        }
        return p;
    }

    @Override
    public List<Product> selectByName(List<Product> products, String name) {

        for (Product p : products){
            if(name.equals(p.getName())){
                System.out.println(p.getName());
            }
        }
        return products;
    }


    @Override
    public Product searchByName(List<Product> products , String searchName) {
        List<Product> product = selectByName(products,searchName);
        if(product!=null){

        }
        return null;
    }



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
