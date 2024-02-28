package views;


import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

public class InterfaceViews implements BoxBorder {
    public static void interfaceCSTAD() {
        System.out.println("\n".repeat(5));
        System.out.print(cyan);
        System.out.println(SPACE.repeat(35) + TOP_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(72) + TOP_RIGHT_CONNECTOR_CORNER);
        System.out.println(SPACE.repeat(35) + VERTICAL_CONNECTOR_BORDER + SPACE + TOP_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(68) + TOP_RIGHT_CONNECTOR_CORNER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(4) + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + SPACE.repeat(56) + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + SPACE.repeat(4) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + SPACE.repeat(6) + magenta + " ██████╗███████╗████████╗ █████╗ ██████╗ " + cyan + SPACE.repeat(5) + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + magenta + "██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗" + cyan + SPACE.repeat(5) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + magenta + "██║     ███████╗   ██║   ███████║██║  ██║" + cyan + SPACE.repeat(5) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + magenta + "██║     ╚════██║   ██║   ██╔══██║██║  ██║" + cyan + SPACE.repeat(5) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + magenta + "╚██████╗███████║   ██║   ██║  ██║██████╔╝" + cyan + SPACE.repeat(5) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + magenta + " ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝ " + cyan + SPACE.repeat(5) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + magenta +HORIZONTAL_CONNECTOR_BORDER.repeat(41) + cyan + SPACE.repeat(5) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + magenta + "CENTER OF SCIENCE TECHNOLOGY AND ADVANCED" + cyan + SPACE.repeat(5) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(2) + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE.repeat(21) + magenta + "DEVELOPMENT" + cyan + SPACE.repeat(20) + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE.repeat(2) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(4) +  BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE.repeat(56) + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE.repeat(4) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +VERTICAL_CONNECTOR_BORDER + SPACE + BOTTOM_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(68) + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(35) +BOTTOM_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(72) + BOTTOM_RIGHT_CONNECTOR_CORNER);
        System.out.print(reset);
    }
    public static void applicationMenu (){
        Table table = new Table(7, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0,20,25);
        table.setColumnWidth(1,20,25);
        table.setColumnWidth(2,20,25);
        table.setColumnWidth(3,20,25);
        table.setColumnWidth(4,20,25);
        table.setColumnWidth(5,20,25);
        table.setColumnWidth(6,20,25);

        table.addCell(" ");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(blue + "APPLICATION MENU"  ,cellStyle);
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
        table.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
        table.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
        table.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
        table.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
        table.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));
        table.addCell(HORIZONTAL_CONNECTOR_BORDER.repeat(20));

        table.addCell(cyan + "  (D)Display");
        table.addCell(cyan +"  (RD)Random");
        table.addCell(cyan +"  (W)Write");
        table.addCell(cyan +"  (R)ead");
        table.addCell(cyan +"  (E)Edit");
        table.addCell(cyan +"  (DL)Delete");
        table.addCell(cyan +"  (S)Search");
        table.addCell(cyan +"  (SR)Set row");
        table.addCell(cyan +"  (C)Commit");
        table.addCell(cyan +"  (BU)Back Up");
        table.addCell(cyan +"  (RS)Restore");
        table.addCell(cyan +"  (H)Help");
        table.addCell(cyan +"  (X)Exit" + reset);



        System.out.println(table.render());
    }

    public static void readDetail(Product product){
        Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0,30,40);
        table.setColumnWidth(1,30,40);


        table.addCell("  CODE " + " ".repeat(23) + ": ");
        table.addCell(product.getId().toString());
        table.addCell("  NAME " + " ".repeat(23) + ": ");
        table.addCell(product.getName());
        table.addCell("  UNIT PRICE " + " ".repeat(17) + ": ");
        table.addCell(product.getUnitPrice().toString());
        table.addCell("  QTY " + " ".repeat(24) + ": ");
        table.addCell(product.getQty().toString());
        table.addCell("  IMPORTED AT " + " ".repeat(16) + ": ");
        table.addCell(product.getImportAt().toString());


        System.out.println(table.render());

    }
    public static void informUpdate (){
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0,20,25);
        table.setColumnWidth(1,20,25);
        table.setColumnWidth(2,20,25);
        table.setColumnWidth(3,20,25);
        table.setColumnWidth(4,20,25);


        table.addCell("  1. ALL");
        table.addCell("  2. NAME");
        table.addCell("  3. UNIT PRICE");
        table.addCell("  4. QTY");
        table.addCell("  5. BACK TO MENU");

        System.out.println(table.render());
    }
    public static void displayHelp() {
        System.out.println("# Help Instruction");
        Table table = new Table(1, BorderStyle.UNICODE_BOX, ShownBorders.SURROUND);
        table.addCell("1.      Press       D : Display product as table");
        table.addCell("2.      Press       w : Create a new product");
        table.addCell("3.      Press       R : View product details by code");
        table.addCell("4       Press       E : Edit an existing product by code");
        table.addCell("5.      Press       DL : Delete an existing product by code");
        table.addCell("6.      Press       S : Search an existing product by name");
        table.addCell("7.      Press       C : Commit transaction data");
        table.addCell("8.      Press       BU : Backup data");
        table.addCell("9.      Press       RS : Restore data");
        table.addCell("10.     Press       L : Navigate pagination to the last page");
        table.addCell("11.     Press       P : Navigate pagination to the previous page");
        table.addCell("12.     Press       N : Navigate pagination to the next page");
        table.addCell("13.     Press       F : Navigate pagination to the first page");
        table.addCell("14.     Press       H : Help");
        table.addCell("15.     Press       B : Step Back of the Application");

        System.out.println(table.render());
    }


}
