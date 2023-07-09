package ra.controller;

import ra.model.cart.CartItem;
import ra.model.user.User;
import ra.service.CartService;

import java.util.List;

public class CartController implements IGenericController<CartItem,Integer> {
    private CartService cartService;
    public CartController(User userLogin) {
        cartService = new CartService(userLogin);
    }

    @Override
    public List<CartItem> getAll() {
        return cartService.getAll();
    }
    @Override
    public void save(CartItem item) {
        cartService.save(item);
    }
    @Override
    public void delete(Integer id) {
        cartService.delete(id);
    }
    @Override
    public CartItem findById(Integer id) {
        return cartService.findById(id);
    }
    @Override
    public int getNewId() {
        return cartService.getNewId();
    }

    public void clearAll() {
        cartService.clearAll();
    }
}
