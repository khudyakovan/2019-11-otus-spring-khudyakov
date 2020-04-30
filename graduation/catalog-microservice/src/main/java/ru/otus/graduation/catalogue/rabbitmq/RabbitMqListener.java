package ru.otus.graduation.catalogue.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.catalogue.model.Level;
import ru.otus.graduation.catalogue.model.Price;
import ru.otus.graduation.catalogue.repository.LevelRepository;
import ru.otus.graduation.catalogue.repository.PriceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitMqListener {

    private final LevelRepository levelRepository;
    private final PriceRepository priceRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListener.class);
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${application.rabbit.queues.levels.catalog}")
    public void processLevels(String message) throws JsonProcessingException {
        List<Level> levels = objectMapper.readValue(message, new TypeReference<List<Level>>(){});
        levelRepository.saveAll(levels);
        LOGGER.info("MASTER DATA RECEIVED AND SAVED: Hierarchy Levels");
    }

    @RabbitListener(queues = "${application.rabbit.queues.prices.catalog}")
    public void processPricesAndStocks(String message) throws JsonProcessingException {
        List<Price> prices = objectMapper.readValue(message, new TypeReference<List<Price>>(){});
        priceRepository.saveAll(prices);
        LOGGER.info("MASTER DATA RECEIVED AND SAVED: Prices And Stocks");
    }
}
