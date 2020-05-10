package ru.otus.graduation.orders.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.orders.dto.OrderDetailsDto;

import java.util.List;

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
        List<Order> orders = orderService.findActiveOrders();
        assertAll(
                () -> assertNotNull(orders),
                () -> assertNotEquals(0, orders)
        );
    }

    @Test
    void getOrderDetailsDto() {
        OrderDetailsDto dto = orderService.getOrderDetailsDto(ORDER_NUMBER);
        assertAll(
                () -> assertNotNull(dto.getOrderItems()),
                () -> assertNotEquals(0, dto.getOrderItems())
        );
        System.out.println(dto);
    }
}
