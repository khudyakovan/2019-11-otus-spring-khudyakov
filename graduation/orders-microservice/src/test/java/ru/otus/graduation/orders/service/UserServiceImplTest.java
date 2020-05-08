package ru.otus.graduation.orders.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void findByUsername() {
        assertAll(
                () -> assertNull(userService.findByUsername("")),
                () -> assertNotNull(userService.findByUsername("9174704000")),
                () -> assertNotEquals(0, userService.findByUsername("9174704000"))
        );
    }
}
