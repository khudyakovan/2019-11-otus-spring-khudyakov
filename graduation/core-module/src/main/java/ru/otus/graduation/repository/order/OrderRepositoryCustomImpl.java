package ru.otus.graduation.repository.order;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import ru.otus.graduation.model.Order;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    @Data
    private class MaxNumber {
        private long maxNumber;
    }

    private final MongoTemplate mongoTemplate;

    @Override
    public long findMaxOrderNumber() {
        Aggregation aggregation = newAggregation(
                group()
                        .max("orderNumber").as("maxNumber")
        );

        val maxValue = mongoTemplate.aggregate(aggregation, Order.class, MaxNumber.class).getUniqueMappedResult();
        return maxValue == null ? 0 : maxValue.maxNumber;
    }
}
