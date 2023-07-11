package ra.manager;

import ra.config.Alert;
import ra.config.InputMethods;
import ra.config.Message;
import ra.config.Validation;
import ra.controller.FoodController;
import ra.controller.OrderController;
import ra.controller.UserController;
import ra.model.cart.CartItem;
import ra.model.feedback.Feedback;
import ra.model.food.Food;
import ra.model.order.Order;
import ra.model.order.ShippingMethod;
import ra.model.user.RoleName;
import ra.model.user.User;
import sun.rmi.server.UnicastServerRef;

import java.util.ArrayList;
import java.util.List;


public class OrderManagerV2 {
    private OrderController orderController;

    public OrderManagerV2() {
        orderController = new OrderController();
        if (Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
            while (true) {
                System.out.println("======ORDER MANAGER======");
                System.out.println("1. View all order"); // contains detail - okay
                System.out.println("2. View pending confirm order"); // okay
                System.out.println("3. View confirmed order");
                System.out.println("4. View delivered order"); // view invoice
                System.out.println("5. View canceled order");
                System.out.println("6. Change order status"); // okay
                System.out.println("7. View feedback"); // okay
                System.out.println("0. Back");
                System.out.println("Enter choice: ");
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        viewAllOrder();
                        break;
                    case 2: // pending
                        viewOrderByCode((byte) 2);
                        break;
                    case 3: // confirm
                        viewOrderByCode((byte) 3);
                        break;
                    case 4: // delivered
                        viewInvoice();
                        break;
                    case 5: // cancel
                        viewOrderByCode((byte) 6);
                        break;
                    case 6:
                        changeOrderStatus();
                        break;
                    case 7:
                        viewFeedback();
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
                System.out.println("3. View accepted order");
                System.out.println("4. View delivered order"); // view invoice
                System.out.println("5. View canceled order");
                System.out.println("6. Cancel order"); // okay
                System.out.println("7. Give a feedback");
                System.out.println("0. Back");
                System.out.println("Enter choice: ");
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        viewAllOrder();
                        break;
                    case 2: // pending
                        viewOrderByCode((byte) 2);
                        break;
                    case 3: // accepted
                        viewOrderByCode((byte) 3);
                        break;
                    case 4: // delivered
                        viewInvoice();
                        break;
                    case 5: // cancel
                        viewOrderByCode((byte) 6);
                        break;
                    case 6:
                        cancelOrder();
                        break;
                    case 7:
                        giveAFeedback();
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
    private static void displayOrderForAdmin(Order order, User orderUser) {

        System.out.println("---------------------------------------" + "\n" +
                "Order ID: " + order.getId() + " | Date: " + order.getBuyDate() + "\n" +
                "Buyer: " + orderUser.getName() + " | Phone number: " + orderUser.getTel() + "\n" +
                "Detail: " +
                order.getOrderDetail().toString().replace(", ", "\n").replace("[", "\n").replace("]", "\n").replace("ID: ", "") +
                "Total: " + Validation.formatPrice(order.getTotal()) + " | Status: " + Message.getStatusByCode(order.getStatus()) + "\n" +
                "Receiver: " + order.getReceiver() + "\n" +
                "Phone number: " + order.getPhoneNumber() + "\n" +
                "Address: " + order.getAddress());
    }

    public void viewAllOrder() {
        List<Order> orderList;
        if (Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {

            orderList = orderController.getAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        if (orderList.isEmpty()) {
            System.err.println(Alert.EMPTY_LIST);
            return;
        }
        if (Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
            for (Order order : orderList) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUserId());
                displayOrderForAdmin(order, orderUser);
            }
        } else {
            for (Order order : orderList) {
                System.out.println(order);
            }
        }

    }
    public void viewOrderByCode(byte code) {
        List<Order> orderList;
        if (Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
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
            System.err.println(Alert.EMPTY_LIST);
            return;
        }
        if (Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
            for (Order order : filter) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUserId());
                displayOrderForAdmin(order, orderUser);
            }
        } else {
            for (Order order : filter) {
                System.out.println(order);
            }
        }
    }

    public void viewInvoice() {
        List<Order> orderList;
        if (Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
            orderList = orderController.getAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == 5) {
                filter.add(order);
            }
        }
        if (filter.isEmpty()) {
            System.err.println(Alert.EMPTY_LIST);
            return;
        }

        if (Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
            for (Order order : filter) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUserId());
                displayOrderForAdmin(order, orderUser);
            }
        } else {
            for (Order order : filter) {
                System.out.println("\n");
                System.out.printf("----------Invoice----------\n");
                System.out.printf("          Id:%5d                   \n", order.getId());
                System.out.printf("          Date:%15s                  \n", order.getBuyDate());
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
        Order cancelOrder = orderController.findById(cancelOrderId);
        if (cancelOrder == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        switch (cancelOrder.getStatus()){
            case (byte)2:
                cancelByCustomer(cancelOrder);
                break;
            case (byte)3:
                System.err.println("The order is preparing, cancel is deny!");
                break;
            case (byte)4:
                System.err.println("The order is delivering, cancel is deny!");
                break;
            case (byte)5:
                System.err.println("The order is delivered");
                break;
            case (byte)6:
                System.err.println("The order has been canceled");
                break;
            default:
                System.err.println(InputMethods.ERROR_ALERT);
        }
    }
    private void cancelByCustomer(Order order) {
        System.out.println("Are you sure want to cancel ?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("Enter your choice: ");
        int choice = InputMethods.getInteger();
        if (choice == 1) {
            order.setStatus((byte) 6);
            for (CartItem item : order.getOrderDetail()) {
                FoodController foodController = new FoodController();
                Food food = foodController.findById(item.getItemFood().getFoodId());
                food.setFoodStock(food.getFoodStock() + item.getItemQuantity());
                foodController.save(food);

            }
            UserController userController = new UserController();
            Main.currentLogin.setWallet(Main.currentLogin.getWallet()+ order.getTotal());
            userController.save(Main.currentLogin);
            orderController.save(order);

            System.out.println(Alert.SUCCESS);
        }
    }

    public void changeOrderStatus() {
        System.out.println("Enter order ID: ");
        int changeOrderStatusId = InputMethods.getInteger();
        Order changeOrderStatus = orderController.findByIdForAdmin(changeOrderStatusId);
        if (changeOrderStatus == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        switch (changeOrderStatus.getStatus()){
            case (byte)2:
                confirm(changeOrderStatus);
                break;
            case (byte)3:
                System.err.println("The order is preparing, cancel is deny!");
                break;
            case (byte)4:
                System.err.println("The order is delivering, cancel is deny!");
                break;
            case (byte)5:
                System.err.println("The order is delivered to customer");
                break;
            case (byte) 6:
                System.err.println("The order has been canceled by customer");
                break;
            default:
                System.err.println(InputMethods.ERROR_ALERT);
        }
    }
    private void confirm(Order changeOrderStatus) {
        System.out.println("Enter choice: ");
        System.out.println("1. Confirm");
        System.out.println("2. Cancel");
        System.out.println("0. Back");
        int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    preparingAndDelivering(changeOrderStatus);
                    break;
                case 2:
                    cancelByAdmin(changeOrderStatus);
                    break;
                case 0:
                    break;
                default:
                    System.err.println(InputMethods.ERROR_ALERT);
            }
    }
    private void preparingAndDelivering(Order changeOrderStatus) {
        changeOrderStatus.setStatus((byte) 3);
        orderController.save(changeOrderStatus);
        Thread prepareThread = new Thread(() -> {
            try {
                Thread.sleep(Order.PREPARE_TIME);
                changeOrderStatus.setStatus((byte) 4);
                orderController.save(changeOrderStatus);
                Thread deliveryThread = new Thread(() -> {
                    try {
                        Thread.sleep(Order.DELIVERY_TIME);
                        changeOrderStatus.setStatus((byte) 5);
                        System.out.println("Order ID "+changeOrderStatus.getId()+ " is DELIVERED");
                        orderController.save(changeOrderStatus);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                deliveryThread.start();
                System.out.println("Order ID "+changeOrderStatus.getId()+" is DELIVERING");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        prepareThread.start();
        System.out.println("Order ID "+changeOrderStatus.getId()+" is PREPARING");
    }
    private void cancelByAdmin(Order changeOrderStatus) {
        changeOrderStatus.setStatus((byte) 6);
        orderController.save(changeOrderStatus);
        FoodController foodController = new FoodController();
        for (CartItem item : changeOrderStatus.getOrderDetail()) {
            Food food = foodController.findById(item.getItemFood().getFoodId());
            food.setFoodStock(food.getFoodStock() + item.getItemQuantity());
            foodController.save(food);
        }
        UserController userController = new UserController();
        int buyUserId = changeOrderStatus.getUserId();
        User buyUser = userController.findById(buyUserId);
        buyUser.setWallet(buyUser.getWallet() + changeOrderStatus.getTotal());
        userController.save(buyUser);
        System.out.println(Alert.SUCCESS);
    }


    public void giveAFeedback() {
        List<Order> orderList;
        if (Main.currentLogin.getRoles().contains(RoleName.ADMIN)) {
            orderList = orderController.getAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == 5) {
                filter.add(order);
            }
        }
        if (filter.isEmpty()) {
            System.out.println("Empty");
            return;
        }

        System.out.println("Enter order ID: ");
        int feedbackOrderId = InputMethods.getInteger();
        Order feedbackOrder = find(feedbackOrderId, filter);
        if (feedbackOrder == null) {
            System.err.println(Alert.NOT_FOUND);
            return;
        }
        System.out.println("Enter feedback: ");
        Feedback feedback = new Feedback();
        feedback.setFeedbackContent(InputMethods.getString());
        feedbackOrder.getFeedbackList().add(feedback);
        orderController.save(feedbackOrder);
        System.out.println(Alert.SUCCESS);
    }
    public void viewFeedback(){
        for (Order order: orderController.getAll()){
            System.out.println(order.getFeedbackList());
        }
    }
    public Order find(int id, List<Order> orderList) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

}

