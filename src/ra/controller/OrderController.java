package ra.controller;

import ra.model.order.Order;
import ra.service.OrderService;

import java.util.List;

public class OrderController implements IGenericController<Order, Integer> {
    private OrderService orderService;

    public OrderController() {
        orderService = new OrderService();
    }

    @Override
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @Override
    public void save(Order order) {
        orderService.save(order);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Order findById(Integer id) {
        return orderService.findById(id);
    }
    public Order findByIdForAdmin(Integer id) {
        return orderService.findByIdForAdmin(id);
    }

    public List<Order> findOrderByUserId() {
        return orderService.findOrderByUserId();
    }

    @Override
    public int getNewId() {
        return orderService.getNewId();
    }

}
