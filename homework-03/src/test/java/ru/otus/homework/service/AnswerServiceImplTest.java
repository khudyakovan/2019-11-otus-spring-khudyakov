package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.homework.dao.AnswerDaoImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnswerServiceImplTest {
    private static AnswerServiceImpl answerService;
    private static AnswerDaoImpl answerDao;
    private static Resource answerFilePath;
    private static final String LANGUAGE_POSTFIX = "ru";

    @BeforeAll
    static void setUp() {
        answerFilePath = new ClassPathResource("answers_" + LANGUAGE_POSTFIX + ".csv");
        answerDao = new AnswerDaoImpl(answerFilePath);
        answerService = new AnswerServiceImpl(answerDao);
    }

    @DisplayName("Класс создан")
    @Test
    void shouldHaveCorrectConstructor() {
        assertNotNull(answerService);
    }

    @DisplayName("Метод проверяет корректность ответа на вопрос")
    @Test
    void shouldCheckIfUserAnswerCorrect() {
        assertTrue(answerService.isUserAnswerCorrect(1,3));
    }
}