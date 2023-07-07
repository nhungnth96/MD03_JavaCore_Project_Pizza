package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.controller.CartController;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.model.cart.CartItem;

import ra.model.order.Order;
import ra.model.product.Product;
import ra.model.user.User;

import java.util.List;

public class CartManager {
    private static CartController cartController ;
    private  ProductController productController;
    public CartManager() {
        cartController = new CartController(Main.currentLogin);
        productController = new ProductController();
        while (true) {
            System.out.println("======MY CART======");
            System.out.println("1. View Cart");
            System.out.println("2. Change item quantity");
            System.out.println("3. Delete item");
            System.out.println("4. Clear Cart");
            System.out.println("5. Check out");
            System.out.println("6. Back");
            System.out.println("Enter choice: ");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    showCart();
                    break;
                case 2:
                    changeItemQuantity();
                    break;
                case 3:
                    deleteItemInCart();
                    break;
                case 4:
                    clearCart();
                    break;
                case 5:
                    checkOut(productController);
                    break;
                case 6:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 6) {
                break;
            }
        }

    }
    public void showCart() {
        User currentLogin = Main.currentLogin;
        List<CartItem> itemList = currentLogin.getCart();
        if(itemList.isEmpty()){
            System.out.println("\u001B[33mEmpty item list\u001B[0m");
            return;
        }
        for (CartItem item:itemList){
            item.setProduct(productController.findById(item.getProduct().getProductId()));
            System.out.println(item);
        }
    }
    public static void addItemToCart() {
        cartController = new CartController(Main.currentLogin);
        ProductController productController = new ProductController();
        System.out.println("Enter product ID: ");
       int productId = InputMethods.getInteger();
       Product product = productController.findById(productId);
       if(product==null){
           System.err.println(Alert.NOT_FOUND);
           return;
       }
       CartItem item = new CartItem();
       item.setItemId(cartController.getNewId());
       item.setProduct(product);
        System.out.println("Enter quantity: ");
        item.setQuantity(InputMethods.getInteger());
        cartController.save(item);
        System.out.println(Alert.SUCCESS);
    }
    public void changeItemQuantity(){
        System.out.println("Enter item ID: ");
        int itemId = InputMethods.getInteger();
        CartItem item = cartController.findById(itemId);
        if(item==null){
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println("Quantity:" + item.getQuantity());
        System.out.println("Enter new quantity: ");
        item.setQuantity(InputMethods.getInteger());
        cartController.save(item);

        }
    public void deleteItemInCart() {
        System.out.println("Enter item ID: ");
        int deleteId = InputMethods.getInteger();
        if(cartController.findById(deleteId)==null){
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        cartController.delete(deleteId);
        System.out.println(Alert.SUCCESS);
    }
    public void clearCart() {
        cartController.clearAll();
        System.out.println(Alert.SUCCESS);
    }
    public void checkOut(ProductController productController){
        OrderController orderController = new OrderController() ;
        User currentLogin = Main.currentLogin;
        List<CartItem> itemList = currentLogin.getCart();
        if(itemList.isEmpty()){
            System.out.println("\u001B[33mEmpty item list\u001B[0m");
            return;
        }
        // kiểm tra số lượng trong kho
        for(CartItem item: itemList){
            Product product = productController.findById(item.getProduct().getProductId());
            if(item.getQuantity()>product.getStock()){
                System.out.println("sản phẩm"+ product.getProductName()+" chỉ còn"+product.getStock()+"please reduce");
                return;
            }
        }
        Order newOrder = new Order();
        newOrder.setId(orderController.getNewId());
        // copy sp trong giỏ hàng sang hóa đơn
        newOrder.setOrderDetail(currentLogin.getCart());
        // cập nhật tổng tiền
        double total = 0;
        for (CartItem item: itemList) {
            total+= item.getQuantity()*item.getProduct().getProductPrice();
        }
        newOrder.setTotal(total);
        newOrder.setUserId(currentLogin.getId());
        System.out.println("Enter receiver name: ");
        newOrder.setReceiver(InputMethods.getString());
        System.out.println("Enter phone number: ");
        newOrder.setPhoneNumber(InputMethods.getString());
        System.out.println("Enter address: ");
        newOrder.setAddress(InputMethods.getString());
        orderController.save(newOrder);
        // giảm số lượng trong stock
        for(CartItem item: itemList){
            Product product = productController.findById(item.getProduct().getProductId());
            product.setStock(product.getStock()- item.getQuantity());
            productController.save(product);
        }
        clearCart();
    }
}
