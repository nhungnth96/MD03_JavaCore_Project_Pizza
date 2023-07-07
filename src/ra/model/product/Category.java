package ra.model.product;

import ra.config.InputMethods;

import java.io.Serializable;

public class Category implements Serializable {
    // pizza, sides, dessert, drinks
    private int categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(int id, String name) {
        this.categoryId = id;
        this.categoryName = name;
    }

    public int getId() {
        return categoryId;
    }

    public void setId(int id) {
        this.categoryId = id;
    }

    public String getName() {
        return categoryName;
    }

    public void setName(String name) {
        this.categoryName = name;
    }

    @Override
    public String toString() {
        return
                "ID: " + categoryId +
                " | Name: " + categoryName;

    }

}
