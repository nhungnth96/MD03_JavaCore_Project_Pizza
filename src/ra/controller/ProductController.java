package ra.controller;

import ra.model.product.Product;
import ra.model.user.User;
import ra.service.ProductService;

import java.util.List;

public class ProductController implements IGenericController<Product, Integer> {
    private ProductService productService;
    public ProductController() {
        productService = new ProductService();
    }

    @Override
    public List<Product> getAll() {
        return productService.getAll();
    }
    @Override
    public void save(Product product) {
        productService.save(product);
    }
    @Override
    public void delete(Integer id) {
        productService.delete(id);
    }
    @Override
    public Product findById(Integer id) {
        return productService.findById(id);
    }
    @Override
    public int getNewId() {
        return productService.getNewId();
    }
}
