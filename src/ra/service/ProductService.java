package ra.service;

import ra.model.product.Category;
import ra.model.product.Product;
import ra.database.DataBase;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IGenericService<Product,Integer> {
    private List<Product> products;
    public ProductService() {
        List<Product> productList = (List<Product>) DataBase.readFromFile(DataBase.PRODUCT_PATH);
        if (productList == null) {
            productList = new ArrayList<>();
        }
        this.products = productList;
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        if (findById(product.getProductId()) == null) {
            // add
            products.add(product);
        } else {
            // update
            products.set(products.indexOf(findById(product.getProductId())), product);
        }
        // save to DB
        DataBase.writeToFile(products, DataBase.PRODUCT_PATH);
    }

    @Override
    public void delete(Integer id) {
        // remove object, findById(id) trả về object User
        products.remove(findById(id));
        // save to DB
        DataBase.writeToFile(products, DataBase.PRODUCT_PATH);
    }

    @Override
    public Product findById(Integer id) {
        // trả về object
        for (Product product : products) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }



    public int getNewId() {
        int maxId = 0;
        for (Product product : products) {
            if (product.getProductId() > maxId) {
                maxId = product.getProductId();
            }
        }
        return maxId + 1;

    }

}
