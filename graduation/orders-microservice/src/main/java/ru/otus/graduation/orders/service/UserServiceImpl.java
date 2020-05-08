package ru.otus.graduation.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.User;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final ApplicationConfig config;
    private final RabbitTemplate rabbitTemplate;
    private static final String MAIN_EXCHANGE = "main";
    private static final String QUEUES_GROUP = "users";
    private static final String FIND_BY_NAME_QUEUE = "name";
    private static final String BLANK_USERNAME = "Username mustn't be blank!";

    /*
    Вызов внешнего сервиса по RPC
     */

    @Override
    public User findByUsername(String username) {
        if(username.isBlank()) {
            log.warn(BLANK_USERNAME);
            return null;
        }
        return (User)rabbitTemplate.convertSendAndReceive(
                config.getExchanges().get(MAIN_EXCHANGE),
                config.getQueues().get(QUEUES_GROUP).get(FIND_BY_NAME_QUEUE),
                username);
    }
}
