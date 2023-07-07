package ra.service;

import ra.model.product.Category;
import ra.database.DataBase;

import java.util.ArrayList;
import java.util.List;

public class CategoryService implements IGenericService<Category, Integer> {
    private List<Category> categories;

    public CategoryService() {
        List<Category> categoryList = (List<Category>) DataBase.readFromFile(DataBase.CATEGORY_PATH);
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
        this.categories = categoryList;
    }

    @Override
    public List<Category> getAll() {
        return categories;
    }

    @Override
    public void save(Category category) {
        if (findById(category.getId()) == null) {
            // add
            categories.add(category);
        } else {
            // update
            categories.set(categories.indexOf(findById(category.getId())), category);
        }
        // save to DB
        DataBase.writeToFile(categories, DataBase.CATEGORY_PATH);
    }

    @Override
    public void delete(Integer id) {
        // remove object, findById(id) trả về object User
        categories.remove(findById(id));
        // save to DB
        DataBase.writeToFile(categories, DataBase.CATEGORY_PATH);
    }

    @Override
    public Category findById(Integer id) {
        // trả về object
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    public int getNewId() {
        int maxId = 0;
        for (Category category : categories) {
            if (category.getId() > maxId) {
                maxId = category.getId();
            }
        }
        return maxId + 1;
    }


}
