package ru.otus.graduation.catalogue.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.graduation.repository.master.ProductRepository;
import ru.otus.graduation.service.StatusEmitterService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataMongoTest
@ComponentScan({ "ru.otus.graduation.catalogue.config"})
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @MockBean
    StatusEmitterService statusEmitterService;

    private static final String FULL_LEVEL_ID = "FD0219000";
    private static final String PART_OF_LEVEL_ID = "FD0219";

    @Test
    void getGoodsByLevelId() {
        assertAll(
                () -> assertNotEquals(0, productRepository.findByParentIdStartingWith(PART_OF_LEVEL_ID)),
                () -> assertNotEquals(0, productRepository.findByParentIdStartingWith(FULL_LEVEL_ID))
        );
    }
}
