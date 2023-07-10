package ra.service;

import ra.model.cart.CartItem;
import ra.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class CartService implements IGenericService<CartItem, Integer> {
    private User userLogin;
    private UserService userService;
    public CartService(User userLogin) {
        this.userLogin = userLogin;
        userService = new UserService();
    }


    @Override
    public List<CartItem> getAll() {
        return userLogin.getCart();
    }

    @Override
    public void save(CartItem item) {
         List<CartItem> cart =  userLogin.getCart();
        if (findById(item.getItemId()) == null) {
            // add
            // kiểm tra sp tồn tại trong cart
            CartItem itemFood = findExistedItem(item.getItemFood().getFoodId());

            // đã có trong cart
            if(itemFood!=null){
                if(itemFood.getItemFood().getFoodCategory().getCategoryName().equals("Pizza")){
                    if(itemFood.getPizzaCrust().equals(item.getPizzaCrust())&&
                            itemFood.getPizzaSize().equals(item.getPizzaSize()) &&
                    itemFood.getPizzaExtrasCheese().equals(item.getPizzaExtrasCheese())){
                        itemFood.setItemQuantity((itemFood.getItemQuantity())+item.getItemQuantity());
                    }
                } else {
                    itemFood.setItemQuantity((itemFood.getItemQuantity()) + item.getItemQuantity());
                }
            }

            // chưa có trong cart
            else {
                cart.add(item);
            }
        } else {
            // update
            cart.set(cart.indexOf(findById(item.getItemId())),item);
        }
        // save to DB
        userService.save(userLogin);
    }

    @Override
    public void delete(Integer id) {
        // remove object, findById(id) trả về object
        userLogin.getCart().remove(findById(id));
        // save to DB
       userService.save(userLogin);
    }
    public void clearAll(){
        userLogin.setCart(new ArrayList<>());
        userService.save(userLogin);
    }
    public int getNewId() {
        int maxId = 0;
        for (CartItem item : userLogin.getCart()) {
            if (item.getItemId() > maxId) {
                maxId = item.getItemId();
            }
        }
        return maxId + 1;
    }
    @Override
    public CartItem findById(Integer id) {
        // trả về item
        for (CartItem item : userLogin.getCart()) {
            if (item.getItemId() == id) {
                return item;
            }
        }
        return null;
    }
    public CartItem findExistedItem(int id){
        for(CartItem item:userLogin.getCart()){
            if(item.getItemFood().getFoodId()==id){
                return item;
            }
        }
        return null;
    }




}
