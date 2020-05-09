package ru.otus.graduation.orders.service;

import org.bson.types.ObjectId;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Status;

import java.util.List;

public interface OrderService {
    Order findByOrderNumber(long orderNumber);

    Order findByProposalNumber(long proposalNumber);

    List<Order> findByMobilePhone(String mobilePhone);

    List<Order> findAll();

    List<Order> findActiveOrders();

    Order changeStatus(ObjectId orderId, Status status);

    void emitOrderStatus(Order order);

    long findMaxOrderNumber();

    Order save(Order order);
}
