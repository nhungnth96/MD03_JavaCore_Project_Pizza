package ra.service;

import ra.database.DataBase;
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
    public void save(CartItem cartItem) {
         List<CartItem> cart =  userLogin.getCart();
        if (findById(cartItem.getItemId()) == null) {
            // add
            // kiểm tra sp tồn tại trong cart
            CartItem productItem = findExistedProduct(cartItem.getProduct().getProductId());

            // đã có trong cart
            if(productItem!=null){
                productItem.setQuantity((productItem.getQuantity())+cartItem.getQuantity());
            }

            // chưa có trong cart
            else {
                cart.add(cartItem);
            }
        } else {
            // update
            cart.set(cart.indexOf(findById(cartItem.getItemId())),cartItem);
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
    public CartItem findExistedProduct(int id){
        for(CartItem item:userLogin.getCart()){
            if(item.getProduct().getProductId()==id){
                return item;
            }
        }
        return null;
    }




}
