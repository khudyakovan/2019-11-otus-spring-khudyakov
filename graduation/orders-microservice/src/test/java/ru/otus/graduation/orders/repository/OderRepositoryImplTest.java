package ru.otus.graduation.orders.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.orders.config.MongoDbInit;
import ru.otus.graduation.orders.config.MongockConfig;
import ru.otus.graduation.repository.order.OrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Import({MongockConfig.class, MongoDbInit.class})
public class OderRepositoryImplTest {

    private static final long ORDER_NUMBER = 1000L;
    private static final String PHONE_NUMBER = "9174704000";
    @Autowired
    OrderRepository orderRepository;

    @Test
    void findByOrderNumber() {
        assertNotNull(orderRepository.findByOrderNumber(ORDER_NUMBER));
    }

    @Test
    void findByMobilePhone() {
        assertNotNull(orderRepository.findByMobilePhoneOrderByCurrentDateDesc(PHONE_NUMBER));
    }

    @Test
    void findActiveOrders() {
        List<Order> orders = orderRepository.findAll();
        assertAll(
                () -> assertNotNull(orders),
                () -> assertNotEquals(0, orders)
        );
    }
}
