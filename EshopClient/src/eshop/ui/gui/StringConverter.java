package eshop.ui.gui;

public class StringConverter {
    /*
        Equivalent zur EA in der CUI für Exceptions
     */
    public static int toInteger(String str) {

        try {
            return Integer.parseInt(str);
        }catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Double toDouble(String str) {
        try {
            return Double.parseDouble(str);
        }catch (NumberFormatException e) {
            return -1.01;
        }
    }

}
