package ru.otus.graduation.catalogue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Level;
import ru.otus.graduation.model.Product;
import ru.otus.graduation.model.Proposal;
import ru.otus.graduation.model.StatusMessage;
import ru.otus.graduation.repository.master.LevelRepository;
import ru.otus.graduation.repository.master.ProductRepository;
import ru.otus.graduation.repository.proposal.ProposalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitMqListener {

    private final LevelRepository levelRepository;
    private final ProductRepository productRepository;
    private final ProposalRepository proposalRepository;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListener.class);

    private static final String LEVELS_HANDLED = "MASTER DATA RECEIVED AND SAVED: Hierarchy Levels";
    private static final String PRICES_AND_STOCK_HANDLED = "MASTER DATA RECEIVED AND SAVED: Prices And Stocks";
    private static final String STATUS_CHANGED_TO = "PROPOSAL/ORDER (%s/%s) status changed to: %s";

    @RabbitListener(queues = "${application.rabbit.queues.levels.catalog}")
    public void processLevels(String message) throws JsonProcessingException {
        List<Level> levels = objectMapper.readValue(message, new TypeReference<List<Level>>(){});
        levelRepository.saveAll(levels);
        LOGGER.info(LEVELS_HANDLED);
    }

    @RabbitListener(queues = "${application.rabbit.queues.prices.catalog}")
    public void processPricesAndStocks(String message) throws JsonProcessingException {
        List<Product> products = objectMapper.readValue(message, new TypeReference<List<Product>>(){});
        productRepository.saveAll(products);
        LOGGER.info(PRICES_AND_STOCK_HANDLED);
    }

    @RabbitListener(queues = "${application.rabbit.queues.products.order}")
    public void processOrderStatus(String message) throws JsonProcessingException {
        StatusMessage statusMessage = objectMapper.readValue(message, new TypeReference<StatusMessage>(){});
        Proposal proposal = proposalRepository.findByProposalNumber(statusMessage.getProposalNumber());
        proposal.setStatus(statusMessage.getStatus());
        proposal.setCurrentDate(statusMessage.getCurrentDate());
        proposalRepository.save(proposal);
        LOGGER.info(String.format(STATUS_CHANGED_TO,
                statusMessage.getProposalNumber(),
                statusMessage.getOrderNumber(),
                statusMessage.getStatus()));
    }
}
