package ra.model.cart;

import ra.config.Validation;
import ra.model.product.Product;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int itemId;
    private Product product;
    private int quantity;

    public CartItem() {

    }

    public CartItem(int itemId, Product product, int quantity) {
        this.itemId = itemId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ID: " + itemId + " | Product: " + product.getProductName() + "\n" +
                "Price: " + Validation.formatPrice(product.getProductPrice()) + " | Quantity: " + quantity;

    }
}
