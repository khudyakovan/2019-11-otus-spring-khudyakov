package ru.otus.graduation.repository.proposal;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import ru.otus.graduation.model.Proposal;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
@RequiredArgsConstructor
public class ProposalRepositoryCustomImpl implements ProposalRepositoryCustom{

    @Data
    private class MaxNumber {
        private long maxNumber;
    }

    private final MongoTemplate mongoTemplate;

    @Override
    public long findMaxProposalNumber() {
        Aggregation aggregation = newAggregation(
                group()
                .max("proposalNumber").as("maxNumber")
        );

        val maxValue = mongoTemplate.aggregate(aggregation, Proposal.class, MaxNumber.class).getUniqueMappedResult();
        return maxValue == null ? 0 : maxValue.maxNumber;
    }
}
