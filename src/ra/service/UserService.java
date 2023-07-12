package ra.service;

import ra.config.Alert;
import ra.database.DataBase;
import ra.manager.Main;
import ra.model.user.RoleName;
import ra.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserService implements IGenericService<User, Integer> {
    private List<User> users;

    public UserService() {
        List<User> userList = (List<User>) DataBase.readFromFile(DataBase.USER_PATH); // dữ liệu đọc từ file
        if (userList == null) {
            userList = new ArrayList<>();
        }
        this.users = userList;

    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        if (findById(user.getId()) == null) {
            // add
            users.add(user);
        } else {
            // update
            users.set(users.indexOf(findById(user.getId())), user);
        }
        // save to DB
        DataBase.writeToFile(users, DataBase.USER_PATH);
    }

    @Override
    public void delete(Integer id) {
        // remove object, findById(id) trả về object User
        users.remove(findById(id));
        // save to DB
        DataBase.writeToFile(users, DataBase.USER_PATH);
    }

    @Override
    public User findById(Integer id) {
        // trả về object
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public int getNewId() {
        int maxId = 0;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }

    public void changeStatus(Integer userId) {
        User changeStatusUser = findById(userId);
        if (changeStatusUser == null) {
            System.err.println(Alert.NOT_FOUND);
            return; // dừng hàm
        } if(Main.currentLogin.equals(changeStatusUser)){
            System.err.println("You can't change status yourself");
            return;
        }
        if (changeStatusUser.getRoles().contains(RoleName.ADMIN)){
            System.err.println("You can't change status of admin");
            return;
        }
        changeStatusUser.setStatus(!changeStatusUser.isStatus());
        save(changeStatusUser);
        System.out.println(Alert.SUCCESSFUL);
    }

    public void changeRole(Integer userId) {
        User changeRoleUser = findById(userId);
        if (changeRoleUser == null) {
            System.err.println(Alert.NOT_FOUND);
            return; // dừng hàm
        }
        Set<RoleName> currentRoles = changeRoleUser.getRoles();
        if (currentRoles.contains(RoleName.USER) && currentRoles.size() == 1) {
            currentRoles.add(RoleName.ADMIN);
        } else if (Main.currentLogin.equals(changeRoleUser)) {
            System.err.println("You can't change role yourself");
            return;
        } else {
            currentRoles.remove(RoleName.ADMIN);
        }
        changeRoleUser.setRoles(currentRoles);
        save(changeRoleUser);
        System.out.println(Alert.SUCCESSFUL);
    }

    public User checkExistedAccount(String username, String password) {
        if (username.equals("admin") && password.equals("Admin@123")) {
            return DataBase.admin;

        }
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean checkExistedUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExistedPassword(String password) {
        for (User user : users) {
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExistedEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExistedPhoneNumber(String tel) {
        for (User user : users) {
            if (user.getTel().equals(tel)) {
                return true;
            }
        }
        return false;
    }


}
