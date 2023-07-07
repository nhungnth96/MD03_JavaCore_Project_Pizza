package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Validation;
import ra.controller.UserController;
import ra.model.user.User;

public class ProfileManager {
    User currentUser = Main.currentLogin;
    private UserController userController;

    public ProfileManager(UserController userController) {
        this.userController = userController;
        while (true) {
            System.out.println("====MY PROFILE====");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Back");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    editProfile();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 4) {
                break;
            }
        }
    }

    public void viewProfile() {
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Password: " + currentUser.getPassword());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Address: " + currentUser.getAddress());
        System.out.println("Tel: " + currentUser.getTel());
    }

    public void editProfile() {
        System.out.println("Enter name: ");
        currentUser.setName(InputMethods.getString());
        System.out.println("Enter address: ");
        currentUser.setAddress(InputMethods.getString());
        System.out.println("Enter tel: ");
        String tel;
        while (true) {
            tel = InputMethods.getString();
            if (Validation.validateTel(tel)) {
                break;
            }
            System.out.println(Alert.ERROR_TEL);
        }
        currentUser.setTel(tel);
        userController.save(currentUser);
    }

    public void changePassword() {
        System.out.println("Enter old password: ");
        String oldPass;
        while (true) {
            oldPass = InputMethods.getString();
            if (!Validation.validatePassword(oldPass)) {
                System.err.println(Alert.ERROR_PASSWORD);
                continue;
            }
            if (userController.checkExistedPassword(oldPass)) {
                break;
            }
            System.err.println("The old password isn't correct! Please try again...");
        }
        System.out.println("Enter new password:");
        String newPass;
        while (true) {
            newPass = InputMethods.getString();
            if (newPass.equals(oldPass)) {
                System.err.println("New password cannot be same old password! Please try again...");
                continue;
            }
            if (Validation.validatePassword(newPass)) {
                break;
            }
            System.err.println(Alert.ERROR_PASSWORD);
        }
        currentUser.setPassword(newPass);
        userController.save(currentUser);
        System.out.println(Alert.SUCCESS);
    }
}
