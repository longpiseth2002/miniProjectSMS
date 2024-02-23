import utils.InterfaceViews;
import utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Integer> list = new ArrayList<>();

    static void add(int n) {
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        add(1100004);
        System.out.println(
                " ".repeat(25) + "██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗    ████████╗ ██████╗ \n" +
                        " ".repeat(25) + "██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝    ╚══██╔══╝██╔═══██╗   \n" +
                        " ".repeat(25) + "██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗         ██║   ██║   ██║   \n" +
                        " ".repeat(25) + "██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝         ██║   ██║   ██║   \n" +
                        " ".repeat(25) + "╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗       ██║   ╚██████╔╝   \n" +
                        " ".repeat(25) + " ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝       ╚═╝    ╚═════╝ ");
        System.out.println(
                " ".repeat(30) + "  ██████╗███████╗████████╗ █████╗ ██████╗     ███████╗███╗   ███╗███████╗ \n" +
                        " ".repeat(30) + " ██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗    ██╔════╝████╗ ████║██╔════╝ \n" +
                        " ".repeat(30) + " ██║     ███████╗   ██║   ███████║██║  ██║    ███████╗██╔████╔██║███████╗ \n" +
                        " ".repeat(30) + " ██║     ╚════██║   ██║   ██╔══██║██║  ██║    ╚════██║██║╚██╔╝██║╚════██║ \n" +
                        " ".repeat(30) + " ╚██████╗███████║   ██║   ██║  ██║██████╔╝    ███████║██║ ╚═╝ ██║███████║ \n" +
                        " ".repeat(30) + "  ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝     ╚══════╝╚═╝     ╚═╝╚══════╝ ");
        String op;
        InterfaceViews.interfaceCSTAD();
        do {
            InterfaceViews.applicationMenu();
            System.out.print("➡ SELECT OPTION MENU : ");
            op = scanner.nextLine().toLowerCase();
            switch (op) {
                case "d" -> {
                    Util.display(list);
                }
                case "rd" -> {
                    System.out.println("Random");
                }
                case "w" -> {
                    System.out.println("Write");
                }
                case "r" -> {
                    System.out.println("Read");
                }
                case "e" -> {
                    System.out.println("Edit");
                }
                case "dl" -> {
                    System.out.println("Delete");
                }
                case "s" -> {
                    System.out.println("Search");
                }
                case "sr" -> {
                    Util.setUpRow();
                }
                case "c" -> {
                    System.out.println("Commit");
                }
                case "bu" -> {
                    System.out.println("back Up");
                }
                case "rs" -> {
                    System.out.println("Restore");
                }
                case "h" -> {
                    Util.displayHelp();
                }
                case "x" -> {
                    Util.exit();
                }
            }

        } while (op != "x");


        Util.displayHelp();
        InterfaceViews interfaceViews = new InterfaceViews();

        InterfaceViews.applicationMenu();
        interfaceViews.readDetail();
        InterfaceViews.informUpdate();
        InterfaceViews.applicationMenu();
        Util.display(list);


    }
}
