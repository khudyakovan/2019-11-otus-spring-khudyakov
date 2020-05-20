package ru.otus.graduation.catalogue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.Proposal;
import ru.otus.graduation.model.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutEmitterServiceImpl implements CheckoutEmitterService {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfig config;
    private static final String MAIN_EXCHANGE = "main";
    private static final String USERS_QUEUES = "users";
    private static final String MAIN_USER_QUEUE = "main";
    private static final String PRODUCT_QUEUES = "products";
    private static final String PROPOSAL_QUEUE = "proposal";
    private final ObjectMapper objectMapper;

    public void emitUser(User user) {
        String queueName = config.getQueueNameByGroupNameAndPropertyName(USERS_QUEUES, MAIN_USER_QUEUE);
        try {
            rabbitTemplate.convertAndSend(this.getMainExchange(),
                    queueName,
                    objectMapper.writeValueAsString(user)
            );
        } catch (JsonProcessingException e) {
            log.info(e.getLocalizedMessage());
        }
    }

    public void emitProposal(Proposal proposal){
        String queueName = config.getQueueNameByGroupNameAndPropertyName(PRODUCT_QUEUES, PROPOSAL_QUEUE);
        try {
            rabbitTemplate.convertAndSend(this.getMainExchange(),
                    queueName,
                    objectMapper.writeValueAsString(proposal)
            );
        } catch (JsonProcessingException e) {
            log.info(e.getLocalizedMessage());
        }
    }

    private String getMainExchange(){
        return this.config.getExchangeByPropertyName(MAIN_EXCHANGE);
    }
}
