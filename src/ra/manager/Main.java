package ra.manager;


import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Validation;
import ra.controller.CategoryController;
import ra.controller.ProductController;
import ra.controller.UserController;
import ra.model.user.RoleName;
import ra.model.user.User;

import java.util.Arrays;
import java.util.HashSet;

public class Main {
    // currentLogin chạy toàn cục
    public static User currentLogin;

    private static UserController userController = new UserController();
    //    private static CartController cartController = new CartController(currentLogin);
    private static CategoryController categoryController = new CategoryController();
    private static ProductController productController = new ProductController();

    // ===============================HOME PAGE===============================
    public static void homePage() {
        boolean loop = true;
        while (loop) {
            System.out.println("======WELCOME TO DOMINO PIZZA=====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Continue without login");
            System.out.println("4. Exit");
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
                case 4:
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
            if (Validation.validateUserName(username)) {
                break;
            }
            System.err.println(Alert.ERROR_USERNAME);
        }
        System.out.println("Enter password: ");
        String password;
        while (true) {
            password = InputMethods.getString();
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
        System.out.println("======SIGN UP=====");
        int id = userController.getNewId();
        System.out.println("Enter name: ");
        String name = InputMethods.getString();
        System.out.println("Enter username: ");
        String username;
        while (true) {
            username = InputMethods.getString();
            if (userController.checkExistedUsername(username)) {
                System.err.println(Alert.ERROR_EXISTED);
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
            if (Validation.validatePassword(password)) {
                break;
            }
            System.err.println(Alert.ERROR_PASSWORD);
        }
        System.out.println("Enter email: ");
        String email;
        while (true) {
            email = InputMethods.getString();
            if (userController.checkExistedEmail(email)) {
                System.err.println(Alert.ERROR_EXISTED);
                continue;
            }
            if (Validation.validateEmail(email)) {
                break;
            }
            System.err.println(Alert.ERROR_EMAIL);
        }
        User user = new User(id, name, username, password,email, new HashSet<>(Arrays.asList(RoleName.USER)));
        userController.save(user);
        System.out.println(Alert.SUCCESS);
        System.out.println("Please login");
    }

    // ===============================GUEST PAGE=============================== DONE
    public static void guestPage() {
        while (true) {
            System.out.println("\u001B[33mYou must be login to buy product\u001B[0m");
            System.out.println("1. View Product");
            System.out.println("2. Search Product");
            System.out.println("3. Back");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    ProductManager.showAllProduct(productController.getAll());
                    break;
                case 2:
                    ProductManager.searchProduct(productController.getAll());
                    break;
                case 3:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 3) {
                break;
            }
        }
    }

    // ===============================USER PAGE===============================
    public static void userPage() {
        while (true) {
            System.out.println("1. View Menu"); // okay
            System.out.println("2. Add to cart");
            System.out.println("3. My Cart");
            System.out.println("4. My Order");
            System.out.println("5. My Profile"); // done
            System.out.println("6. Log out");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    ProductManager.showAllProduct(productController.getAll());
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
                    new OrderManager();
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
                case 6:
                    logout();
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 6) {
                break;
            }
        }

    }

    // ===============================ADMIN PAGE=============================== order
    public static void adminPage() {
        while (true) {
            System.out.println("1. User Manager"); // done
            System.out.println("2. Category Manager"); // done
            System.out.println("3. Product Manager"); // done
            System.out.println("4. Order Manager"); // not yet
            System.out.println("5. Log out");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    new UserManager(userController);
//                    System.out.println("====USER MANAGER====");
//                    System.out.println("1. Show all account");
//                    System.out.println("2. Block / Unblock account");
//                    System.out.println("3. Back");
                    break;
                case 2:
                    new CategoryManager(categoryController);
//                    System.out.println("======CATEGORY MANAGER======");
//                    System.out.println("1. Show category");
//                    System.out.println("2. Create category");
//                    System.out.println("3. Edit category");
//                    System.out.println("4. Delete category");
//                    System.out.println("5. Back");
                    break;
                case 3:
                    new ProductManager(productController, categoryController);
//                    System.out.println("======PRODUCT MANAGER======");
//                    System.out.println("1. Show product");
//                    System.out.println("2. Search product");
//                    System.out.println("3. Create product");
//                    System.out.println("4. Edit product");
//                    System.out.println("5. Delete product");
//                    System.out.println("6. Change status");
//                    System.out.println("7. Back");
                    break;
                case 4:
                    // not yet
                    break;
                case 5:
                    logout();
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 5) {
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




