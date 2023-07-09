package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Validation;
import ra.controller.UserController;
import ra.model.food.Food;
import ra.model.user.RoleName;
import ra.model.user.User;

import java.util.*;

public class UserManager {
    private UserController userController;
    public UserManager(UserController userController) {
        this.userController = userController;
        while (true) {
            System.out.println("====USER MANAGER====");
            System.out.println("1. Show all account");
            System.out.println("2. Block / Unblock account");
            System.out.println("3. Search account");
            System.out.println("4. Change account's role ");
            System.out.println("5. Create account");
            System.out.println("0. Back");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    showAllAccount();
                    break;
                case 2:
                    changeStatus();
                    break;
                case 3:
                    searchAccount();
                    break;
                case 4:
                    changeRole();
                    break;
                case 5:
                    createAccount();
                    break;
                case 0:
                    Main.adminPage();
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 0) {
                break;
            }
        }
    }

    public void showAllAccount() {
        if (userController.getAll().isEmpty()) {
            System.out.println("\u001B[33mEmpty user list\u001B[0m");
            return;
        }
        for (User user : userController.getAll()) {
            System.out.println("------------------");
            System.out.println(user);
        }
    }
    public void searchAccount(){
        System.out.println("Enter keyword: ");
        String username = InputMethods.getString().toLowerCase();
        List<User> result = new ArrayList<>();
        for (User user : userController.getAll()) {
            if (user.getUsername().toLowerCase().contains(username)) {
                result.add(user);
            }
        }
        if (result.isEmpty()) {
            System.err.println(Alert.NOT_FOUND);
        } else {
            for (User user:result){
                System.out.println(user);
            }
        }
    }

    public void changeStatus() {
        System.out.println("Enter account ID: ");
        int accountId = InputMethods.getInteger();
        userController.changeStatus(accountId);
        System.out.println(Alert.SUCCESS);
    }

    public void changeRole() {
        System.out.println("Enter account ID: ");
        int accountId = InputMethods.getInteger();
        userController.changeRole(accountId);
        System.out.println(Alert.SUCCESS);
    }

    public void createAccount() {
        User user = new User();
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
        System.out.println("Enter Roles: (etc: user,admin,...)");
        String roles = InputMethods.getString();
        String[] stringRoles = roles.split(",");
        List<String> listRoles = Arrays.asList(stringRoles);;
        for (String role : stringRoles) {
            switch (role) {
                case "admin":
                    user.getRoles().add(RoleName.ADMIN);
                case "manager":
                    user.getRoles().add(RoleName.MANAGER);
                case "user":
                    user.getRoles().add(RoleName.USER);
                default:
                    user.getRoles().add(RoleName.USER);
            }
        }

        userController.save(user);
        System.out.println(Alert.SUCCESS);

    }

}



