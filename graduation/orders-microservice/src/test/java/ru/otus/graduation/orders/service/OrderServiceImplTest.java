package ru.otus.graduation.orders.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    private static final long ORDER_NUMBER = 1;
    private static final String PHONE_NUMBER = "9174704000";

    @Test
    void findByOrderNumber() {
        assertNotNull(orderService.findByOrderNumber(ORDER_NUMBER));
    }

    @Test
    void findByMobilePhone() {
        assertNotNull(orderService.findByMobilePhone(PHONE_NUMBER));
    }

    @Test
    void findActiveOrders() {
        assertAll(
                () -> assertNotNull(orderService.findActiveOrders()),
                () -> assertNotEquals(0, orderService.findActiveOrders())
        );
    }
}
