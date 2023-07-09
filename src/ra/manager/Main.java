package ra.manager;


import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Validation;
import ra.controller.CategoryController;
import ra.controller.FoodController;
import ra.controller.UserController;
import ra.model.user.RoleName;
import ra.model.user.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    // currentLogin chạy toàn cục
    public static User currentLogin;

    private static UserController userController = new UserController();
    //    private static CartController cartController = new CartController(currentLogin);
    private static CategoryController categoryController = new CategoryController();
    private static FoodController foodController = new FoodController();

    // ===============================HOME PAGE===============================
    public static void homePage() {
        boolean loop = true;
        while (loop) {
            System.out.println("======WELCOME TO DOMINO PIZZA=====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Continue without login");
            System.out.println("0. Exit");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    guestPage();
                    break;
                case 0:
                    loop = false;
                    System.out.println("Thank you!!!");
                    System.exit(0);
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
        }
    }

    // ===============================SIGN IN=============================== DONE
    public static void login() {
        System.out.println("======LOG IN=====");
        System.out.println("Enter username: ");
        String username;
        while (true) {
            username = InputMethods.getString();
            if(!Validation.validateSpaces(username)){
                System.err.println(Alert.ERROR_SPACE);
                continue;
            }
            if (Validation.validateUserName(username)) {
                break;
            }
            System.err.println(Alert.ERROR_USERNAME);
        }
        System.out.println("Enter password: ");
        String password;
        while (true) {
            password = InputMethods.getString();
            if(!Validation.validateSpaces(password)){
                System.err.println(Alert.ERROR_SPACE);
                continue;
            }
            if (Validation.validatePassword(password)) {
                break;
            }
            System.err.println(Alert.ERROR_PASSWORD);

        }
        User userLogin = userController.checkExistedAccount(username, password);
        if (userLogin == null) {
            System.err.println("The account doesn't existed");
        } else {
            if (userLogin.getRoles().contains(RoleName.ADMIN)) {
                currentLogin = userLogin;
                System.out.println("Welcome "+currentLogin.getName().toUpperCase());
                adminPage();
            } else {
                if (userLogin.getRoles().contains(RoleName.USER) && userLogin.isStatus()) {
                    currentLogin = userLogin;
                    System.out.println("Welcome " + currentLogin.getName().toUpperCase());
                    userPage();
                } else {
                    System.err.println("Your account has been locked");
                    homePage();
                }
            }
        }
    }

    // ===============================SIGN UP=============================== DONE
    public static void register() {
        User user = new User();
        System.out.println("======SIGN UP=====");
        user.setId(userController.getNewId());
        System.out.println("Enter name: ");
        user.setName(InputMethods.getString());
        System.out.println("Enter username: ");
        String username;
        while (true) {
            username = InputMethods.getString();
            if(!Validation.validateSpaces(username)){
                System.err.println(Alert.ERROR_SPACE);
                continue;
            }
            if (userController.checkExistedUsername(username)) {
                System.err.println(Alert.ERROR_EXISTED);
                continue;
            }
            if (Validation.validateUserName(username)) {
                user.setUsername(username);
                break;
            }
            System.err.println(Alert.ERROR_USERNAME);

        }
        System.out.println("Enter password: ");
        String password;
        while (true) {
            password = InputMethods.getString();
            if(!Validation.validateSpaces(password)){
                System.err.println(Alert.ERROR_SPACE);
                continue;
            }
            if (Validation.validatePassword(password)) {
                user.setPassword(password);
                break;
            }
            System.err.println(Alert.ERROR_PASSWORD);
        }
        System.out.println("Enter email: ");
        String email;
        while (true) {
            email = InputMethods.getString();
            if(!Validation.validateSpaces(email)){
                System.err.println(Alert.ERROR_SPACE);
                continue;
            }
            if (userController.checkExistedEmail(email)) {
                System.err.println(Alert.ERROR_EXISTED);
                continue;
            }
            if (Validation.validateEmail(email)) {
                user.setEmail(email);
                break;
            }
            System.err.println(Alert.ERROR_EMAIL);
        }
        user.setRoles(new HashSet<>(Arrays.asList(RoleName.USER)));
        userController.save(user);
        System.out.println(Alert.SUCCESS);
        System.out.println("Please login");
    }

    // ===============================GUEST PAGE=============================== DONE
    public static void guestPage() {
        while (true) {
            System.out.println("\u001B[33mYou must be login to buy product\u001B[0m");
            System.out.println("1. View food menu");
            System.out.println("2. Search food menu");
            System.out.println("0. Back");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    FoodManager.viewFoodMenu();
                    break;
                case 2:
                    FoodManager.searchFoodMenu();
                    break;
                case 0:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 0) {
                break;
            }
        }
    }

    // ===============================USER PAGE===============================
    public static void userPage() {
        while (true) {
            System.out.println("1. View food menu"); // okay
            System.out.println("2. Add to cart");
            System.out.println("3. My Cart");
            System.out.println("4. My Order");
            System.out.println("5. My Profile"); // done
            System.out.println("0. Log out");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    FoodManager.viewFoodMenu();
                    // View menu
                    break;
                case 2:
                    CartManager.addItemToCart();
                    // Add to cart
                    break;
                case 3:
                    new CartManager();
//                    System.out.println("======MY CART======");
//                    System.out.println("1. View Cart");
//                    System.out.println("2. Change item quantity");
//                    System.out.println("3. Delete item");
//                    System.out.println("4. Clear Cart");
//                    System.out.println("5. Back");
                    break;
                case 4:
//                    new OrderManager();
                    new OrderManagerV2();
//                    System.out.println("======MY ORDER======");
//                    System.out.println("1. View all order");
//                    System.out.println("2. View waiting order");
//                    System.out.println("3. View confirmed order");
//                    System.out.println("4. View canceled order");
//                    System.out.println("5. View order details");
//                    System.out.println("6. Back");
                    break;
                case 5:
                    new ProfileManager(userController);
//                    System.out.println("====MY PROFILE====");
//                    System.out.println("1. View Profile");
//                    System.out.println("2. Edit Profile");
//                    System.out.println("3. Change Password");
//                    System.out.println("4. Back");
                    break;
                case 0:
                    logout();
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 0) {
                break;
            }
        }

    }

    // ===============================ADMIN PAGE=============================== order
    public static void adminPage() {
        while (true) {
            System.out.println("1. User Manager"); // done
            System.out.println("2. Category Manager"); // done
            System.out.println("3. Food Manager"); // done
            System.out.println("4. Order Manager"); // not yet
            System.out.println("0. Log out");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    new UserManager(userController);
//                    System.out.println("====USER MANAGER====");
//                    System.out.println("1. Show all account");
//                    System.out.println("2. Block / Unblock account");
//                    System.out.println("3. Change account's role ");
//                    System.out.println("4. Create account");
//                    System.out.println("5. Back");
                    break;
                case 2:
                    new CategoryManager(categoryController);
//                    System.out.println("======CATEGORY MANAGER======");
//                    System.out.println("1. View category");
//                    System.out.println("2. Create category");
//                    System.out.println("3. Edit category");
//                    System.out.println("4. Delete category");
//                    System.out.println("5. Search category");
//                    System.out.println("6. Back");
                    break;
                case 3:
                    new FoodManager(foodController, categoryController);
//                    System.out.println("======FOOD MANAGER======");
//                    System.out.println("1. View food");
//                    System.out.println("2. Search food");
//                    System.out.println("3. Create food");
//                    System.out.println("4. Edit food");
//                    System.out.println("5. Delete food");
//                    System.out.println("6. Change food status");
//                    System.out.println("7. Fill food stock");
//                    System.out.println("8. Back");
//                    System.out.println("Enter choice: ");
                    break;
                case 4:
                    new OrderManagerV2();
                    break;
                case 0:
                    logout();
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 0) {
                break;
            }
        }
    }

    // ===============================LOG OUT===============================
    public static void logout() {
        currentLogin = null;
        homePage();
    }

    public static void main(String[] args) {
        homePage();
    }

}




