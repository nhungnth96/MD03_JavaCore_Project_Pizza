package ra.controller;

import ra.model.order.Order;
import ra.service.OrderService;

import java.util.List;

public class OrderController {
    private OrderService orderService;

    public OrderController() {
        orderService = new OrderService();
    }
    public void save(Order order) {
        orderService.save(order);
    }
    public Order findById(Integer id) {
        return orderService.findById(id);
    }
    public List<Order> findOrderByUserId(){
        return orderService.findOrderByUserId();
    }
    public int getNewId() {
        return orderService.getNewId();
    }

}
