package ru.otus.graduation.master.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.graduation.master.system.config.ApplicationConfig;
import ru.otus.graduation.master.system.model.Level;
import ru.otus.graduation.master.system.model.Price;
import ru.otus.graduation.master.system.repository.LevelRepository;
import ru.otus.graduation.master.system.repository.PriceRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MasterDataEmitter {

    private final LevelRepository levelRepository;
    private final PriceRepository priceRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfig config;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterDataEmitter.class);
    private static final String LEVELS_SENT_MESSAGE = "Hierarchy levels sent";
    private static final String PLU_SENT_MESSAGE = "PLU, prices and stocks sent";
    private static final String LEVELS_QUEUES = "levels";
    private static final String PRICES_QUEUES = "prices";

    @Scheduled(cron = "0 0 01 * * ?")
    public void emitLevels(){
        Map<String, String> queueNames = config.getQueues().get(LEVELS_QUEUES);
        List<Level> levels = levelRepository.findAll();
        queueNames.forEach((key, value) -> {
            try {
                rabbitTemplate.convertAndSend(config.getExchange(),
                        value,
                        objectMapper.writeValueAsString(levels)
                );
            } catch (JsonProcessingException e) {
                LOGGER.info(e.getLocalizedMessage());
            }
        });
        LOGGER.info(LEVELS_SENT_MESSAGE);
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void emitPrices(){
        Map<String, String> queueNames = config.getQueues().get(PRICES_QUEUES);
        List<Price> prices = priceRepository.findAll();
        queueNames.forEach((key, value) -> {
            try {
                rabbitTemplate.convertAndSend(config.getExchange(),
                        value,
                        objectMapper.writeValueAsString(prices)
                );
            } catch (JsonProcessingException e) {
                LOGGER.info(e.getLocalizedMessage());
            }
        });
        LOGGER.info(PLU_SENT_MESSAGE);
    }

}
