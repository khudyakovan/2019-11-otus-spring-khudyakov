package ru.otus.graduation.catalogue.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.graduation.repository.proposal.ProposalRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProposalRepositoryTest {

    @Autowired
    ProposalRepository proposalRepository;

    @Test
    void getMaxProposalNumber(){
        assertEquals(1, proposalRepository.findMaxProposalNumber());
    }
}
