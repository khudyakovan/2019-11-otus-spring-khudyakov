package ru.otus.graduation.catalogue.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.graduation.repository.master.ProductRepository;
import ru.otus.graduation.service.HelperService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    private static final String FULL_LEVEL_ID = "FD0219000";
    private static final String TRANSFORMED_LEVEL_ID = "FD0219";
    @Autowired
    HelperService helperService;

    @Test
    void getGoodsByLevelId() {
        String beginning = helperService.getBeginningOfLevel(FULL_LEVEL_ID);
        assertAll(
                () -> assertEquals(TRANSFORMED_LEVEL_ID, helperService.getBeginningOfLevel(FULL_LEVEL_ID)),
                () -> assertNotEquals(0, productRepository.findByParentIdStartingWith(beginning)),
                () -> assertNotEquals(0, productRepository.findByParentIdStartingWith(FULL_LEVEL_ID))
        );
        System.out.println(beginning);
    }
}
