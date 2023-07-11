package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Validation;
import ra.controller.CartController;
import ra.controller.FoodController;
import ra.controller.OrderController;
import ra.model.cart.CartItem;

import ra.model.food.PizzaCrust;
import ra.model.food.PizzaExtrasCheese;
import ra.model.food.PizzaSize;
import ra.model.order.Order;
import ra.model.food.Food;
import ra.model.order.PaymentMethod;
import ra.model.order.ShippingMethod;
import ra.model.user.User;

import java.util.List;

public class CartManager {
    private static CartController cartController ;
    private FoodController foodController;
    private OrderController orderController;
    public CartManager() {
        cartController = new CartController(Main.currentLogin);
        foodController = new FoodController();
        orderController = new OrderController();
        while (true) {
            System.out.println("======MY CART======");
            System.out.println("1. View Cart");
            System.out.println("2. Change item quantity");
            System.out.println("3. Delete item");
            System.out.println("4. Clear Cart");
            System.out.println("5. Check out");
            System.out.println("0. Back");
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
                    checkOut(foodController);
                    break;
                case 0:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
            if (choice == 0) {
                break;
            }
        }

    }
    public void showCart() {
        User currentLogin = Main.currentLogin;
        List<CartItem> itemList = currentLogin.getCart();
        if(itemList.isEmpty()){
            System.out.println(Alert.EMPTY_LIST);
            return;
        }
        for (CartItem item:itemList){
            item.setItemFood(foodController.findById(item.getItemFood().getFoodId()));
            System.out.println(item);
        }
    }
    public static void addItemToCart() {
        cartController = new CartController(Main.currentLogin);
        FoodController foodController = new FoodController();
        System.out.println("Enter food ID: ");
       int footId = InputMethods.getInteger();
       Food food = foodController.findById(footId);
       if(food ==null){
           System.err.println(Alert.NOT_FOUND);
           return;
       }
       CartItem item = new CartItem();
       item.setItemId(cartController.getNewId());
       item.setItemFood(food);
        System.out.println("Enter quantity: ");
        item.setItemQuantity(InputMethods.getInteger());
        cartController.save(item);
        System.out.println(Alert.SUCCESS);
    }
    public static void addItemToCartV2() {
        cartController = new CartController(Main.currentLogin);
        FoodController foodController = new FoodController();
        System.out.println("Enter food ID: ");
        int footId = InputMethods.getInteger();
        Food food = foodController.findById(footId);
        if(food ==null){
            System.err.println(Alert.NOT_FOUND);
            return;
        }
            CartItem item = new CartItem();
            item.setItemId(cartController.getNewId());
            item.setItemFood(food);
        if(item.getItemFood().getFoodCategory().getCategoryName().equals("Pizza")){
            System.out.println("Choose pizza crust: ");
            for (int i = 0; i < PizzaCrust.values().length; i++) {
                System.out.println((i + 1) + ". " + PizzaCrust.values()[i]);
            }
            while(true){
                int crustChoice = InputMethods.getInteger();
                if (crustChoice >= 1 && crustChoice <= PizzaCrust.values().length) {
                    item.setPizzaCrust(PizzaCrust.values()[crustChoice-1]);
                    break;
                }
                System.err.println(InputMethods.ERROR_ALERT);
            }
            System.out.println("Choose pizza size: ");
            for (int i = 0; i < PizzaSize.values().length; i++) {
                System.out.println((i + 1) + ". " + PizzaSize.values()[i]);
            }
            while(true){
                int sizeChoice = InputMethods.getInteger();
                if (sizeChoice >= 1 && sizeChoice <= PizzaSize.values().length) {
                    item.setPizzaSize(PizzaSize.values()[sizeChoice-1]);
                    item.setPizzaPrice(item.getItemFood().getFoodPrice()+item.getPizzaSize().getPrice());
                    break;
                }
                System.err.println(InputMethods.ERROR_ALERT);
            }
            System.out.println("Choose pizza extras cheese: ");
            for (int i = 0; i < PizzaExtrasCheese.values().length; i++) {
                System.out.println((i + 1) + ". " + PizzaExtrasCheese.values()[i]);

            }
            while(true){
                int extrasCheeseChoice = InputMethods.getInteger();
                if (extrasCheeseChoice >= 1 && extrasCheeseChoice <= PizzaExtrasCheese.values().length) {
                    item.setPizzaExtrasCheese(PizzaExtrasCheese.values()[extrasCheeseChoice-1]);
                    item.setPizzaPrice(item.getPizzaPrice()+item.getPizzaExtrasCheese().getPrice());
                    break;
                }
                System.err.println(InputMethods.ERROR_ALERT);
            }
        }
        System.out.println("Enter quantity: ");
        item.setItemQuantity(InputMethods.getInteger());
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
        System.out.println("Quantity:" + item.getItemQuantity());
        System.out.println("Enter new quantity: ");
        item.setItemQuantity(InputMethods.getInteger());
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
    public void checkOut(FoodController foodController){
        cartController = new CartController(Main.currentLogin);
        this.foodController = foodController;
        User currentLogin = Main.currentLogin;

        List<CartItem> itemList = currentLogin.getCart();
        if(itemList.isEmpty()){
            System.out.println(Alert.EMPTY_LIST);
            return;
        }
        // kiểm tra số lượng trong kho
        for(CartItem item: itemList){
            Food food = foodController.findById(item.getItemFood().getFoodId());
            if(item.getItemQuantity()> food.getFoodStock()){
                System.err.println("The "+ food.getFoodName()+" is only have "+ food.getFoodStock()+" in stock, please reduce");
                return;
            }
        }
        Order newOrder = new Order();
        OrderController orderController = new OrderController() ;
        newOrder.setId(orderController.getNewId());
        // copy sp trong giỏ hàng sang hóa đơn
        newOrder.setOrderDetail(currentLogin.getCart());
        // cập nhật tổng tiền
        double total = 0;
        for (CartItem item: itemList) {
            if (item.getItemFood().getFoodCategory().getCategoryName().equals("Pizza")) {
                total += (item.getItemQuantity() * item.getPizzaPrice());
            } else {
                total += (item.getItemQuantity() * item.getItemFood().getFoodPrice());
            }
        }
        newOrder.setTotal(total);
        newOrder.setUserId(currentLogin.getId());
        System.out.println("Choose payment method: ");
        for (int i = 0; i < PaymentMethod.values().length; i++) {
            System.out.println((i + 1) + ". " + PaymentMethod.values()[i]);
            // 1. Cash
            // 2. Wallet
        }
        while(true){
            int payChoice = InputMethods.getInteger();
            if (payChoice >= 1 && payChoice <= PaymentMethod.values().length) {
                if(payChoice==2){
                    if(currentLogin.getWallet()< newOrder.getTotal()){
                        System.err.println("Your wallet is not enough money to pay, load more money or choose another method ");
                        return;
                    }
                    currentLogin.setWallet(currentLogin.getWallet()- newOrder.getTotal());
                }
                newOrder.setPaymentMethod(PaymentMethod.values()[payChoice-1]);
                break;
            }
            System.err.println(InputMethods.ERROR_ALERT);
        }
        System.out.println("Choose shipping method: ");
        for (int i = 0; i < ShippingMethod.values().length; i++) {
            System.out.println((i + 1) + ". " + ShippingMethod.values()[i]);
            // 1. Delivery
            // 2. Takeaway
        }
        while(true){
            int shipChoice = InputMethods.getInteger();
            if (shipChoice >= 1 && shipChoice <= PaymentMethod.values().length){
                newOrder.setShippingMethod(ShippingMethod.values()[shipChoice-1]);
                if(shipChoice==1){
                    newOrder.setTotal(total+newOrder.getShippingMethod().getPrice());
                    if(newOrder.getPaymentMethod().equals(PaymentMethod.WALLET)){
                        if(currentLogin.getWallet()< newOrder.getTotal()){
                            System.err.println("Your wallet is not enough money to pay, load more money or choose another method ");
                            return;
                        }
                    }
                    currentLogin.setWallet(currentLogin.getWallet()- newOrder.getTotal());
                    System.out.println("Enter receiver name: ");
                    newOrder.setReceiver(InputMethods.getString());
                    System.out.println("Enter phone number: ");
                    String tel;
                    while (true) {
                        tel = InputMethods.getString();
                        if (Validation.validateTel(tel)) {
                            newOrder.setPhoneNumber(tel);
                            break;
                        }
                        System.err.println(Alert.ERROR_TEL);
                    }
                    System.out.println("Enter address: ");
                    newOrder.setAddress(InputMethods.getString());
                    break;
                }
                    if (currentLogin.getName().equals("")){
                        System.out.println("Enter receiver: ");
                        newOrder.setReceiver(InputMethods.getString());
                        break;
                    }
                    newOrder.setReceiver(currentLogin.getName());
                    if (currentLogin.getTel().equals("")){
                        System.out.println("Enter phone number: ");
                        String tel;
                        while (true) {
                            tel = InputMethods.getString();
                            if (Validation.validateTel(tel)) {
                                break;
                            }
                            System.err.println(Alert.ERROR_TEL);
                        }
                        newOrder.setPhoneNumber(tel);
                        break;
                    }
                    newOrder.setPhoneNumber(currentLogin.getTel());
                    if (currentLogin.getAddress().equals("")){
                        System.out.println("Enter address: ");
                        newOrder.setAddress(InputMethods.getString());
                        break;
                    }
                    newOrder.setAddress(currentLogin.getAddress());
                    break;
                }
            System.err.println(InputMethods.ERROR_ALERT);
            }

        orderController.save(newOrder);
        System.out.println("\u001B[43mThank you!!!\u001B[0m");
        // giảm số lượng trong stock
        for(CartItem item: itemList){
            FoodController foodController1 = new FoodController();
            Food food = foodController1.findById(item.getItemFood().getFoodId());
            food.setFoodStock(food.getFoodStock()- item.getItemQuantity());
            foodController1.save(food);
        }
        clearCart();
    }
}
