package ru.otus.graduation.catalogue.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.graduation.repository.proposal.ProposalRepository;
import ru.otus.graduation.service.StatusEmitterService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ComponentScan({ "ru.otus.graduation.catalogue.config"})
public class ProposalRepositoryTest {

    @Autowired
    ProposalRepository proposalRepository;
    @MockBean
    StatusEmitterService statusEmitterService;

    @Test
    void getMaxProposalNumber(){
        assertEquals(1000, proposalRepository.findMaxProposalNumber());
    }
}
