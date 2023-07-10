package ra.service;

import ra.model.food.Food;
import ra.database.DataBase;

import java.util.ArrayList;
import java.util.List;

public class FoodService implements IGenericService<Food,Integer> {
    private List<Food> foods;
    public FoodService() {
        List<Food> foodList = (List<Food>) DataBase.readFromFile(DataBase.FOOD_PATH);
        if (foodList == null) {
            foodList = new ArrayList<>();
        }
        this.foods = foodList;
    }

    @Override
    public List<Food> getAll() {
        return foods;
    }

    @Override
    public void save(Food food) {
        if (findById(food.getFoodId()) == null) {
            // add
            foods.add(food);
        } else {
            // update
            foods.set(foods.indexOf(findById(food.getFoodId())), food);
        }
        // save to DB
        DataBase.writeToFile(foods, DataBase.FOOD_PATH);
    }

    @Override
    public void delete(Integer id) {
        // remove object, findById(id) trả về object User
        foods.remove(findById(id));
        // save to DB
        DataBase.writeToFile(foods, DataBase.FOOD_PATH);
    }

    @Override
    public Food findById(Integer id) {
        // trả về object
        for (Food food : foods) {
            if (food.getFoodId() == id) {
                return food;
            }
        }
        return null;
    }



    public int getNewId() {
        int maxId = 0;
        for (Food food : foods) {
            if (food.getFoodId() > maxId) {
                maxId = food.getFoodId();
            }
        }
        return maxId + 1;

    }

}
