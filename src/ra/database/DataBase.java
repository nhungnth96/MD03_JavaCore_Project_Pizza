package ra.database;

import org.omg.CORBA.PUBLIC_MEMBER;
import ra.model.user.RoleName;
import ra.model.user.User;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class DataBase {
    public static final String USER_PATH = "src\\ra\\database\\user.txt";
    public static final String CATEGORY_PATH = "src\\ra\\database\\category.txt";
    public static final String FOOD_PATH = "src\\ra\\database\\food.txt";
    public static final String ORDER_PATH = "src\\ra\\database\\order.txt";
    public static final String FEEDBACK_PATH = "src\\ra\\database\\feedback.txt";
    public static final User admin = new User(0,"admin", "admin", "Admin@123", new HashSet<>(Arrays.asList(RoleName.ADMIN, RoleName.USER)));
    public static void writeToFile(Object o, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace(); // sinh ra lỗi nhưng ko dừng chương trình
        }
    }

    public static Object readFromFile(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            ois.close();
            fis.close();
            return o;
        } catch (EOFException e) {
            // phát sinh lỗi khi đọc file rỗng -> bỏ qua
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
