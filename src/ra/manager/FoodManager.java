package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.controller.CategoryController;
import ra.controller.FoodController;
import ra.model.food.Category;
import ra.model.food.Food;
import ra.model.user.RoleName;
import java.util.ArrayList;
import java.util.List;

public class FoodManager {
    private FoodController foodController;
    private CategoryController categoryController;
    private List<Food> foodList;
    private List<Category> categoryList;

    public FoodManager() {
    }

    public FoodManager(FoodController foodController, CategoryController categoryController) {
        this.foodController = foodController;
        this.categoryController = categoryController;
        this.foodList = foodController.getAll();
        this.categoryList = categoryController.getAll();
        while (true) {
            System.out.println("======FOOD MANAGER======");
            System.out.println("1. View food");
            System.out.println("2. Search food");
            System.out.println("3. Create food");
            System.out.println("4. Edit food");
            System.out.println("5. Delete food");
            System.out.println("6. Change food status");
            System.out.println("7. Fill food stock");
            System.out.println("0. Back");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    viewFoodMenu();
                    break;
                case 2:
                    searchFoodMenu();
                    break;
                case 3:
                    createFood();
                    break;
                case 4:
                    editFood();
                    break;
                case 5:
                    deleteFood();
                    break;
                case 6:
                    changeFoodStatus();
                    break;
                case 7:
                    fillFoodStock();
                    break;
                case 0:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 0) {
                break;
            }
        }

    }

    public static void viewFoodMenu() {
        FoodController foodController = new FoodController();
        List<Food> foodList = foodController.getAll();
        if (foodList.size() == 0) {
            System.err.println("\u001B[33mEmpty product list\u001B[0m");
            return;
        }
        while (true) {
            System.out.println("======Menu======");
            System.out.println("1. View all food ");
            System.out.println("2. View food by category");
            System.out.println("0. Back");
            System.out.println("Enter choice: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    viewAllFood(foodList);
                    break;
                case 2:
                    ViewFoodByCategory(foodList);
                    break;
                case 0:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 0) {
                break;
            }
        }

    }
    private static void viewAllFood(List<Food> foodList) {
        if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
            for (Food food : foodList) {
                System.out.println(food.displayForAdmin());
            }
        } else {
            for (Food food : foodList) {
                if (food.isFoodStatus()) {
                    System.out.println(food);
                }
            }
        }
    }
    private static void ViewFoodByCategory(List<Food> foodList) {
        CategoryController categoryController = new CategoryController();
        List<Category> categories = categoryController.getAll();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("Enter choice: ");
        while(true){
            int catChoice = InputMethods.getInteger();
            if (catChoice >= 1 && catChoice <= categories.size()) {
                String catName = categories.get(catChoice - 1).getCategoryName();
                for (Food food : foodList) {
                    if (food.getFoodCategory().getCategoryName().equals(catName)) {
                        if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
                            System.out.println(food.displayForAdmin());
                        } else {
                            if (food.isFoodStatus()) {
                                System.out.println(food);
                            }
                        }
                    }
                }
                break;
            } else {
                System.err.println(InputMethods.ERROR_ALERT);
            }
        }
    }



    public static void searchFoodMenu() {
        FoodController foodController = new FoodController();
        List<Food> foodList = foodController.getAll();
        while (true) {
            System.out.println("======Search======");
            System.out.println("1. Search food by name ");
            System.out.println("2. Search food by ingredient ");
            System.out.println("0. Back");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    searchFoodByName(foodList);
                    break;
                case 2:
                    searchFoodByIngredient(foodList);
                    break;
                case 0:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }

            if (choice >= 0 && choice <= 2) {
                break;
            }
        }
    }

    private static void searchFoodByName(List<Food> foodList) {
        System.out.println("Enter keyword: ");
        String name = InputMethods.getString().toLowerCase();
        List<Food> result = new ArrayList<>();
        for (Food food : foodList) {
            if (food.getFoodName().toLowerCase().contains(name)) {
                result.add(food);
            }
        }
        if (result.isEmpty()) {
            System.err.println(Alert.NOT_FOUND);
        } else {
            for (Food food : result) {
                if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
                    System.out.println(food.displayForAdmin());
                } else {
                    if (food.isFoodStatus()) {
                        System.out.println(food);
                    }
                }
            }

        }
    }
    private static void searchFoodByIngredient(List<Food> foodList) {
        System.out.println("Enter keyword: ");
        String ingredient = InputMethods.getString().toLowerCase();
        List<Food> result = new ArrayList<>();
        for (Food food : foodList) {
            if (food.getFoodIngredient().toLowerCase().contains(ingredient)) {
                result.add(food);
            }
        }
        if (result.isEmpty()) {
            System.err.println(Alert.NOT_FOUND);
        } else {
            for (Food food : result) {
                if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
                    System.out.println(food.displayForAdmin());
                } else {
                    if (food.isFoodStatus()) {
                        System.out.println(food);
                    }
                }
            }
        }
    }

    private static void viewFoodByIngredient(List<Food> foodList) {
        while (true) {
            System.out.println("1. Seafood ");
            System.out.println("2. Chicken ");
            System.out.println("3. Beef ");
            System.out.println("4. Pork ");
            System.out.println("5. Vegetable ");
            System.out.println("6. Back");
            System.out.println("Enter choice: ");
            byte choiceDes = InputMethods.getByte();
            String des = "";
            switch (choiceDes) {
                case 1:
                    des = "Seafood";
                    break;
                case 2:
                    des = "Chicken";
                    break;
                case 3:
                    des = "Beef";
                    break;
                case 4:
                    des = "Pork";
                    break;
                case 5:
                    des = "Vegetable";
                    break;
                case 6:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (!des.isEmpty()) {

                if (Main.currentLogin != null && Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
                    for (Food food : foodList) {
                        if (food.getFoodIngredient().contains(des)) {
                            System.out.println(food.displayForAdmin());
                        }
                    }
                } else {
                    for (Food food : foodList) {
                        if (food.getFoodIngredient().contains(des)) {
                            if (food.isFoodStatus()) {
                                System.out.println(food);
                            }
                        }
                    }
                }
            }
            if (choiceDes >= 1 && choiceDes <= 6) {
                break;
            }
        }
    }

    public void createFood() {
        System.out.println("Enter quantity want to add: ");
        int foodQuantity = InputMethods.getInteger();
        for (int i = 1; i <= foodQuantity; i++) {
            Food food = new Food();
            food.setFoodId(foodController.getNewId());
            System.out.println("Food ID: " + food.getFoodId());
            food.inputData(categoryList);
            foodController.save(food);
            System.out.println(Alert.SUCCESS);
        }
    }

    public void editFood() {
        System.out.println("Enter food ID: ");
        Integer editFoodId = InputMethods.getInteger();
        Food editFood = foodController.findById(editFoodId);
        if (editFood == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println(editFood);
        editFood.inputData(categoryList);
        foodController.save(editFood);
        System.out.println(Alert.SUCCESS);
    }

    public void deleteFood() {
        System.out.println("Enter food ID: ");
        int deleteFoodId = InputMethods.getInteger();
        Food deleteFood = foodController.findById(deleteFoodId);
        if (deleteFood == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        foodController.delete(deleteFoodId);
        System.out.println(Alert.SUCCESS);
    }

    public void changeFoodStatus() {
        System.out.println("Enter food ID: ");
        int changeStatusFoodId = InputMethods.getInteger();
        Food changeStatusFood = foodController.findById(changeStatusFoodId);
        if (changeStatusFood == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println(changeStatusFood);
        changeStatusFood.setFoodStatus(!changeStatusFood.isFoodStatus());
        foodController.save(changeStatusFood);
        System.out.println(Alert.SUCCESS);
    }

    public void fillFoodStock() {
        System.out.println("Enter food ID: ");
        int fillFoodStockId = InputMethods.getInteger();
        Food fillFoodStock = foodController.findById(fillFoodStockId);
        if (fillFoodStock == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println("Current stock: " + fillFoodStock.getFoodStock());
        System.out.println("Enter quantity want to fill: ");
        int fillFoodQuantity = InputMethods.getInteger();
        fillFoodStock.setFoodStock(fillFoodStock.getFoodStock() + fillFoodQuantity);
        foodController.save(fillFoodStock);
        System.out.println(Alert.SUCCESS);
    }
}
