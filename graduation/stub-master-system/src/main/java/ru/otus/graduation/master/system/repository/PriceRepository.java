package ru.otus.graduation.master.system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.master.system.model.Price;

public interface PriceRepository extends MongoRepository<Price, String> {
}
