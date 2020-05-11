package ru.otus.graduation.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.graduation.dto.MailMessageDto;
import ru.otus.graduation.dto.OrderItemDto;
import ru.otus.graduation.model.*;
import ru.otus.graduation.orders.dto.OrderDetailsDto;
import ru.otus.graduation.repository.order.OrderRepository;
import ru.otus.graduation.service.StatusEmitterService;
import ru.otus.graduation.service.UserService;
import ru.otus.graduation.service.master.ProductService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private final StatusEmitterService statusEmitterService;
    private static final String MAIN_EXCHANGE = "main";
    private static final String PRODUCT_QUEUES = "products";
    private static final String ORDER_QUEUE = "order";
    private static final String MAIL_QUEUE = "orders-to-mail";

    @Override
    public Order findById(String id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order save(OrderDetailsDto dto) {
        Order order = orderRepository.findByOrderNumber(dto.getOrderNumber());
        order.setStatus(dto.getStatus());
        Map<String, Integer> details = dto.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItemDto::getId, OrderItemDto::getQuantity));
        order.setDetails(details);
        order.setCurrentDate(new Date());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findByOrderNumber(long orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public Order findByProposalNumber(long proposalNumber) {
        return orderRepository.findByProposalNumber(proposalNumber);
    }

    @Override
    public List<Order> findByMobilePhone(String mobilePhone) {
        return orderRepository.findByMobilePhoneOrderByCurrentDateDesc(mobilePhone);
    }

    @Override
    public List<Order> findActiveOrders() {
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.PROPOSAL);
        statuses.add(Status.QUEUED);
        statuses.add(Status.CANCELLED);
        statuses.add(Status.READY);
        return orderRepository.findByStatusIsIn(statuses);
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
        statusEmitterService.emitStatusToSpecificQueue(
                MAIN_EXCHANGE,
                PRODUCT_QUEUES,
                ORDER_QUEUE,
                message);
    }

    @Override
    public long findMaxOrderNumber() {
        return orderRepository.findMaxOrderNumber();
    }

    @Override
    public OrderDetailsDto getOrderDetailsDto(long orderNumber) {
        OrderDetailsDto dto = new OrderDetailsDto();
        dto.setOrderNumber(orderNumber);

        Order order = orderRepository.findByOrderNumber(orderNumber);
        dto.setStatus(order.getStatus());
        List<OrderItemDto> orderItems = this.getOrderDetails(order);

        dto.setOrderItems(orderItems);
        return dto;
    }

    @Override
    public void emitOrderStatusMailMessage(Order order) throws JsonProcessingException {
        statusEmitterService.emitStatusToSpecificQueue(
                MAIN_EXCHANGE,
                PRODUCT_QUEUES,
                MAIL_QUEUE,
                this.getMailMessageDto(order));
    }

    private MailMessageDto getMailMessageDto(Order order) throws JsonProcessingException {
        MailMessageDto dto = new MailMessageDto();

        dto.setOrderNumber(order.getOrderNumber());
        dto.setProposalNumber(order.getProposalNumber());
        dto.setMobileNumber(order.getMobilePhone());
        dto.setEmail(order.getEmail());
        dto.setPickupTime(order.getTime());
        dto.setCurrentDate(new Date());
        dto.setDetails(this.getOrderDetails(order));
        dto.setStatus(order.getStatus());
        return dto;
    }

    private List<OrderItemDto> getOrderDetails(Order order) {
        List<String> ids = new ArrayList<>(order.getDetails().keySet());
        List<Product> products = productService.findByIdIsIn(ids);
        List<OrderItemDto> dtos = new ArrayList<>();

        order.getDetails().entrySet().forEach(entry -> {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(entry.getKey());
            Product product = products.stream()
                    .filter(p -> entry.getKey().equals(p.getId()))
                    .findFirst().get();
            itemDto.setPrice(product.getPrice());
            itemDto.setName(product.getName());
            itemDto.setQuantity(entry.getValue());
            itemDto.setSummary(product.getPrice() * entry.getValue());
            dtos.add(itemDto);
        });
        return dtos;
    }

}
