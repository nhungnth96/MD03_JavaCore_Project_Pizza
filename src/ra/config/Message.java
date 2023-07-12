package ra.config;


public class Message {
    public static String getStatusByCode (byte code){
        switch (code) {
            case 2:
                return "Pending confirm";
            case 3:
                return "Confirmed and preparing";
            case 4:
                    return "Prepared and delivering";
            case 5:
                return "Delivered"; // view invoice
            case 6:
                return "Cancel";
            default:
                return InputMethods.FORMAT_ERROR;
        }

    }
}
