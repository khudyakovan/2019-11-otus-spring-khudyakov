package ru.otus.graduation.repository.sale;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Sale;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@RequiredArgsConstructor
public class SaleRepositoryCustomImpl implements SaleRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Data
    private class MaxNumber {
        private long maxNumber;
    }

    @Override
    public long findMaxSaleNumber() {
        Aggregation aggregation = newAggregation(
                group()
                        .max("saleNumber").as("maxNumber")
        );

        val maxValue = mongoTemplate.aggregate(aggregation, Sale.class, MaxNumber.class).getUniqueMappedResult();
        return maxValue == null ? 0 : maxValue.maxNumber;
    }
}
