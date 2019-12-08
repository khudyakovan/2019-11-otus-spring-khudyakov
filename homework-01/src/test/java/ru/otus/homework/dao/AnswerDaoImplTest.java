package ru.otus.homework.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.*;

class AnswerDaoImplTest {

    private static AnswerDaoImpl answerDao;
    private static Resource answerFilePath;
    final int ALL_ANSWERS_COUNT = 18;
    final int TESTING_QUESTION_UID = 1;
    final int EXPECTED_ANSWERS_COUNT = 4;
    final int CORRECT_ANSWER = 3;

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
                ()->assertEquals(ALL_ANSWERS_COUNT, answerDao.getAllAnswers().size())
        );
    }

    @DisplayName("Метод возвращает все варианты ответов на конкретный вопрос")
    @Test
    void shouldGetAllAnswersByQuestionUid() {
        assertAll(
                ()->assertTrue(answerDao.getAllAnswersByQuestionUid(TESTING_QUESTION_UID).size()>0),
                ()->assertEquals(EXPECTED_ANSWERS_COUNT,answerDao.getAllAnswersByQuestionUid(TESTING_QUESTION_UID).size())
        );
    }

    @DisplayName("Метод проверяет корректность ответа на вопрос")
    @Test
    void shouldCheckIfUserAnswerCorrect() {
        assertTrue(answerDao.isUserAnswerCorrect(TESTING_QUESTION_UID,CORRECT_ANSWER));
    }
}