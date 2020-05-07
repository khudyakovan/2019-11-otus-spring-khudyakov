package ru.otus.graduation.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.*;
import ru.otus.graduation.repository.LevelRepository;
import ru.otus.graduation.repository.OrderRepository;
import ru.otus.graduation.repository.ProductRepository;
import ru.otus.graduation.service.StatusEmitterService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitMqListenerService {

    private final LevelRepository levelRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final StatusEmitterService statusEmitterService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListenerService.class);
    private static final String LEVELS_HANDLED = "MASTER DATA RECEIVED AND SAVED: Hierarchy Levels";
    private static final String PRICES_AND_STOCK_HANDLED = "MASTER DATA RECEIVED AND SAVED: Prices And Stocks";
    private static final String PROPOSAL_HANDLED = "Proposal #%s was handled. Order #%s was created";
    private static final String MAIN_EXCHANGE = "main";
    private static final String PRODUCT_QUEUES = "products";
    private static final String ORDER_QUEUE = "order";

    @RabbitListener(queues = "${application.rabbit.queues.levels.orders}")
    public void processLevels(String message) throws JsonProcessingException {
        List<Level> levels = objectMapper.readValue(message, new TypeReference<List<Level>>() {
        });
        levelRepository.saveAll(levels);
        LOGGER.info(LEVELS_HANDLED);
    }

    @RabbitListener(queues = "${application.rabbit.queues.prices.orders}")
    public void processPricesAndStocks(String message) throws JsonProcessingException {
        List<Product> products = objectMapper.readValue(message, new TypeReference<List<Product>>() {
        });
        productRepository.saveAll(products);
        LOGGER.info(PRICES_AND_STOCK_HANDLED);
    }

    @RabbitListener(queues = "${application.rabbit.queues.products.proposal}")
    public void processProposal(String message) throws JsonProcessingException {
        Proposal proposal = objectMapper.readValue(message, new TypeReference<Proposal>() {
        });
        Order order = new Order();
        order.setOrderNumber(orderRepository.findMaxOrderNumber() + 1);
        order.setProposalNumber(proposal.getProposalNumber());
        order.setMobilePhone(proposal.getMobilePhone());
        order.setProposalDetails(proposal.getProposalDetails());
        order.setStatus(Status.QUEUED);
        order = orderRepository.save(order);

        this.emitStatus(order);
        LOGGER.info(String.format(PROPOSAL_HANDLED, order.getProposalNumber(), order.getOrderNumber()));
    }

    private void emitStatus(Order order){
        StatusMessage message = new StatusMessage();
        message.setSender(Sender.ORDERS);
        message.setProposalNumber(order.getProposalNumber());
        message.setOrderNumber(order.getOrderNumber());
        message.setMobileNumber(order.getMobilePhone());
        message.setStatus(order.getStatus());
        message.setCurrentDate(new Date());
        statusEmitterService.emitStatusToSpecificQueue(
                MAIN_EXCHANGE,
                PRODUCT_QUEUES,
                ORDER_QUEUE,
                message);
    }
}
