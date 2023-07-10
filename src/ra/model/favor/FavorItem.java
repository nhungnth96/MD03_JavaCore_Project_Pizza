package ra.model.favor;

import ra.config.Validation;
import ra.model.food.Food;
import ra.model.food.PizzaCrust;
import ra.model.food.PizzaExtrasCheese;
import ra.model.food.PizzaSize;

import java.io.Serializable;

public class FavorItem implements Serializable {
    private int favorId;
    private Food favorFood;
    private int itemQuantity;
    private PizzaCrust pizzaCrust = PizzaCrust.MEDIUM;
    private PizzaSize pizzaSize = PizzaSize.MEDIUM;
    private PizzaExtrasCheese pizzaExtrasCheese = PizzaExtrasCheese.NONE;
    private double pizzaPrice;
    public FavorItem() {
    }

    public FavorItem(int favorId, Food favorFood, int itemQuantity) {
        this.favorId = favorId;
        this.favorFood = favorFood;
        this.itemQuantity = itemQuantity;
    }

    public int getFavorId() {
        return favorId;
    }

    public void setFavorId(int favorId) {
        this.favorId = favorId;
    }

    public Food getFavorFood() {
        return favorFood;
    }

    public void setFavorFood(Food favorFood) {
        this.favorFood = favorFood;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public PizzaCrust getPizzaCrust() {
        return pizzaCrust;
    }

    public void setPizzaCrust(PizzaCrust pizzaCrust) {
        this.pizzaCrust = pizzaCrust;
    }

    public PizzaSize getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public PizzaExtrasCheese getPizzaExtrasCheese() {
        return pizzaExtrasCheese;
    }

    public void setPizzaExtrasCheese(PizzaExtrasCheese pizzaExtrasCheese) {
        this.pizzaExtrasCheese = pizzaExtrasCheese;
    }

    public double getPizzaPrice() {
        return pizzaPrice;
    }

    public void setPizzaPrice(double pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    @Override
    public String toString() {
        if(favorFood.getFoodCategory().getCategoryName().equals("Pizza")){
            return "ID: " + favorId + " | " +favorFood.getFoodCategory().getCategoryName() +": "+ favorFood.getFoodName() + "\n"+
                    "Crust: "+ pizzaCrust + " | Size: " +pizzaSize + " | Extras: "+pizzaExtrasCheese+ "\n"+
                    "Price: " + Validation.formatPrice(pizzaPrice) + " | Quantity: " + itemQuantity;
        } else {
            return "ID: " + favorId+ " | " + favorFood.getFoodCategory().getCategoryName()+": " + favorFood.getFoodName() + "\n"+
                    "Price: " + Validation.formatPrice(favorFood.getFoodPrice()) + " | Quantity: " + itemQuantity;
        }
    }
}
