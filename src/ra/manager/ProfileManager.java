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
            System.out.println("4. My Wallet");
            System.out.println("0. Back");
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
                    loadMoney();
                    break;
                case 0:
                    break;
                default:
                    System.err.println(InputMethods.FORMAT_ERROR);
            }
            if (choice == 0) {
                break;
            }
        }
    }

    public void viewProfile() {
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Password: " + currentUser.getPassword());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Tel: " + currentUser.getTel());
        System.out.println("Address: " + currentUser.getAddress());
    }

    public void editProfile() {
        System.out.println("Enter name: ");
        currentUser.setName(InputMethods.getString());
        System.out.println("Enter tel: ");
        String tel;
        while (true) {
            tel = InputMethods.getString();
            if (Validation.validateTel(tel)) {
                break;
            }
            System.err.println(Alert.TEL_ERROR);
        }
        currentUser.setTel(tel);
        System.out.println("Enter address: ");
        currentUser.setAddress(InputMethods.getString());
        userController.save(currentUser);
        System.out.println(Alert.SUCCESSFUL);
    }

    public void changePassword() {
        System.out.println("Enter old password: ");
        String oldPass;
        while (true) {
            oldPass = InputMethods.getString();
            if(!Validation.validateSpaces(oldPass)){
                System.err.println(Alert.SPACE_ERROR);
                continue;
            }
            if (!Validation.validatePassword(oldPass)) {
                System.err.println(Alert.PASSWORD_ERROR);
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
            System.err.println(Alert.PASSWORD_ERROR);
        }
        currentUser.setPassword(newPass);
        userController.save(currentUser);
        System.out.println(Alert.SUCCESSFUL);
    }
    public void loadMoney(){
        System.out.println("Current: " + Validation.formatPrice(currentUser.getWallet()));
        System.out.println("Do you want to load more money?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        while(true){
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    System.out.println("Enter number of money want to load: ");
                    double money = InputMethods.getDouble();
                    double currentTotal = currentUser.getWallet() ;
                    currentUser.setWallet(currentTotal+money);
                    System.out.println(Alert.SUCCESSFUL);
                    break;
                case 2:
                    break;
                default:
                    System.err.println(InputMethods.FORMAT_ERROR);
            } if (choice>=1 && choice <= 2){
                break;
            }
        }

    }
}
