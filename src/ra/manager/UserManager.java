package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.controller.UserController;
import ra.model.user.User;

public class UserManager {
    private UserController userController;

    public UserManager(UserController userController) {
        this.userController = userController;
        while (true) {
            System.out.println("====USER MANAGER====");
            System.out.println("1. Show all account");
            System.out.println("2. Block / Unblock account");
            System.out.println("3. Back");
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
                    Main.adminPage();
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 3) {
                break;
            }
        }
    }

    public void showAllAccount() {
        if(userController.getAll().isEmpty()){
            System.out.println("\u001B[33mEmpty user list\u001B[0m");
            return;
        }
        for (User user : userController.getAll()) {
            System.out.println("------------------");
            System.out.println(user);
        }
    }

    public void changeStatus() {
        System.out.println("Enter account ID: ");
        int accountId = InputMethods.getInteger();
        userController.changeStatus(accountId);
            System.out.println(Alert.SUCCESS);
        }
    }

