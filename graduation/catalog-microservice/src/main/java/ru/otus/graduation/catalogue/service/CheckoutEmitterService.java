package ru.otus.graduation.catalogue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.Proposal;
import ru.otus.graduation.model.User;

@Service
@RequiredArgsConstructor
public class CheckoutEmitterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutEmitterService.class);
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfig config;
    private static final String MAIN_EXCHANGE = "main";
    private static final String USERS_QUEUES = "users";
    private static final String MAIN_USER_QUEUE = "main";
    private static final String PRODUCT_QUEUES = "products";
    private static final String PROPOSAL_QUEUE = "proposal";
    private final ObjectMapper objectMapper;

    public void emitUser(User user) {
        String queueName = config.getQueues().get(USERS_QUEUES).get(MAIN_USER_QUEUE);
        try {
            rabbitTemplate.convertAndSend(config.getExchanges().get(MAIN_EXCHANGE),
                    queueName,
                    objectMapper.writeValueAsString(user)
            );
        } catch (JsonProcessingException e) {
            LOGGER.info(e.getLocalizedMessage());
        }
    }

    public void emitProposal(Proposal proposal){
        String queueName = config.getQueues().get(PRODUCT_QUEUES).get(PROPOSAL_QUEUE);
        try {
            rabbitTemplate.convertAndSend(config.getExchanges().get(MAIN_EXCHANGE),
                    queueName,
                    objectMapper.writeValueAsString(proposal)
            );
        } catch (JsonProcessingException e) {
            LOGGER.info(e.getLocalizedMessage());
        }
    }
}
