package ra.config;

import java.util.Scanner;

public final class InputMethods {
    public static final String ERROR_ALERT = "Invalid format! Please try again....";
    public static final String EMPTY_ALERT = "The input field cannot be blanked! Please try again....";
    public static final String ERROR_NUMBER = "Please enter an integer greater than 0";
    public static final String ERROR_SPACE = "The input contains white spaces";
    public static final String ERROR_STOCK = "Please enter a quantity of stock greater than 10";
    public static final String ERROR_PRODUCT_ID = "Please";
    /*========================================Input Method Start========================================*/

    /**
     * getString()  Return a String value from the user.
     */
    public static String getString() {
        while (true) {
            String result = getInput();
            if (result.equals("")) {
                System.err.println(EMPTY_ALERT);
                continue;
            }
            if (!result.matches("\\S+")) {
                System.err.println(ERROR_SPACE);
                continue;
            }
            return result;
        }
    }

    /**
     * getChar()  Return a Character value from the user.
     */
    public static char getChar() {
        return getString().charAt(0);
    }

    /**
     * getBoolean()  Return a Boolean value from the user.
     */
    public static boolean getBoolean() {
        String result = getString();
        return result.equalsIgnoreCase("true");
    }

    /**
     * getByte()  Return a Byte value from the user.
     */
    public static byte getByte() {
        while (true) {
            try {
                return Byte.parseByte(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getShort()  Return a Short value from the user.
     */
    public static short getShort() {
        while (true) {
            try {
                return Short.parseShort(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getInteger()  Return a Integer value from the user.
     */
    public static int getInteger() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getLong()  Return a Long value from the user.
     */
    public static long getLong() {
        while (true) {
            try {
                return Long.parseLong(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getFloat()  Return a Float value from the user.
     */
    public static float getFloat() {
        while (true) {
            try {
                return Float.parseFloat(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    /**
     * getDouble()  Return a Double value from the user.
     */
    public static double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }
    /*========================================Input Method End========================================*/


//     * getInput()  Return any String value from the user.

    public static String getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    /**
     * pressAnyKey()  Press any key to continue....
     */
    public static void pressAnyKey() {
        getInput();
    }

    /*========================================Other Method========================================*/
    public static double getPositiveDouble() {
        while (true) {
            double result = getDouble();
            if (result > 0) {
                return result;
            } else {
                System.out.println(ERROR_NUMBER);
            }

        }
    }

    public static int getStock() {
        while (true) {
            int result = getInteger();
            if (result >= 10) {
                return result;
            } else {
                System.out.println(ERROR_STOCK);
            }

        }
    }

    public static String getProductId() {
        while (true) {
            String result = getString();
            if (result.length() > 3) {
                return result;
            } else {
                System.err.println(ERROR_PRODUCT_ID);
                ;
            }
        }
    }

}
