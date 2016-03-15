package Lab1;

import javax.swing.*;

class Logger {
    private static JTextArea textArea;

    public static void setOutput(JTextArea ta) {
        textArea = ta;
    }

    public static void logMessage(String s) {
        if (textArea != null) {
            textArea.append(s + "\n");
        } else throw new NullPointerException("Logger output is not set!");
    }

    private Logger() {
    }
}
