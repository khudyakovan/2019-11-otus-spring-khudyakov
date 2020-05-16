package ru.otus.graduation.master.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.Level;
import ru.otus.graduation.model.Product;
import ru.otus.graduation.repository.master.LevelRepository;
import ru.otus.graduation.repository.master.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterDataEmitterService {

    private final LevelRepository levelRepository;
    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfig config;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterDataEmitterService.class);
    private static final String HIERARCHY_LEVELS_SENT = "Hierarchy levels sent";
    private static final String PLU_PRICES_AND_STOCKS_SENT = "PLU, prices and stocks sent";
    private static final String LEVELS_QUEUES = "levels";
    private static final String PRICES_QUEUES = "prices";
    private static final String MAIN_EXCHANGE= "main";

    @Scheduled(cron = "0 0 01 * * ?")
    public void emitLevels(){
//        Map<String, String> queueNames = config.getQueues().get(LEVELS_QUEUES);
//        List<Level> levels = levelRepository.findAll();
//        queueNames.forEach((key, value) -> {
//            try {
//                rabbitTemplate.convertAndSend(config.getExchanges().get(MAIN_EXCHANGE),
//                        value,
//                        objectMapper.writeValueAsString(levels)
//                );
//            } catch (JsonProcessingException e) {
//                LOGGER.info(e.getLocalizedMessage());
//            }
//        });

        List<String> queueNames = config.getAllQueueNamesByGroupName(LEVELS_QUEUES);
        List<Level> levels = levelRepository.findAll();
        queueNames.forEach(queueName -> {
            try {
                rabbitTemplate.convertAndSend(this.getMainExchange(),
                        queueName,
                        objectMapper.writeValueAsString(levels)
                );
            } catch (JsonProcessingException e) {
                LOGGER.info(e.getLocalizedMessage());
            }
        });

        LOGGER.info(HIERARCHY_LEVELS_SENT);
    }

    //@Scheduled(cron = "0 */15 * * * *")
    @Scheduled(cron = " 0 0 * * * ?")
    public void emitPrices(){
        List<String> queueNames = config.getAllQueueNamesByGroupName(PRICES_QUEUES);
        List<Product> products = productRepository.findAll();
        queueNames.forEach(queueName -> {
            try {
                rabbitTemplate.convertAndSend(this.getMainExchange(),
                        queueName,
                        objectMapper.writeValueAsString(products)
                );
            } catch (JsonProcessingException e) {
                LOGGER.info(e.getLocalizedMessage());
            }
        });
        LOGGER.info(PLU_PRICES_AND_STOCKS_SENT);
    }

    private String getMainExchange(){
        return config.getExchangeByPropertyName(MAIN_EXCHANGE);
    }

}
