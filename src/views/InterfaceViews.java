package views;


import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.Scanner;

public class InterfaceViews implements BoxBorder {
    public static void interfaceCSTAD() {
        System.out.println("\n".repeat(5));
        System.out.print(cyan);
        System.out.println(SPACE.repeat(10) + TOP_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(120) + TOP_RIGHT_CONNECTOR_CORNER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + TOP_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(116) + TOP_RIGHT_CONNECTOR_CORNER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(8) + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + SPACE.repeat(96) + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + SPACE.repeat(8) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + SPACE.repeat(10) + magenta + " ██████╗███████╗████████╗ █████╗ ██████╗     ███████╗███╗   ███╗███████╗" + cyan + SPACE.repeat(10) + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + TOP_LEFT_CONNECTOR_CORNER + TOP_RIGHT_CONNECTOR_CORNER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(10) + magenta + "██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗    ██╔════╝████╗ ████║██╔════╝" + cyan + SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(10) + magenta + "██║     ███████╗   ██║   ███████║██║  ██║    ███████╗██╔████╔██║███████╗" + cyan + SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(10) + magenta + "██║     ╚════██║   ██║   ██╔══██║██║  ██║    ╚════██║██║╚██╔╝██║╚════██║" + cyan + SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);

        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(10) + magenta + "╚██████╗███████║   ██║   ██║  ██║██████╔╝    ███████║██║ ╚═╝ ██║███████║" + cyan + SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(10) + magenta + " ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝     ╚══════╝╚═╝     ╚═╝╚══════╝" + cyan + SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(10) + magenta + HORIZONTAL_CONNECTOR_BORDER.repeat(72) + cyan + SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(20) + magenta + "CENTER OF SCIENCE TECHNOLOGY AND ADVANCED DEVELOPMENT" + cyan + SPACE.repeat(19) + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(6) + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE.repeat(35) + magenta + "STOCK MANAGEMENT SYSTEM" + cyan + SPACE.repeat(34) + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + VERTICAL_CONNECTOR_BORDER + VERTICAL_CONNECTOR_BORDER + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE.repeat(6) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER + SPACE.repeat(8) + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE.repeat(96) + BOTTOM_LEFT_CONNECTOR_CORNER + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE.repeat(8) + VERTICAL_CONNECTOR_BORDER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + VERTICAL_CONNECTOR_BORDER + SPACE + BOTTOM_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(116) + BOTTOM_RIGHT_CONNECTOR_CORNER + SPACE + VERTICAL_CONNECTOR_BORDER);
        System.out.println(SPACE.repeat(10) + BOTTOM_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(120) + BOTTOM_RIGHT_CONNECTOR_CORNER);
        System.out.print(reset);

