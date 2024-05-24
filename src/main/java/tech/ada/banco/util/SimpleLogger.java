package tech.ada.banco.util;

public class SimpleLogger {
    private static SimpleLogger instance = new SimpleLogger();

    private SimpleLogger(){
    }

    public static SimpleLogger getInstance() {
        return instance;
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void error(String message) {
        System.err.println(message);
    }
}
