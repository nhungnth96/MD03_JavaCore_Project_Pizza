package ra.service;

import ra.config.Alert;
import ra.model.favor.FavorItem;
import ra.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class FavorService implements IGenericService<FavorItem,Integer> {
    private User userLogin;
    private UserService userService;
    public FavorService(User userLogin) {
        this.userLogin = userLogin;
        userService = new UserService();
    }


    @Override
    public List<FavorItem> getAll() {
        return userLogin.getFavor();
    }

    @Override
    public void save(FavorItem item) {
        List<FavorItem> favor =  userLogin.getFavor();
        if (findById(item.getFavorId()) == null) {
            FavorItem itemFood = findExistedItem(item.getFavorFood().getFoodId());
            if(itemFood!=null){
                System.err.println(Alert.EXISTED_ERROR);
            }
            else {
                favor.add(item);
            }
        }
        userService.save(userLogin);
    }
    @Override
    public void delete(Integer id) {
        // remove object, findById(id) trả về object
        userLogin.getFavor().remove(findById(id));
        // save to DB
        userService.save(userLogin);
    }
    public void clearAll(){
        userLogin.setFavor(new ArrayList<>());
        userService.save(userLogin);
    }
    public int getNewId() {
        int maxId = 0;
        for (FavorItem item : userLogin.getFavor()) {
            if (item.getFavorId() > maxId) {
                maxId = item.getFavorId();
            }
        }
        return maxId + 1;
    }
    @Override
    public FavorItem findById(Integer id) {
        // trả về item
        for (FavorItem item : userLogin.getFavor()) {
            if (item.getFavorId() == id) {
                return item;
            }
        }
        return null;
    }
    public FavorItem findExistedItem(int id){
        for(FavorItem item:userLogin.getFavor()){
            if(item.getFavorFood().getFoodId()==id){
                return item;
            }
        }
        return null;
    }
}
