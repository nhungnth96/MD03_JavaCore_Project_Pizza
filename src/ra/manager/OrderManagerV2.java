package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Message;
import ra.config.Validation;
import ra.controller.FoodController;
import ra.controller.OrderController;
import ra.controller.UserController;
import ra.model.cart.CartItem;
import ra.model.food.Food;
import ra.model.order.Order;
import ra.model.user.RoleName;
import ra.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class OrderManagerV2 {
    private OrderController orderController;

    public OrderManagerV2() {
        orderController = new OrderController();
        if(Main.currentLogin.getRoles().contains(RoleName.ADMIN)){
            while (true) {
                System.out.println("======ORDER MANAGER======");
                System.out.println("1. View all order"); // contains detail - okay
                System.out.println("2. View pending confirm order"); // okay
                System.out.println("3. View delivering order"); // view shipper road
                System.out.println("4. View delivered order"); // view invoice
                System.out.println("5. View canceled order");
                System.out.println("6. Change order status"); // okay
                System.out.println("0. Back");
                System.out.println("Enter choice: ");
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        viewAllOrder();
                        break;
                    case 2:
                        viewOrderByCode((byte) 1);
                        break;
                    case 3:
                        viewOrderByCode((byte) 2);
                        break;
                    case 4:
                        viewInvoice();
                        break;
                    case 5:
                        viewOrderByCode((byte) 3);
                        break;
                    case 6:
                        changeOrderStatus();
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

        } else {
            while (true) {
                System.out.println("======MY ORDER======");
                System.out.println("1. View all order"); // contains detail - okay
                System.out.println("2. View pending confirm order"); // okay
                System.out.println("3. View delivering order"); // view shipper road
                System.out.println("4. View delivered order"); // view invoice
                System.out.println("5. View canceled order");
                System.out.println("6. Cancel order"); // okay
                System.out.println("0. Back");
                System.out.println("Enter choice: ");
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        viewAllOrder();
                        break;
                    case 2:
                        viewOrderByCode((byte) 1);
                        break;
                    case 3:
                        viewOrderByCode((byte) 2);
                        break;
                    case 4:
                        viewInvoice();

                        break;
                    case 5:
                        viewOrderByCode((byte) 3);
                        break;
                    case 6:
                        cancelOrder();
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
    }

    public void viewAllOrder() {
        List<Order> orderList;
        if(Main.currentLogin.getRoles().contains(RoleName.ADMIN)){

            orderList = orderController.getAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        if (orderList.isEmpty()) {
            System.err.println("\u001B[33mEmpty order list\u001B[0m");
            return;
        }
        if(Main.currentLogin.getRoles().contains(RoleName.ADMIN)){
            for (Order order : orderList) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUserId());
                displayOrderForAdmin(order,orderUser);
            }
        } else {
            for (Order order : orderList) {
                System.out.println(order);
            }
        }

    }

    public void viewOrderByCode(byte code) {
        List<Order> orderList;
        if(Main.currentLogin.getRoles().contains(RoleName.ADMIN)){
            orderList = orderController.getAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == code) {
                filter.add(order);
            }
        }
        if (filter.isEmpty()) {
            System.out.println("Empty");
            return;
        }
        if(Main.currentLogin.getRoles().contains(RoleName.ADMIN)){
            for (Order order : filter) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUserId());
                displayOrderForAdmin(order,orderUser);
            }
        }
        else {
            for (Order order : orderList) {
                System.out.println(order);
            }
        }
    }
    public void viewInvoice() {
        List<Order> orderList;
        if(Main.currentLogin.getRoles().contains(RoleName.ADMIN)){
             orderList = orderController.getAll();
        } else {
           orderList = orderController.findOrderByUserId();
        }
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == 2) {
                filter.add(order);
            }
        }
        if(filter.isEmpty()){
            System.out.println("Empty");
            return;
        }
