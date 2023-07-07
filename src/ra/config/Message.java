package ra.config;

public class Message {
    public static String getStatusByCode (byte code){
        switch (code) {
            case 1:
                return "Waiting to confirm";
            case 2:
                return "Accepted";
            case 3:
                return "Cancel";
            default:
                return InputMethods.ERROR_ALERT;
        }
    }
}
