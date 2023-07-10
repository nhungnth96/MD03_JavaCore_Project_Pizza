package ra.controller;

import ra.model.cart.CartItem;
import ra.model.favor.FavorItem;
import ra.model.user.User;
import ra.service.CartService;
import ra.service.FavorService;

import java.util.List;

public class FavorController implements IGenericController<FavorItem,Integer> {
    private FavorService favorService;
    public FavorController(User userLogin) {
        favorService = new FavorService(userLogin);
    }

    @Override
    public List<FavorItem> getAll() {
        return favorService.getAll();
    }
    @Override
    public void save(FavorItem item) {
        favorService.save(item);
    }
    @Override
    public void delete(Integer id) {
        favorService.delete(id);
    }
    @Override
    public FavorItem findById(Integer id) {
        return favorService.findById(id);
    }
    @Override
    public int getNewId() {
        return favorService.getNewId();
    }

    public void clearAll() {
        favorService.clearAll();
    }
}
