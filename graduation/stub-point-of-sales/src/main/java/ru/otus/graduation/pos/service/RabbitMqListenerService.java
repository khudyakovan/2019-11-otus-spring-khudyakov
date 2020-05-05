package ru.otus.graduation.pos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Product;
import ru.otus.graduation.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitMqListenerService {

    private final ProductRepository productRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListenerService.class);
    private final ObjectMapper objectMapper;
    private static final String MD_STATUS_MESSAGE="MASTER DATA RECEIVED AND SAVED: Prices And Stocks";

    @RabbitListener(queues = "${application.rabbit.queues.prices.pos}")
    public void processPricesAndStocks(String message) throws JsonProcessingException {
        List<Product> products = objectMapper.readValue(message, new TypeReference<List<Product>>(){});
        productRepository.saveAll(products);
        LOGGER.info(MD_STATUS_MESSAGE);
    }
}
