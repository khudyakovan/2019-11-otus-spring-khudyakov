package ru.otus.graduation.repository.proposal;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Proposal;

public interface ProposalRepository extends MongoRepository<Proposal, String>, ProposalRepositoryCustom {
    Proposal findByMobilePhone(String mobilePhone);
    Proposal findByProposalNumber(long proposalNumber);
}
