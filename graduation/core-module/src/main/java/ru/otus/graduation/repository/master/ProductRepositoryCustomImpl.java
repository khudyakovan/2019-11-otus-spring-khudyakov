package ru.otus.graduation.repository.master;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import ru.otus.graduation.model.Product;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Product> getRandomProducts() {
        Aggregation aggregation = newAggregation(
                group("id", "name", "parent", "stock", "price")
                        .sum("stock").as("max_stock"),
                sort(Sort.Direction.DESC, "max_stock"),
                limit(100),
                sample(20),
                //project().andExclude("_id")
                project().andExclude("_id")
                        .and("$_id.id").as("_id")
                        .and("$_id.name").as("name")
                        .and("$_id.parent").as("parent")
                        .and("$_id.stock").as("stock")
                        .and("$_id.price").as("price")
        );

        return mongoTemplate.aggregate(aggregation, Product.class, Product.class)
                .getMappedResults();
    }
}
