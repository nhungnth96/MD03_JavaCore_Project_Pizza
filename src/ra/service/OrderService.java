package ra.service;

import ra.database.DataBase;
import ra.manager.Main;
import ra.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders;

    public OrderService() {
        List<Order> orderList = (List<Order>) DataBase.readFromFile(DataBase.PRODUCT_PATH);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        this.orders = orderList;
    }

    public void save(Order order) {
        if (findById(order.getId()) == null) {
            // add
            orders.add(order);
        } else {
            // update
            orders.set(orders.indexOf(findById(order.getId())), order);
        }
        // save to DB
        DataBase.writeToFile(orders, DataBase.ORDER_PATH);

    }

    public int getNewId() {
        int maxId = 0;
        for (Order order : orders) {
            if (order.getId() > maxId) {
                maxId = order.getId();
            }
        }
        return maxId + 1;
    }

    public Order findById(int id) {
        for (Order order : findOrderByUserId()) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public List<Order> findOrderByUserId() {
        List<Order> findList = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == Main.currentLogin.getId()) {
                findList.add(order);
            }
        }
        return findList;
    }
}