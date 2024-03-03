package controller;

import views.InterfaceViews;

import java.util.Scanner;

public class MenuController {
    // Instance variables:
    private Scanner scanner;
    private InterfaceViews interfaceViews;
    // Constructor:
    public MenuController(){
        scanner = new Scanner(System.in);
        interfaceViews = new InterfaceViews();
    }
    // Methods:
    public void helpMenu(){
        // Call a method from interfaceViews to display the help menu
        interfaceViews.displayHelp(scanner);
        // Pass the scanner object for potential user input within the help menu
    }
}

