package ra.model.cart;

import ra.config.Validation;
import ra.model.food.Food;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int itemId;
    private Food itemFood;
    private int itemQuantity;

    public CartItem() {

    }

    public CartItem(int itemId, Food itemFood, int itemQuantity) {
        this.itemId = itemId;
        this.itemFood = itemFood;
        this.itemQuantity = itemQuantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Food getItemFood() {
        return itemFood;
    }

    public void setItemFood(Food itemFood) {
        this.itemFood = itemFood;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String toString() {
        return "ID: " + itemId + " | Food: " + itemFood.getFoodName() +
                " | Price: " + Validation.formatPrice(itemFood.getFoodPrice()) + " | Quantity: " + itemQuantity;
    }


}
