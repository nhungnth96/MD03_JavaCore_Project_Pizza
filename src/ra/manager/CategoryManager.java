package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.controller.CategoryController;
import ra.model.product.Category;

import java.util.List;

public class CategoryManager {
    private  CategoryController categoryController;
    private  List<Category> categoryList;
    public CategoryManager(CategoryController categoryController) {
        this.categoryController = categoryController;
        this.categoryList = categoryController.getAll();
    while (true){
            System.out.println("======CATEGORY MANAGER======");
            System.out.println("1. Show category");
            System.out.println("2. Create category");
            System.out.println("3. Edit category");
            System.out.println("4. Delete category");
            System.out.println("5. Back");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    showAllCategory();
                    break;
                case 2:
                    createCategory();
                    break;
                case 3:
                    editCategory();
                    break;
                case 4:
                    deleteCategory();
                    break;
                case 5:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            } if (choice==5){
                break;
            }
        }

    }
    public  void showAllCategory(){
        if(categoryList.size()==0){
            System.out.println("\u001B[33mEmpty category list\u001B[0m");
        }
        for (Category category : categoryList) {
            System.out.println(category);
        }
    }
    public  void createCategory(){
        System.out.println("Enter quantity want to add: ");
        int quantity = InputMethods.getInteger();
        for (int i = 1; i <= quantity; i++) {
            Category category = new Category();
            category.setId(categoryController.getNewId());
            System.out.println("Category ID: "+category.getId());
            System.out.println("Enter category name: ");
            category.setName(InputMethods.getString());
            categoryController.save(category);
            System.out.println(Alert.SUCCESS);
        }
    }
    public  void editCategory(){
        System.out.println("Enter category ID: ");
        Integer editId = InputMethods.getInteger();
        Category editCategory = categoryController.findById(editId);
        if (editCategory == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println(editCategory);
        System.out.println("Enter category name: ");
        editCategory.setName(InputMethods.getString());
        categoryController.save(editCategory);
        System.out.println(Alert.SUCCESS);
    }
    public  void deleteCategory() {
        System.out.println("Enter category ID: ");
        int deleteId = InputMethods.getInteger();
        categoryController.delete(deleteId);
        System.out.println(Alert.SUCCESS);
    }
}
