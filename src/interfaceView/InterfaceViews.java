package interfaceView;


import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

public class InterfaceViews implements BoxBorder{
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

        table.addCell("  (D)Display");
        table.addCell("  (RD)Random");
        table.addCell("  (W)Write");
        table.addCell("  (R)ead");
        table.addCell("  (E)Edit");
        table.addCell("  (D)Delete");
        table.addCell("  (S)Search");
        table.addCell("  (SO)Set row");
        table.addCell("  (C)Commit");
        table.addCell("  (BU)Back Up");
        table.addCell("  (RS)Restore");
        table.addCell("  (H)Help");
        table.addCell("  (X)Exit");



        System.out.println(table.render());
    }

    public void readDetail(){
        Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.setColumnWidth(0,30,40);
        table.setColumnWidth(1,30,40);


        table.addCell("  CODE " + " ".repeat(23) + ":");
        table.addCell("  CSTAD -1220933");
        table.addCell("  NAME " + " ".repeat(23) + ":");
        table.addCell("  COCA COLA");
        table.addCell("  UNIT PRICE " + " ".repeat(17) + ":");
        table.addCell("  933.0");
        table.addCell("  QTY " + " ".repeat(24) + ":");
        table.addCell("  100");
        table.addCell("  IMPORTED AT " + " ".repeat(16) + ":");
        table.addCell("  2024-05-22");


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

}
