package utils;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

public class Util {
    private static Scanner input = new Scanner(System.in);
    private static int numberOfRow = 5;

    public static void exit() {
        System.exit(0);
    }

    public static void display(List<Integer> list) {
        Scanner input = new Scanner(System.in);
        int numberOfAllData = list.size();
        int remain = numberOfAllData % numberOfRow;
        int numberOfPage = remain == 0 ? numberOfAllData / numberOfRow : numberOfAllData / numberOfRow + 1;
        int numberOfCurrentPage = 1;
        int numberOfRowStart = 0;
        int numberOfRowEnd = Math.min(numberOfRow, numberOfAllData);
        boolean stepCheck = true;
        do {
            if (stepCheck) {
                System.out.println("\n-------------------------------------------------------------------");

                Table table = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);
                CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
                table.setColumnWidth(0, 20, 30);
                table.setColumnWidth(1, 20, 30);
                table.setColumnWidth(2, 20, 30);
                table.setColumnWidth(3, 20, 30);
                table.setColumnWidth(4, 20, 30);



                table.addCell("  CODE " ,cellStyle);
                table.addCell("  NAME " ,cellStyle);
                table.addCell("  UNIT PRICE " ,cellStyle);
                table.addCell("  QTY " ,cellStyle);
                table.addCell("  IMPORTED AT " ,cellStyle);
                for (int i = numberOfRowStart; i < numberOfRowEnd; i++) {
                    table.addCell("CODE[" + i + "]=" + list.get(i) ,cellStyle);
                    table.addCell(" PRODUCT ::" + i ,cellStyle);
                    table.addCell(" $500 " ,cellStyle);
                    table.addCell(" " + i ,cellStyle);
                    table.addCell(" 2024-12-09 " ,cellStyle);

                }
                System.out.println(table.render());
                System.out.println("-------------------------------------------------------------------");
                String textBlock = """
                        *********************************************************************************************
                        Page %d of %d                                                            Total record:%d
                        Page Navigation                 (N):next || (P):Previous || (G):Goto || (L):last || (F):First
                        *********************************************************************************************
                        """;
                System.out.print(String.format(textBlock, numberOfCurrentPage, numberOfPage, numberOfAllData));
            }
            System.out.print("⏩ (B)ack or Navigation page:");
            String op = input.nextLine().toUpperCase();
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
                default -> stepCheck = false;
            }
            if (op.equalsIgnoreCase("G")) input.nextLine();
        } while (true);
    }


    // help method
    public static void displayHelp() {
        System.out.println("# Help Instruction");
        Table table = new Table(1, BorderStyle.CLASSIC_COMPATIBLE_WIDE, ShownBorders.SURROUND);
        table.addCell("1.      Press       l : Display product as table");
        table.addCell("2.      Press       w : Create a new product");
        table.addCell("3.      Press       r : View product details by code");
        table.addCell("4       Press       e : Edit an existing product by code");
        table.addCell("5.      Press       d : Delete an existing product by code");
        table.addCell("6.      Press       s : Search an existing product by name");
        table.addCell("7.      Press       c : Commit transaction data");
        table.addCell("8.      Press       k : Backup data");
        table.addCell("9.      Press       t : Restore data");
        table.addCell("10.     Press       f : Navigate pagination to the last page");
        table.addCell("11.     Press       p : Navigate pagination to the previous page");
        table.addCell("12.     Press       n : Navigate pagination to the next page");
        table.addCell("13.     Press       1 : Navigate pagination to the first page");
        table.addCell("14.     Press       h : Help");
        table.addCell("15.     Press       b : Step Back of the Application");

        System.out.println(table.render());
    }

    public static void setUpRow() {
        do {
            try {
                System.out.print("⏩.Enter number of row:");
                int inputRow = input.nextInt();
                if (inputRow > 1) {
                    numberOfRow = inputRow;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

}
