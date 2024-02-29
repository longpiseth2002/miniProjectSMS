import controller.BackgroundProcessController;
import controller.ProductController;
import views.BoxBorder;
import views.InterfaceViews;


import java.io.IOException;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ProductController productController = new ProductController();
        BackgroundProcessController backgroundProcessController = new BackgroundProcessController();
        System.out.println(
                " ".repeat(30) + "  ██████╗███████╗████████╗ █████╗ ██████╗     ███████╗███╗   ███╗███████╗ \n" +
                        " ".repeat(30) + " ██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗    ██╔════╝████╗ ████║██╔════╝ \n" +
                        " ".repeat(30) + " ██║     ███████╗   ██║   ███████║██║  ██║    ███████╗██╔████╔██║███████╗ \n" +
                        " ".repeat(30) + " ██║     ╚════██║   ██║   ██╔══██║██║  ██║    ╚════██║██║╚██╔╝██║╚════██║ \n" +
                        " ".repeat(30) + " ╚██████╗███████║   ██║   ██║  ██║██████╔╝    ███████║██║ ╚═╝ ██║███████║ \n" +
                        " ".repeat(30) + "  ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝     ╚══════╝╚═╝     ╚═╝╚══════╝ ");
        String op;

        InterfaceViews.interfaceCSTAD();
        backgroundProcessController.start();
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
                    backgroundProcessController.random();
                }

                case "w" -> {
                    productController.write();
                }
                case "r" -> {
                    productController.read();
                }
                case "e" -> {
                    productController.editProduct();
                }
                case "dl" -> {
                    productController.deleteById();
                }
                case "s" -> {
                    productController.searchByName();
                }
                case "sr" -> {
                    productController.setNumberRow();
                }
                case "c" -> {
                    backgroundProcessController.commit();
                }
                case "bu" -> {
                    productController.BackUpFile();
                }
                case "rs" -> {
                    backgroundProcessController.restore();
                }
                case "h" -> {
                    InterfaceViews.displayHelp();
                }
                case "x" -> {
                    productController.exitProgram();
                }
                default -> {
                    System.out.println(BoxBorder.red + " ❌ Invalid Option." +
                            "" + BoxBorder.reset + "\n\t🏠 Back to Menu Application...");
                }

            }


        } while (op != "x");


    }
}