//        for (Order order : filter) {
//            System.out.println("\n");
//            System.out.printf("----------Invoice----------\n");
//            System.out.printf("          Id:%5d                   \n", order.getId());
//            System.out.printf("          Date:                  \n", order.getBuyDate());
//            System.out.println("          Infomation                   ");
//            System.out.printf("Receiver: " + order.getReceiver() + " | Phone: " + order.getPhoneNumber() + "\n");
//            System.out.println("Address " + order.getAddress());
//            System.out.println("----------Detail----------");
//            for (CartItem item : order.getOrderDetail()) {
//                System.out.println(item);
//            }
//            System.out.println("Total: " + Validation.formatPrice(order.getTotal()));
//            System.out.println("--------Thank you---------");
//        }
        if(Main.currentLogin.getRoles().contains(RoleName.ADMIN)){
            for (Order order : filter) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUserId());
                displayOrderForAdmin(order, orderUser);
            }
        }else {
            for (Order order : filter) {
                System.out.println("\n");
                System.out.printf("----------Invoice----------\n");
                System.out.printf("          Id:%5d                   \n", order.getId());
                System.out.printf("          Date:                  \n", order.getBuyDate());
                System.out.println("          Infomation                   ");
                System.out.printf("Receiver: " + order.getReceiver() + " | Phone: " + order.getPhoneNumber() + "\n");
                System.out.println("Address " + order.getAddress());
                System.out.println("----------Detail----------");
                for (CartItem item : order.getOrderDetail()) {
                    System.out.println(item);
                }
                System.out.println("Total: " + Validation.formatPrice(order.getTotal()));
                System.out.println("--------Thank you---------");
            }
        }





    }
    public void cancelOrder() {
        System.out.println("Enter order ID: ");
        int cancelOrderId = InputMethods.getInteger();
        Order order = orderController.findById(cancelOrderId);
        if (order == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        if (order.getStatus() == 3) {
            System.err.println("This order has been canceled");
            return;
        }
        if (order.getStatus() == 1) {
            System.out.println("Are you sure want to cancel ?");
            System.out.println("1.Yes");
            System.out.println("2.No");
            System.out.println("Enter your choice: ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                order.setStatus((byte) 3);
                for (CartItem item : order.getOrderDetail()) {
                    FoodController foodController = new FoodController();
                    Food food = foodController.findById(item.getItemFood().getFoodId());
                    food.setFoodStock(food.getFoodStock() + item.getItemQuantity());
                    foodController.save(food);
                }
                orderController.save(order);
                System.out.println(Alert.SUCCESS);
            }
        }
    }
    public void changeOrderStatus(){
        System.out.println("Enter order ID: ");
        int changeOrderStatusId = InputMethods.getInteger();
        Order changeOrderStatus = orderController.findById(changeOrderStatusId);
        if (changeOrderStatus == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        if (changeOrderStatus.getStatus() == 3) {
            System.err.println("This order has been canceled");
            return;
        }
        if (changeOrderStatus.getStatus() == 2) {
            System.err.println("This order is delivering, can't change status");
            return;
        }
        if (changeOrderStatus.getStatus() == 1) {
            System.out.println("Choose status: ");
            System.out.println("1.Accept");
            System.out.println("2.Deny");
            System.out.println("Enter your choice: ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                changeOrderStatus.setStatus((byte) 2);
                orderController.save(changeOrderStatus);
                System.out.println(Alert.SUCCESS);
            }
            else if (choice==2){
                changeOrderStatus.setStatus((byte) 3);
                for (CartItem item : changeOrderStatus.getOrderDetail()) {
                    FoodController foodController = new FoodController();
                    Food food = foodController.findById(item.getItemFood().getFoodId());
                    food.setFoodStock(food.getFoodStock() + item.getItemQuantity());
                    foodController.save(food);
                }
                orderController.save(changeOrderStatus);
                System.out.println(Alert.SUCCESS);
            } else {
                System.err.println(InputMethods.ERROR_ALERT);
            }
        }
    }


    private static void displayOrderForAdmin(Order order, User orderUser) {
        System.out.println("---------------------------------------"+"\n"+
                "ID: " + order.getId() + " | Date: " + order.getBuyDate() + "\n" +
                "Buyer: "+ orderUser.getName() + " | Phone number: "+ orderUser.getTel() + "\n"+
                "Detail: " +
                order.getOrderDetail().toString().replace(", ","\n").replace("[","\n").replace("]","\n").replace("ID: ","") +
                "Total: " + Validation.formatPrice(order.getTotal()) + " | Status: " + Message.getStatusByCode(order.getStatus()) + "\n" +
                "Receiver: " + order.getReceiver() + "\n"+
                "Phone number: " + order.getPhoneNumber() + "\n"+
                "Address: " + order.getAddress());
    }
}

