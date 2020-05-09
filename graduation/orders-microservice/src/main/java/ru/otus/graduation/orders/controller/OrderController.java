package ru.otus.graduation.orders.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Status;
import ru.otus.graduation.orders.service.OrderService;
import ru.otus.graduation.service.StatusEmitterService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final StatusEmitterService statusEmitterService;
    private static final String BASE_ORDER_URI = "/api/v1/orders";
    private static final String BASE_PROPOSALS_URI = "/api/v1/proposals";

    
    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping(value = BASE_ORDER_URI + "/active")
    public List<Order> findActiveOrders() {
        return orderService.findActiveOrders();
    }

    @RequestMapping(path = {BASE_ORDER_URI, BASE_PROPOSALS_URI},
            params = "mobile-number")
    public List<Order> findOrdersByPhone(@RequestParam(name = "mobile-number") String mobileNumber) {
        return orderService.findByMobilePhone(mobileNumber);
    }

    @RequestMapping(path = {BASE_ORDER_URI, BASE_PROPOSALS_URI},
            params = "order-number")
    public Order findAOrdersByNumber(@RequestParam(name = "order-number") long orderNumber) {
        return orderService.findByOrderNumber(orderNumber);
    }

    @RequestMapping(path = {BASE_ORDER_URI},
            params = "proposal-number")
    public Order findAOrdersByProposalNumber(@RequestParam(name = "proposal-number") long proposalNumber) {
        return orderService.findByProposalNumber(proposalNumber);
    }

    @PutMapping(path = {BASE_PROPOSALS_URI})
    public void orderCancellation(@RequestBody Order order) {
        if (order != null) {
            Order o = orderService.changeStatus(order.getId(), Status.CANCELLED);
            orderService.emitOrderStatus(o);
        }
    }

}
