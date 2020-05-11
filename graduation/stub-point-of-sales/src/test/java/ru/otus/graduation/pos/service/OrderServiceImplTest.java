package ru.otus.graduation.pos.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.graduation.model.Order;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    private static final long ORDER_NUMBER = 1;

    @Test
    void findByOrderNumber() {
        Order order = orderService.findByOrderNumber(ORDER_NUMBER);
        assertAll(
                ()->assertNotNull(order),
                ()->assertNotNull(order.getDetails()),
                ()->assertNotEquals(0, order.getDetails().size())
        );
        System.out.println(order);
        System.out.println(order.getDetails());
    }
}
