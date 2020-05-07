package ru.otus.graduation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.StatusMessage;

@Service
@RequiredArgsConstructor
public class StatusEmitterService {

    private final ApplicationConfig config;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusEmitterService.class);
    private static final String STATUS_EXCHANGE = "status";
    private final RabbitTemplate rabbitTemplate;
    private static final String STATUS_MESSAGE_SENT = "Status message sent. Status: ";

    public void emitStatusBroadcast(StatusMessage message) {
        try {
            rabbitTemplate.convertAndSend(config.getExchanges().get(STATUS_EXCHANGE),
                    objectMapper.writeValueAsString(message)
            );
        } catch (JsonProcessingException e) {
            LOGGER.info(e.getLocalizedMessage());
        }
        LOGGER.info(String.format("%s %s", STATUS_MESSAGE_SENT, message.getStatus()));
    }

    public void emitStatusToSpecificQueue(String exchangeName, String queueGroup, String queue, StatusMessage message){
        String queueName = config.getQueues().get(queueGroup).get(queue);
        try {
            rabbitTemplate.convertAndSend(config.getExchanges().get(exchangeName),
                    queueName,
                    objectMapper.writeValueAsString(message)
            );
        } catch (JsonProcessingException e) {
            LOGGER.info(e.getLocalizedMessage());
        }
    }
}
