package ru.otus.graduation.pos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.Order;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final ApplicationConfig config;
    private final RabbitTemplate rabbitTemplate;
    private static final String MAIN_EXCHANGE = "main";
    private static final String QUEUES_GROUP = "products";
    private static final String FIND_BY_ORDER_NUMBER= "pos-get-order";

    @Override
    public Order findByOrderNumber(long orderNumber) {
        return (Order) rabbitTemplate.convertSendAndReceive(
                config.getExchanges().get(MAIN_EXCHANGE),
                config.getQueues().get(QUEUES_GROUP).get(FIND_BY_ORDER_NUMBER),
                orderNumber);
    }
}
