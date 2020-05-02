package ru.otus.graduation.master.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Sender;
import ru.otus.graduation.model.StatusMessage;

@Service
@RequiredArgsConstructor
public class RabbitMqListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListenerService.class);
    private final ObjectMapper objectMapper;
    private final MasterDataEmitterService emitterService;
    private static final String STATUS_INIT_HANDLED = "Status INIT handled. Initiator: ";

    @RabbitListener(queues = "${application.rabbit.exchanges.status}")
    public void processStatusMessages(String message) throws JsonProcessingException {
        StatusMessage statusMessage = objectMapper.readValue(message, new TypeReference<StatusMessage>(){});
        switch (statusMessage.getStatus()) {
            case INIT:
                if (statusMessage.getSender() != Sender.POS) {
                    emitterService.emitLevels();
                }
                emitterService.emitPrices();
                LOGGER.info(String.format("%s %s", STATUS_INIT_HANDLED, statusMessage.getSender()));
                break;
        }
    }
}
