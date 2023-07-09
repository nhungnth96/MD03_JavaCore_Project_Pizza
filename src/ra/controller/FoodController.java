package ra.controller;

import ra.model.food.Food;
import ra.service.FoodService;

import java.util.List;

public class FoodController implements IGenericController<Food, Integer> {
    private FoodService foodService;
    public FoodController() {
        foodService = new FoodService();
    }

    @Override
    public List<Food> getAll() {
        return foodService.getAll();
    }
    @Override
    public void save(Food food) {
        foodService.save(food);
    }
    @Override
    public void delete(Integer id) {
        foodService.delete(id);
    }
    @Override
    public Food findById(Integer id) {
        return foodService.findById(id);
    }
    @Override
    public int getNewId() {
        return foodService.getNewId();
    }
}
