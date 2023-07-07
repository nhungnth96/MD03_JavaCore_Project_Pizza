package ra.model.product;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Validation;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private int productId;
    private String productName;
    private String productDes;
    private Category category;
    private double productPrice;
    private int stock;
    private boolean status = true;

    public Product() {
    }

    public Product(int productId, String productName, String productDes, Category category, double price, int stock, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.productDes = productDes;
        this.category = category;
        this.productPrice = price;
        this.stock = stock;
        this.status = status;

    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return
                "---------------------------" + "\n"+
                        "ID: " + productId +  "\n"+
                        "Name: " + productName + "\n"+
                        "Des: " + productDes + "\n"+
                        "Price: " + Validation.formatPrice(productPrice);


    }

    public String displayForAdmin() {
        return  "---------------------------"+ "\n" +
                "ID: " + productId + " | Category: " + category.getName() +  " | Status: " + (status ? "On stock" : "Out of stock") + "\n" +
                        "Name: " + productName +  " | Des: " + productDes + "\n" +

                "Price: " +  Validation.formatPrice(productPrice) +
                        " | Stock: " + stock
                        ;

    }


    public void inputData(List<Category> categories) {
        System.out.println("Enter name: ");
        this.productName = InputMethods.getString();
        System.out.println("Enter price: ");
        this.productPrice = InputMethods.getPositiveDouble();
        System.out.println("Enter des: ");
        this.productDes = InputMethods.getString();
        System.out.println("Enter stock: ");
        this.stock = InputMethods.getStock();
        System.out.println("Choose category: ");
        for (Category category : categories) {
            System.out.println(category);
        }
        System.out.println("Enter category ID : ");
        while (true) {
            int categoryId = InputMethods.getInteger();
            boolean flag = true;
            for (Category category : categories) {
                if (categoryId == category.getId()) {
                    flag = false;
                    this.category = category;
                    break;
                }
            }
            if (flag) {
                System.out.println(Alert.NOT_FOUND);
            } else {
                break;
            }
        }
    }

}
