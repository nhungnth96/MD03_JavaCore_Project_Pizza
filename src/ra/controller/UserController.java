package ra.controller;

import ra.model.user.User;
import ra.service.UserService;

import java.util.List;

public class UserController implements IGenericController<User, Integer> {
    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    @Override
    public List<User> getAll() {
        return userService.getAll();
    }

    @Override
    public void save(User user) {
        userService.save(user);
    }

    @Override
    public void delete(Integer id) {
        userService.delete(id);
    }

    @Override
    public User findById(Integer id) {
        return userService.findById(id);
    }

    @Override
    public int getNewId() {
        return userService.getNewId();
    }

    public void changeStatus(Integer userId) {
        userService.changeStatus(userId);
    }

    public void changeRole(Integer userId) {
        userService.changeRole(userId);

    }

    public User checkExistedAccount(String username, String password) {
        return userService.checkExistedAccount(username, password);
    }

    public boolean checkExistedUsername(String username) {
        return userService.checkExistedUsername(username);
    }

    public boolean checkExistedPassword(String password) {
        return userService.checkExistedPassword(password);
    }

    public boolean checkExistedEmail(String email) {
        return userService.checkExistedEmail(email);
    }

    public boolean checkExistedPhoneNumber(String tel) {
        return userService.checkExistedPhoneNumber(tel);
    }
}
