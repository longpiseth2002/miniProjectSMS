package dao;

import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import views.BoxBorder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static dao.BackgroundProcessImpl.*;
import static views.BoxBorder.*;

public class Random {
    private static Random random;
    private static BackgroundProcess backgroundProcess=BackgroundProcessImpl.createObject();
    public void random(List<Product> productslist, String filename, Scanner input) throws IOException {
        try {
            Table table = new Table(3, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.SURROUND);
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            table.setColumnWidth(0, 20, 25);
            table.setColumnWidth(1, 20, 25);
            table.setColumnWidth(2, 20, 25);
            table.addCell(cyan + "W.Write", cellStyle);
            table.addCell(cyan + "R.Read", cellStyle);
            table.addCell(cyan + "B.Back" + reset, cellStyle);
            String checkRdCommit="";
            if ( backgroundProcess.commitCheck("src/allFile/TransectionFile.txt", input)) {
                backgroundProcess.commit(productslist, "src/allFile/TransectionFile.txt", "src/allFile/dataFile.txt", "random", input);
            }
            if(checkRdCommit.equalsIgnoreCase("b")){
                return;
            }
            //loop for hold write, read operation
            outloop:
            do {
                String wrOption = "";
                do {
                    System.out.println(table.render());
                    System.out.print("➡️CHOOSE OPTION : ");
                    wrOption = input.nextLine();
                } while (!(wrOption.equalsIgnoreCase("w") || wrOption.equalsIgnoreCase("r") || wrOption.equalsIgnoreCase("b")));

                if (wrOption.equalsIgnoreCase("b")) {
                    System.out.println(" 🏠 BACK TO APPLICATION MENU...\n\n");
                    break outloop;
                }
                //if user choose write option
                if (wrOption.equalsIgnoreCase("w")) {
                    String lastId = null;
                    String op = null;
                    int n = 0;
                    //loop for validate number of record to write
                    do {
                        try {
                            System.out.print("⏩  ENTER NUMBERS OF RECORD [ 1-30M ] : ");
                            n = Integer.parseInt(input.nextLine());
                        } catch (Exception e) {
                            System.out.println(red + "   ❌INVALID INPUT" + reset);
                        }
                    } while (n < 1 || n > 30000000);
                    boolean apov = false;
                    String stDigit = Integer.toString(n);
                    int digit = stDigit.length();
                    int divi = (digit > 3) ? (int) Math.pow(10, digit - 3) : 1;
                    int remain = n % divi;
                    int repeatNumber;
                    //check use choose to append or override
                    do {
                        System.out.println(cyan + "[A] : APPEND  ||  [O] : OVERRIDE " + reset);
                        System.out.print("➡️ENTER OPTION : ");
                        op = input.nextLine();
                        if(op==null) input.nextLine();
                        apov = op.equalsIgnoreCase("a");
                    } while (!(op.equalsIgnoreCase("a") || op.equalsIgnoreCase("o")));
                    String wrirteCheck = "";
                    Table table1 = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                    CellStyle centerStyle = new CellStyle(CellStyle.HorizontalAlign.center);
                    table1.setColumnWidth(0, 30, 35);
                    table1.setColumnWidth(1, 30, 35);
                    table1.addCell(cyan + "  S.Start writing",centerStyle);
                    table1.addCell(cyan + "  B.Back" + reset,cellStyle);
                        //back or start write
                    do {
                        System.out.println(table1.render());
                        System.out.print("⏩ CHOOSE OPTION : ");
                        wrirteCheck = input.nextLine();
                        if(wrirteCheck==null) input.nextLine();
                    } while (!(wrirteCheck.equalsIgnoreCase("s") || wrirteCheck.equalsIgnoreCase("b")));
                    //start random write
                    if (wrirteCheck.equalsIgnoreCase("s")) {
                        long start = System.nanoTime();
                        Product obj;
                        obj = new Product("coca", 2.5, 12);
                        int first = (apov) ? backgroundProcess.readFromFile("src/allFile/lastId.txt") + 1 : 1;
                        String line = "," + obj.getName() + "," + obj.getUnitPrice() + "," + obj.getQty() + "," + obj.getImportAt() + "\n";
                        String date = String.valueOf(LocalDate.now());
                        int id = 0;
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, apov))) {
                            for (int i = 0; i < n; i++) {
                                id = first + i;
                                writer.write(id + line);
                                if (i % divi == remain) {
                                    repeatNumber = (int) (i / (n / 100f));
                                    System.out.printf("\r\u001B[31m[ %d/%d ] %s%s[ %.2f%% ]", i, n, "█".repeat(repeatNumber), "\u001B[37m▒".repeat(100 - repeatNumber), i / (n / 100f));
                                }
                            }
                            System.out.printf(blue + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", n, n, "\u001B[35m█".repeat(100), 100f);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        long end = System.nanoTime();
                        System.out.println(blue + "\nDATA WRITTEN TO FILE SUCCESSFULLY .");
                        System.out.printf(reset + "\uD83D\uDD52 TIME = %.0fms  = %.3fs\n" , (double) (end - start) / 1000000 ,(((double) (end - start) / 1000000000) ) );
                        productslist.clear();
                        if (apov) {
                            product().setLastAssignedId(id);
                            backgroundProcess.writeSizeToFile(id, "src/allFile/lastId.txt");
                        } else {
                            product().setLastAssignedId(n);
                            backgroundProcess.writeSizeToFile(n, "src/allFile/lastId.txt");
                        }
                    } else {
                        continue outloop;
                    }

                } else {
                    //read operation
                    if (Files.exists(Paths.get(filename))) {
                        int n = (int) countLines(filename);
                        if(n>0){
                            String stDigit = Integer.toString(n);
                            int digit = stDigit.length();
                            int divi = (digit > 3) ? (int) Math.pow(10, digit - 3) : 1;
                            int remain = n % divi;
                            AtomicInteger repeatNumber = new AtomicInteger();
                            Table table1 = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            CellStyle cellStyle1 = new CellStyle(CellStyle.HorizontalAlign.center);
                            table1.setColumnWidth(0, 30, 35);
                            table1.setColumnWidth(1, 30, 35);
                            table1.addCell(cyan + "  S.Start reading" , cellStyle1);
                            table1.addCell(cyan + "  B.Back" + reset,cellStyle1);

                            String startAndBack = null;
                            do {
                                System.out.println(table1.render());
                                System.out.print("⏩ CHOOSE OPTION : ");
                                wrOption = input.nextLine();
                            } while (!(wrOption.equalsIgnoreCase("s") || wrOption.equalsIgnoreCase("b")));
                            //start read operation
                            if (wrOption.equalsIgnoreCase("s")) {
                                long start = System.nanoTime();
                                productslist.clear();
                                AtomicInteger i = new AtomicInteger();
                                //read file using stream
                                try (Stream<String> lines = Files.lines(Paths.get(filename))) {
                                    lines.parallel().forEach(line -> {
                                        String[] parts = split(line, ',');
                                        productslist.add(new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), convertToDate(parts[4])));
                                        i.getAndIncrement();
                                        if (i.get() % divi == remain) {
                                            int progress = (i.get() >= n) ? 100 : (i.get() <= 0) ? 0 : (int) (i.get() / (n / 100f));
                                            repeatNumber.set(progress);
                                            System.out.printf("\r\u001B[31m[ %d/%d ] %s%s[ %.2f%% ]", i.get(), n, "█".repeat(repeatNumber.get()), "\u001B[37m▒".repeat(100 - repeatNumber.get()), i.get() / (n / 100f));
                                        }
                                    });

                                    System.out.printf(blue + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", n, n, "\u001B[35m█".repeat(100), 100f);
                                    System.out.println(reset);
                                }
                                long end = System.nanoTime();
                                System.out.println(blue + "COMPLETE.");
                                System.out.printf(reset + "\uD83D\uDD52 TIME = %.0fms  = %.3fs\n" , (double) (end - start) / 1000000 ,(((double) (end - start) / 1000000000) ) );
                                ProductDaoImpl.setSORT(true);
                            } else {
                                continue outloop;
                            }
                        }else{
                            System.out.println(red + "   ❌ NO DATA TO READ..!");
                        }
                    } else {
                        System.out.println(yellow + "   ⚠️NO DATA FILE");
                        System.out.println(blue + "\uD83D\uDC49 WRITE DATA FIRST OR " + red + "BACK" + reset);
                    }
                }
            } while (true);
        }catch (OutOfMemoryError e){
            System.out.println(yellow + "   ⛔ FULL OF MEMORY" + reset);
        }
    }
    public static Random createObject(){
        if(random==null){
            return  new Random();
        }
        return random;
    }
    private Random(){};
}