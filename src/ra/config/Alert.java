package ra.config;

public class Alert {
    public static final String NOT_FOUND = "Not found";
    public static final String SUCCESSFUL = "\u001B[1;36mSuccessful\u001B[0m";
    public static final String FAILED = "Failed";
    public static final String EXISTED_ERROR = "Already existed";
    public static final String USERNAME_ERROR = "Username must be at least 6 characters";
    public static final String PASSWORD_ERROR = "Password must be at least 6 characters, at least 1 uppercase letter, 1 lowercase letter and 1 symbol character";
    public static final String EMAIL_ERROR = "Invalid email format";
    public static final String TEL_ERROR = "Invalid phone number format";
    public static final String SPACE_ERROR = "The input contains white spaces";
    public static final String SOLD_OUT = "Out of stock";
    public static final String EMPTY_LIST = "\u001B[33mEmpty list\u001B[0m";
    public static final String NO_CART = "\u001B[33mThere's is no item in cart\u001B[0m";
    public static final String NO_FEEDBACK = "\u001B[33mThere's is no feedback\u001B[0m";
    public static final String RED_BOLD = "\033[1;31m▪\u001B[0m";    // RED
    public static final String BLUE_BOLD = "\033[1;34m▪\u001B[0m";   // BLUE
    public static final String WHITE_BOLD = "\033[1;37m▪\u001B[0m";  // WHITE
}
