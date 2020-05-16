package ru.otus.graduation.service.proposal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Proposal;
import ru.otus.graduation.repository.proposal.ProposalRepository;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService{
    private final ProposalRepository proposalRepository;

    @Override
    public Proposal findByMobilePhone(String mobilePhone) {
        return proposalRepository.findByMobilePhone(mobilePhone);
    }

    @Override
    public Proposal findByProposalNumber(long proposalNumber) {
        return proposalRepository.findByProposalNumber(proposalNumber);
    }

    @Override
    public long findMaxProposalNumber() {
        return proposalRepository.findMaxProposalNumber();
    }

    @Override
    public Proposal save(Proposal proposal) {
        return proposalRepository.save(proposal);
    }
}
