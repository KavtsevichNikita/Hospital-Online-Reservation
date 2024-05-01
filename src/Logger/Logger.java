package Logger;

public class Logger {
    public static void logError(String message) {
        System.out.println("\u001B[31mError: " + message + "\u001B[0m"); // Red color
    }
    public static void logInfo(String message) {
        System.out.println("\u001B[32m" + message + "\u001B[0m"); // Green color
    }
    public static void logCustom(String message) {
        System.out.println("\u001B[34m" + message + "\u001B[0m"); // Blue color
    }
}