        System.out.println("\n\n");
    }

    public static void applicationMenu() {
        Table table = new Table(7, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0, 20, 25);
        table.setColumnWidth(1, 20, 25);
        table.setColumnWidth(2, 20, 25);
        table.setColumnWidth(3, 20, 25);
        table.setColumnWidth(4, 20, 25);
        table.setColumnWidth(5, 20, 25);
        table.setColumnWidth(6, 20, 25);

        table.addCell(" ");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(darkRed + "APPLICATION MENU", cellStyle);
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

        table.addCell(cyan + "  (D)DISPLAY");
        table.addCell(cyan + "  (RD)RANDOM");
        table.addCell(cyan + "  (W)WRITE");
        table.addCell(cyan + "  (R)READ");
        table.addCell(cyan + "  (E)EDIT");
        table.addCell(cyan + "  (DL)DELETE");
        table.addCell(cyan + "  (S)SEARCH");
        table.addCell(cyan + "  (SR)SET ROW");
        table.addCell(cyan + "  (C)COMMIT");
        table.addCell(cyan + "  (BU)BACK UP");
        table.addCell(cyan + "  (RS)RESTORE");
        table.addCell(cyan + "  (H)HELP");
        table.addCell(cyan + "  (X)EXIT" + reset);


        System.out.println(table.render());
    }

    public static void readDetail(Product product, Scanner scanner) {
        Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0, 30, 35);
        table.setColumnWidth(1, 20, 25);


        table.addCell(darkRed + "      CODE " + " ".repeat(20) + ": ");
        table.addCell(darkBlue + "CSTAD-" + product.getId() + "");
        table.addCell(darkRed + "      NAME " + " ".repeat(20) + ": ");
        table.addCell(darkBlue + product.getName());
        table.addCell(darkRed + "      UNIT PRICE " + " ".repeat(14) + ": ");
        table.addCell(darkBlue + product.getUnitPrice() + "");
        table.addCell(darkRed + "      QTY " + " ".repeat(21) + ": ");
        table.addCell(darkBlue + product.getQty() + "");
        table.addCell(darkRed + "      IMPORTED AT " + " ".repeat(13) + ": ");
        table.addCell(darkBlue + product.getImportAt().toString());


        System.out.println(table.render());
        System.out.print("⌨️CLICK ANY KEY TO CONTINUE : ");
        scanner.nextLine();
    }

    public static void informUpdate() {
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0, 20, 25);
        table.setColumnWidth(1, 20, 25);
        table.setColumnWidth(2, 20, 25);
        table.setColumnWidth(3, 20, 25);
        table.setColumnWidth(4, 20, 25);


        table.addCell("  1. ALL");
        table.addCell("  2. NAME");
        table.addCell("  3. UNIT PRICE");
        table.addCell("  4. QTY");
        table.addCell("  5. BACK TO MENU");


        System.out.println(table.render());
    }

    public void displayHelp(Scanner scanner) {
        System.out.println("\uD83D\uDE4B\u200D\uD83D\uDE4B\uD83C\uDFFB\u200D\uD83D\uDCE2️ HELP INSTRUCTION ");
        System.out.println(TOP_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(75) + TOP_RIGHT_CONNECTOR_CORNER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     1.      PRESS       D  : \uD83D\uDCBB DISPLAY PRODUCT AS TABLE                  " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     2.      PRESS       W  : \uD83C\uDD95 CREATE A NEW PRODUCT                      " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     3.      PRESS       R  : \uD83D\uDCD6 VIEW PRODUCT DETAILS BY CODE              " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     4.      PRESS       E  : \uD83D\uDCDD EDIT AN EXISTING PRODUCT BY CODE          " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     5.      PRESS       DL : \uD83D\uDEAE DELETE AN EXISTING PRODUCT BY CODE        " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     6.      PRESS       S  : \uD83D\uDD0D SEARCH AN EXISTING PRODUCT BY NAME        " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     7.      PRESS       C  : \uD83D\uDCE9 COMMIT TRANSACTION DATA                   " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     8.      PRESS       BU : \uD83D\uDDC2 BACKUP DATA                               " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     9.      PRESS       RS : \uD83D\uDDC3 RESTORE DATA                              " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     10.     PRESS       L  : \uD83D\uDCC5 NAVIGATE PAGINATION TO THE LAST PAGE      " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     11.     PRESS       P  : \uD83D\uDC49 NAVIGATE PAGINATION TO THE PREVIOUS PAGE  " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     12.     PRESS       N  : \uD83D\uDC48 NAVIGATE PAGINATION TO THE NEXT PAGE      " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     13.     PRESS       F  : \uD83D\uDDD3 NAVIGATE PAGINATION TO THE FIRST PAGE     " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     14.     PRESS       H  : \uD83D\uDEA8 HELP                                      " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(VERTICAL_CONNECTOR_BORDER + darkMagenta + "     15.     PRESS       B  : \uD83C\uDFE1 STEP BACK OF THE APPLICATION              " +reset + VERTICAL_CONNECTOR_BORDER);
        System.out.println(BOTTOM_LEFT_CONNECTOR_CORNER + HORIZONTAL_CONNECTOR_BORDER.repeat(75) + BOTTOM_RIGHT_CONNECTOR_CORNER);


        System.out.print("CLICK [ ENTER ] TO CONTINUE OPERATION ...");
        scanner.nextLine();
        System.out.println("\n");

    }


}
