package ru.otus.graduation.master.system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Price;

public interface PriceRepository extends MongoRepository<Price, String> {
}
