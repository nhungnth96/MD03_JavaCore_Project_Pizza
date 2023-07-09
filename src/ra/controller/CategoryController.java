package ra.controller;

import ra.model.food.Category;
import ra.service.CategoryService;

import java.util.List;

public class CategoryController implements IGenericController<Category,Integer> {
    private CategoryService categoryService;
    public CategoryController() {
        categoryService = new CategoryService();
    }

    @Override
    public List<Category> getAll() {
        return categoryService.getAll();
    }
    @Override
    public void save(Category category) {
        categoryService.save(category);
    }
    @Override
    public void delete(Integer id) {
        categoryService.delete(id);
    }
    @Override
    public Category findById(Integer id) {
        return categoryService.findById(id);
    }
    @Override
    public int getNewId() {
        return categoryService.getNewId();
    }
}
