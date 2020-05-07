package ru.otus.graduation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Order;

public interface OrderRepository  extends MongoRepository<Order, String>, OrderRepositoryCustom {
}
