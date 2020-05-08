package ru.otus.graduation.orders.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.orders.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    private static final String ACTIVE_ORDERS_URI = "/active";

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping(value = ACTIVE_ORDERS_URI)
    public List<Order> findActiveOrders() {
        return orderService.findActiveOrders();
    }

    @GetMapping(params = "mobile-number")
    public List<Order> findOrdersByPhone(@RequestParam(name = "mobile-number") String mobileNumber) {
        return orderService.findByMobilePhone(mobileNumber);
    }

    @GetMapping(params = "order-number")
    public Order findAOrdersByNumber(@RequestParam(name = "order-number") long orderNumber) {
        return orderService.findByOrderNumber(orderNumber);
    }

    @GetMapping(params = "proposal-number")
    public Order findAOrdersByProposalNumber(@RequestParam(name = "proposal-number") long proposalNumber) {
        return orderService.findByProposalNumber(proposalNumber);
    }
}
