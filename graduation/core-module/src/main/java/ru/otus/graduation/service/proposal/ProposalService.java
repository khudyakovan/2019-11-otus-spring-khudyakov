package ru.otus.graduation.service.proposal;

import ru.otus.graduation.model.Proposal;

public interface ProposalService {
    Proposal findByMobilePhone(String mobilePhone);
    Proposal findByProposalNumber(long proposalNumber);
    long findMaxProposalNumber();
    Proposal save(Proposal proposal);
}
