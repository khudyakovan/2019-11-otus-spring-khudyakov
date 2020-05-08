package ru.otus.graduation.orders.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Status;
import ru.otus.graduation.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

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
        statuses.add(Status.DELETED);
        return orderRepository.findByStatusIsIn(statuses);
    }

    @Override
    public Order changeStatus(ObjectId orderId, Status status) {
        Order order = orderRepository.findById(orderId.toString()).get();
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
