package ru.otus.graduation.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.StatusMessage;
import ru.otus.graduation.model.User;
import ru.otus.graduation.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RabbitMqListenerService {

    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListenerService.class);
    private final ObjectMapper objectMapper;
    private static final String USER_STATUS_MESSAGE="DATA RECEIVED AND SAVED: user";

    @RabbitListener(queues = "${application.rabbit.queues.users.main}")
    public void processUsers(String message) throws JsonProcessingException {
        User user = objectMapper.readValue(message, new TypeReference<User>(){});
        userRepository.save(user);
        LOGGER.info(USER_STATUS_MESSAGE);
    }

    @RabbitListener(queues = "${application.rabbit.exchanges.status}")
    public void processStatusMessages(String message) throws JsonProcessingException {
        StatusMessage statusMessage = objectMapper.readValue(message, new TypeReference<StatusMessage>(){});
        LOGGER.info("Status Message Received and Hanled: " + statusMessage);
    }
}
