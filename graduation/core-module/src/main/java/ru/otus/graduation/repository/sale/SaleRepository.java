package ru.otus.graduation.repository.sale;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Sale;

public interface SaleRepository extends MongoRepository<Sale, String>, SaleRepositoryCustom {

}
