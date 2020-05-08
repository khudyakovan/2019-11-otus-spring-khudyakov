package ru.otus.graduation.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.StatusMessage;
import ru.otus.graduation.model.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqListenerService {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private static final String USER_STATUS_MESSAGE = "DATA RECEIVED AND SAVED: user";
    private static final String USER_NOT_FOUND = "User with username = %s not found";

    @RabbitListener(queues = "${application.rabbit.queues.users.main}")
    public void processUsers(String message) throws JsonProcessingException {
        User user = objectMapper.readValue(message, new TypeReference<User>() {
        });
        userService.save(user);
        log.info(USER_STATUS_MESSAGE);
    }

    @RabbitListener(queues = "${application.rabbit.queues.users.name}")
    public User handleFindUserByUserNameRequest(String message){
        User user = userService.findByUsername(message);
        if (user == null) {
            log.warn(String.format(USER_NOT_FOUND, message));
            return null;
        }
        return user;
    }

    @RabbitListener(queues = "${application.rabbit.exchanges.status}")
    public void processStatusMessages(String message) throws JsonProcessingException {
        StatusMessage statusMessage = objectMapper.readValue(message, new TypeReference<StatusMessage>() {
        });
        log.info("Status Message Received and Hanled: " + statusMessage);
    }
}
