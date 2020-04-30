package ru.otus.graduation.pos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.pos.model.Price;

public interface PriceRepository extends MongoRepository<Price, String> {
}
