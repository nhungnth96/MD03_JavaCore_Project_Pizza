package ra.service;

import ra.database.DataBase;
import ra.manager.Main;
import ra.model.order.Order;
import ra.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders;
        public OrderService() {
        List<Order> orderList = (List<Order>) DataBase.readFromFile(DataBase.ORDER_PATH);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        this.orders = orderList;
    }
    public List<Order> getAll() {
        return orders;
    }
    public void save(Order order) {
//        for (int i = 0; i < orders.size(); i++) {
//            if (orders.get(i).getId()==order.getId()){
//                orders.set(i,order);
//                DataBase.writeToFile(orders, DataBase.ORDER_PATH);
//                return;
//            }
//        }
        if (findByIdForAdmin(order.getId()) == null) {
            // add
            orders.add(order);
        } else {
            // update
            orders.set(orders.indexOf(findByIdForAdmin(order.getId())), order);
        }
//         save to DB
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
    public Order findByIdForAdmin(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
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
