package ra.model.food;

import java.io.Serializable;

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(int id, String name) {
        this.categoryId = id;
        this.categoryName = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return
                "ID: " + categoryId +
                " | Name: " + categoryName;

    }

}
