package controller;

import views.InterfaceViews;

import java.util.Scanner;

public class MenuController {
    private Scanner scanner;
    private InterfaceViews interfaceViews;
    public MenuController(){
        scanner = new Scanner(System.in);
        interfaceViews = new InterfaceViews();
    }

    public void helpMenu(){
        interfaceViews.displayHelp(scanner);
    }
}
