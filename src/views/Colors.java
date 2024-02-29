package views;

public class Colors {
    // ANSI escape codes for basic colors
    public static String black() {
        return "\u001B[30m"; // Black
    }

    public static String red() {
        return "\u001B[31m"; // Red
    }

    public static String green() {
        return "\u001B[32m"; // Green
    }

    public static String yellow() {
        return "\u001B[33m"; // Yellow
    }

    public static String blue() {
        return "\u001B[34m"; // Blue
    }

    public static String magenta() {
        return "\u001B[35m"; // Magenta
    }

    public static String cyan() {
        return "\u001B[36m"; // Cyan
    }

    public static String white() {
        return "\u001B[37m"; // White
    }

    // ANSI escape codes for additional colors
    public static String brightBlack() {
        return "\u001B[90m"; // Bright Black (Gray)
    }

    public static String brightRed() {
        return "\u001B[91m"; // Bright Red
    }

    public static String brightGreen() {
        return "\u001B[92m"; // Bright Green
    }

    public static String brightYellow() {
        return "\u001B[93m"; // Bright Yellow
    }

    public static String brightBlue() {
        return "\u001B[94m"; // Bright Blue
    }

    public static String brightMagenta() {
        return "\u001B[95m"; // Bright Magenta
    }

    public static String brightCyan() {
        return "\u001B[96m"; // Bright Cyan
    }

    public static String brightWhite() {
        return "\u001B[97m"; // Bright White
    }

    public static String backgroundBlack() {
        return "\u001B[40m"; // Black background
    }

    public static String backgroundRed() {
        return "\u001B[41m"; // Red background
    }

    public static String backgroundGreen() {
        return "\u001B[42m"; // Green background
    }

    public static String backgroundYellow() {
        return "\u001B[43m"; // Yellow background
    }

    public static String backgroundBlue() {
        return "\u001B[44m"; // Blue background
    }

    public static String backgroundMagenta() {
        return "\u001B[45m"; // Magenta background
    }

    public static String backgroundCyan() {
        return "\u001B[46m"; // Cyan background
    }

    public static String backgroundWhite() {
        return "\u001B[47m"; // White background
    }

    public static String brightBackgroundBlack() {
        return "\u001B[100m"; // Bright Black (Gray) background
    }

    public static String brightBackgroundRed() {
        return "\u001B[101m"; // Bright Red background
    }

    public static String brightBackgroundGreen() {
        return "\u001B[102m"; // Bright Green background
    }

    public static String brightBackgroundYellow() {
        return "\u001B[103m"; // Bright Yellow background
    }

    public static String brightBackgroundBlue() {
        return "\u001B[104m"; // Bright Blue background
    }

    public static String brightBackgroundMagenta() {
        return "\u001B[105m"; // Bright Magenta background
    }

    public static String brightBackgroundCyan() {
        return "\u001B[106m"; // Bright Cyan background
    }

    public static String brightBackgroundWhite() {
        return "\u001B[107m"; // Bright White background
    }

    // Additional colors
    public static String purple() {
        return "\u001B[35m"; // Purple
    }

    public static String pink() {
        return "\u001B[38;5;206m"; // Pink
    }

    public static String darkRed() {
        return "\u001B[38;5;88m"; // Dark Red
    }

    public static String darkGreen() {
        return "\u001B[38;5;22m"; // Dark Green
    }

    public static String darkBlue() {
        return "\u001B[38;5;19m"; // Dark Blue
    }

    public static String darkMagenta() {
        return "\u001B[38;5;90m"; // Dark Magenta
    }

    public static String darkCyan() {
        return "\u001B[38;5;23m"; // Dark Cyan
    }

    public static String darkYellow() {
        return "\u001B[38;5;130m"; // Dark Yellow
    }

    // Reset ANSI escape code
    public static String reset() {
        return "\u001B[0m"; // Reset
    }
    private Colors(){};


}
