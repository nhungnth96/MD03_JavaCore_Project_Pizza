package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Validation;
import ra.controller.FoodController;
import ra.controller.OrderController;
import ra.model.cart.CartItem;
import ra.model.food.Food;
import ra.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private OrderController orderController;
    public OrderManager() {
        orderController = new OrderController();
        while (true) {
            System.out.println("======MY ORDER======");
                    System.out.println("1. View all order");
                    System.out.println("2. View waiting order");
                    System.out.println("3. View confirmed order");
                    System.out.println("4. View canceled order");
                    System.out.println("5. View order details");
                    System.out.println("0. Back");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    showAllOrder();
                    break;
                case 2:
                    showOrderByCode((byte) 1);
                    break;
                case 3:
                    showOrderByCode((byte) 2);
                    break;
                case 4:
                    showOrderByCode((byte) 3);
                    break;
                case 5:
                    showOrderDetail();
                    break;
                case 0:
                    break;
                default:
                    System.err.println(InputMethods.FORMAT_ERROR);
            }
            if (choice == 0) {
                break;
            }
        }
    }
    public void showAllOrder(){
        List<Order> orderList = orderController.findOrderByUserId();
        if(orderList.isEmpty()){
            System.err.println("\"\\u001B[33mEmpty order list\\u001B[0m\"");
            return;
        }
        for (Order order : orderList) {
            System.out.println(order);
        }
    }
    public void showOrderByCode(byte code){
        List<Order> orderList = orderController.findOrderByUserId();
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == code) {
                filter.add(order);
            }
        }
        if(filter.isEmpty()){
            System.out.println("Empty");
            return;
        }
        for (Order order : filter) {
            System.out.println(order);
        }
    }
    public void showOrderDetail(){
        System.out.println("Enter order ID: ");
        int id = InputMethods.getInteger();
        Order order = orderController.findById(id);
        if(order == null){
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println("\n");
        System.out.printf("----------Invoice----------\n");
        System.out.printf("          Id:%5d                   \n",order.getId());
        System.out.println("          Infomation                   ");
        System.out.printf("Receiver: " + order.getReceiver()+" | Phone: "+order.getPhoneNumber()+"\n");
        System.out.println("Address " + order.getAddress()                   );
        System.out.println("----------Detail----------");
        for (CartItem item : order.getOrderDetail()) {
            System.out.println(item);
        }
        System.out.println("Total: "+ Validation.formatPrice(order.getTotal()));
        System.out.println("--------Thank you---------");;
        if(order.getStatus()==1){
            System.out.println("Do you want to cancel this order?");
            System.out.println("1.Yes");
            System.out.println("2.No");
            System.out.println("Enter your choice: ");
            int choice = InputMethods.getInteger();
            if(choice==1){
                order.setStatus((byte) 3);
                for(CartItem item: order.getOrderDetail()){
                    FoodController foodController = new FoodController();
                    Food food = foodController.findById(item.getItemFood().getFoodId());
                    food.setFoodStock(food.getFoodStock()+ item.getItemQuantity());
                    foodController.save(food);
                }
                orderController.save(order);
            }
        }
    }
}
