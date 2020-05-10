package ru.otus.graduation.orders.service;

import ru.otus.graduation.model.Order;
import ru.otus.graduation.orders.dto.OrderDetailsDto;

import java.util.List;

public interface OrderService {

    Order findById(String id);

    Order findByOrderNumber(long orderNumber);

    Order findByProposalNumber(long proposalNumber);

    List<Order> findByMobilePhone(String mobilePhone);

    List<Order> findAll();

    List<Order> findActiveOrders();

    void emitOrderStatus(Order order);

    long findMaxOrderNumber();

    Order save(Order order);

    Order save(OrderDetailsDto orderDetailsDto);

    OrderDetailsDto getOrderDetailsDto(long orderNumber);
}
