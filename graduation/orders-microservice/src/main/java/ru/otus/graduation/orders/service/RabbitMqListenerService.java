package ru.otus.graduation.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.*;
import ru.otus.graduation.repository.master.LevelRepository;
import ru.otus.graduation.repository.master.ProductRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqListenerService {

    private final LevelRepository levelRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final ObjectMapper objectMapper;
    private static final String LEVELS_HANDLED = "MASTER DATA RECEIVED AND SAVED: Hierarchy Levels";
    private static final String PRICES_AND_STOCK_HANDLED = "MASTER DATA RECEIVED AND SAVED: Prices And Stocks";
    private static final String PROPOSAL_HANDLED = "Proposal #%s was handled. Order #%s was created";
    private static final String ORDER_NOT_FOUND = "Order #%s not found!";
    private static final String STATUS_CHANGED_TO = "PROPOSAL/ORDER (%s/%s) status changed to: %s";
//    private static final String MAIN_EXCHANGE = "main";
//    private static final String PRODUCT_QUEUES = "products";
//    private static final String ORDER_QUEUE = "order";

    @RabbitListener(queues = "${application.rabbit.queues.levels.orders}")
    public void processLevels(String message) throws JsonProcessingException {
        List<Level> levels = objectMapper.readValue(message, new TypeReference<List<Level>>() {
        });
        levelRepository.saveAll(levels);
        log.info(LEVELS_HANDLED);
    }

    @RabbitListener(queues = "${application.rabbit.queues.prices.orders}")
    public void processPricesAndStocks(String message) throws JsonProcessingException {
        List<Product> products = objectMapper.readValue(message, new TypeReference<List<Product>>() {
        });
        productRepository.saveAll(products);
        log.info(PRICES_AND_STOCK_HANDLED);
    }

    @RabbitListener(queues = "${application.rabbit.queues.products.proposal}")
    public void processProposal(String message) throws JsonProcessingException {
        Proposal proposal = objectMapper.readValue(message, new TypeReference<Proposal>() {
        });
        Order order = this.createNewOrder(proposal);
        orderService.emitOrderStatus(order);
        log.info(String.format(PROPOSAL_HANDLED, order.getProposalNumber(), order.getOrderNumber()));
    }

    @RabbitListener(queues = "${application.rabbit.queues.products.pos-get-order}")
    public Order findOrderByNumber(long message){
        Order order = orderService.findByOrderNumber(message);
        if (order == null) {
            log.warn(String.format(ORDER_NOT_FOUND, message));
        }
        return order;
    }

    private Order createNewOrder(Proposal proposal){
        Order order = new Order();
        order.setOrderNumber(orderService.findMaxOrderNumber() + 1);
        order.setProposalNumber(proposal.getProposalNumber());
        order.setMobilePhone(proposal.getMobilePhone());
        order.setCurrentDate(new Date());
        order.setTime(proposal.getTime());
        order.setDetails(proposal.getDetails());
        order.setStatus(Status.QUEUED);
        return orderService.save(order);
    }

    @RabbitListener(queues = {"${application.rabbit.queues.sales.sales-to-orders}"})
    public void processOrderStatus(String message) throws JsonProcessingException {
        StatusMessage statusMessage = objectMapper.readValue(message, new TypeReference<StatusMessage>() {
        });
        Order order = orderService.findByOrderNumber(statusMessage.getOrderNumber());
        order.setStatus(statusMessage.getStatus());
        order.setCurrentDate(new Date());
        orderService.save(order);
        log.info(String.format(STATUS_CHANGED_TO,
                statusMessage.getProposalNumber(),
                statusMessage.getOrderNumber(),
                statusMessage.getStatus()));
    }

}
