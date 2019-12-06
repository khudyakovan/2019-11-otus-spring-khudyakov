package ru.otus.homework.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.homework.service.AnswerServiceImpl;
import ru.otus.homework.service.QuestionServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class AnswerDaoImplTest {

    private static AnswerDaoImpl answerDao;
    private static Resource answerFilePath;

    @BeforeAll
    static void setup(){
        answerFilePath = new ClassPathResource("answers.csv");
        answerDao = new AnswerDaoImpl(answerFilePath);
    }

    @DisplayName("Класс создан")
    @Test
    void shouldHaveCorrectConstructor() {
        assertNotNull(answerDao);
    }

    @DisplayName("Метод возвращает все ответы без фильтрации")
    @Test
    void shouldGetAllAnswers() {
        assertAll(
                ()->assertTrue(answerDao.getAllAnswers().size() > 0),
                ()->assertEquals(18, answerDao.getAllAnswers().size())
        );
    }

    @DisplayName("Метод возвращает все варианты ответов на конкретный вопрос")
    @Test
    void shouldGetAllAnswersByQuestionUid() {
        assertAll(
                ()->assertTrue(answerDao.getAllAnswersByQuestionUid(1).size()>0),
                ()->assertEquals(4,answerDao.getAllAnswersByQuestionUid(1).size())
        );
    }

    @DisplayName("Метод проверяет корректность ответа на вопрос")
    @Test
    void shouldCheckIfUserAnswerCorrect() {
        assertTrue(answerDao.isUserAnswerCorrect(1,3));
    }
}