package views;

public interface BoxBorder {
    static final String TOP_LEFT_CORNER =   "┌";
    static final String TOP_RIGHT_CORNER = "┐";
    static final String BOTTOM_LEFT_CORNER = "└";
    static final String BOTTOM_RIGHT_CORNER = "┘";
    static final String HORIZONTAL_BORDER = "─";
    static final String VERTICAL_BORDER = "│";
    static final String SPACE = " ";


    static final String LEFT_CONNECTOR_BORDER = "╠";
    static final String RIGHT_CONNECTOR_BORDER = "╣";
    static final String TOP_CONNECTOR_BORDER = "╦";
    static final String BOTTOM_CONNECTOR_BORDER = "╩";
    static final String CROSS_BORDER = "╬";
    static final String HORIZONTAL_CONNECTOR_BORDER = "═";
    static final String VERTICAL_CONNECTOR_BORDER = "║";
    static final String TOP_LEFT_CONNECTOR_CORNER =   "╔";
    static final String TOP_RIGHT_CONNECTOR_CORNER = "╗";
    static final String BOTTOM_LEFT_CONNECTOR_CORNER = "╚";
    static final String BOTTOM_RIGHT_CONNECTOR_CORNER = "╝";


    // Box Color
    String reset = "\u001B[0m";
    String red = "\u001B[31m";
    String green = "\u001B[32m";
    String yellow = "\u001B[33m";
    String blue = "\u001B[34m";
    String magenta="\u001B[35m";
    String cyan = "\u001B[36m";
    String white = "\u001B[37m";
    String black = "\u001B[30m";
    String blueBackGround = "\u001B[44m";
    String redBackGround = "\u001B[41m";
}
