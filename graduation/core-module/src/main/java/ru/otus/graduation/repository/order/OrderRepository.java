package ru.otus.graduation.repository.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Order;
import ru.otus.graduation.model.Status;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String>, OrderRepositoryCustom {

    Order findByOrderNumber(long orderNumber);
    Order findByProposalNumber(long proposalNumber);
    List<Order> findByMobilePhoneOrderByCurrentDateDesc(String mobilePhone);
    List<Order> findByStatusIsIn(List<Status> statuses);
}
