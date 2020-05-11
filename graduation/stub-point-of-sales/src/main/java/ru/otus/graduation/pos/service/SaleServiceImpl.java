package ru.otus.graduation.pos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.dto.SaleDto;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Sale;
import ru.otus.graduation.model.Sender;
import ru.otus.graduation.model.StatusMessage;
import ru.otus.graduation.repository.sale.SaleRepository;
import ru.otus.graduation.service.StatusEmitterService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final ApplicationConfig config;
    private final SaleRepository saleRepository;
    private final StatusEmitterService statusEmitterService;
    private static final String MAIN_EXCHANGE = "main";
    private static final String SALES_QUEUES = "sales";

    @Override
    public Sale sale(long orderNumber, List<SaleDto> details) {
        Sale sale = new Sale();
        sale.setOrderNumber(orderNumber);
        sale.setSaleNumber(saleRepository.findMaxSaleNumber() + 1);
        sale.setCurrentDate(new Date());
        sale.setDetails(details);
        return saleRepository.save(sale);
    }

    @Override
    public void emitOrderStatus(Order order) {
        StatusMessage message = new StatusMessage();
        message.setSender(Sender.ORDERS);
        message.setProposalNumber(order.getProposalNumber());
        message.setOrderNumber(order.getOrderNumber());
        message.setMobileNumber(order.getMobilePhone());
        message.setStatus(order.getStatus());
        message.setCurrentDate(new Date());
        config.getQueues().get(SALES_QUEUES).entrySet().forEach(entry -> {
            statusEmitterService.emitStatusToSpecificQueue(
                    MAIN_EXCHANGE,
                    SALES_QUEUES,
                    entry.getKey(),
                    message);
        });
    }
}
