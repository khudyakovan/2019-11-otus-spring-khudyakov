package ru.otus.graduation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.StatusMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusEmitterServiceImpl implements StatusEmitterService {

    private final ApplicationConfig config;
    private final ObjectMapper objectMapper;
    private static final String STATUS_EXCHANGE = "status";
    private final RabbitTemplate rabbitTemplate;
    private static final String STATUS_MESSAGE_SENT = "Status message sent. Status: ";

    public void emitStatusBroadcast(StatusMessage message) {
        try {
            //rabbitTemplate.convertAndSend(config.getExchanges().get(STATUS_EXCHANGE),
            rabbitTemplate.convertAndSend(config.getExchangeByPropertyName(STATUS_EXCHANGE),
                    objectMapper.writeValueAsString(message)
            );
        } catch (JsonProcessingException e) {
            log.info(e.getLocalizedMessage());
        }
        log.info(String.format("%s %s", STATUS_MESSAGE_SENT, message.getStatus()));
    }

    public void emitStatusToSpecificQueue(String exchangeName, String queueGroup, String queue, Object message){
        //String queueName = config.getQueues().get(queueGroup).get(queue);
        String queueName = config.getQueueNameByGroupNameAndPropertyName(queueGroup, queue);
        try {
            rabbitTemplate.convertAndSend(config.getExchangeByPropertyName(exchangeName),
                    queueName,
                    objectMapper.writeValueAsString(message)
                    );
        } catch (JsonProcessingException e) {
            log.info(e.getLocalizedMessage());
        }
    }
}
