package ru.otus.graduation.pos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Price;
import ru.otus.graduation.pos.repository.PriceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitMqListenerService {

    private final PriceRepository priceRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListenerService.class);
    private final ObjectMapper objectMapper;
    private static final String MD_STATUS_MESSAGE="MASTER DATA RECEIVED AND SAVED: Prices And Stocks";

    @RabbitListener(queues = "${application.rabbit.queues.prices.pos}")
    public void processPricesAndStocks(String message) throws JsonProcessingException {
        List<Price> prices = objectMapper.readValue(message, new TypeReference<List<Price>>(){});
        priceRepository.saveAll(prices);
        LOGGER.info(MD_STATUS_MESSAGE);
    }
}
