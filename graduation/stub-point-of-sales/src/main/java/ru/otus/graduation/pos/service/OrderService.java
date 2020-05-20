package ru.otus.graduation.pos.service;

import ru.otus.graduation.model.Order;

public interface OrderService {
    Order findByOrderNumber(long orderNumber);
}
