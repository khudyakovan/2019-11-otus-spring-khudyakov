package ru.otus.graduation.orders.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Sender;
import ru.otus.graduation.model.Status;
import ru.otus.graduation.model.StatusMessage;
import ru.otus.graduation.repository.OrderRepository;
import ru.otus.graduation.service.StatusEmitterService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StatusEmitterService statusEmitterService;
    private static final String MAIN_EXCHANGE = "main";
    private static final String PRODUCT_QUEUES = "products";
    private static final String ORDER_QUEUE = "order";

    @Override
    public Order save(Order order) {
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
        return orderRepository.findByMobilePhone(mobilePhone);
    }

    @Override
    public List<Order> findActiveOrders() {
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.PROPOSAL);
        statuses.add(Status.QUEUED);
        statuses.add(Status.CANCELLED);
        return orderRepository.findByStatusIsIn(statuses);
    }

    @Override
    public Order changeStatus(ObjectId orderId, Status status) {
        Order order = orderRepository.findById(orderId.toString()).get();
        order.setStatus(status);
        order.setCurrentDate(new Date());
        return orderRepository.save(order);
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
}
