package ru.otus.graduation.catalogue.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.catalogue.model.Price;


public interface PriceRepository extends MongoRepository<Price, String> {
}
