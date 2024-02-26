import controller.BackgroundProcessController;
import controller.ProductController;
import views.InterfaceViews;


import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductController productController = new ProductController();
        BackgroundProcessController backgroundProcessController = new BackgroundProcessController();

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
            System.out.println();
            switch (op) {
                case "d" -> {
                    productController.display();
                }
                case "rd" -> {
                    backgroundProcessController.randomWrite();
                }
                case "w" -> {
                    productController.write();
                }
                case "r" -> {
                    productController.read();
                }
                case "e" -> {
                    System.out.println("Edit");
                }
                case "dl" -> {
                    productController.deleteById();
                }
                case "s" -> {
                    System.out.println("Search");
                }
                case "sr" -> {
                    productController.setNumberRow();
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
                    InterfaceViews.displayHelp();
                }
                case "x" -> {
                    System.exit(0);
                }
            }


        } while (op != "x");


    }
}
