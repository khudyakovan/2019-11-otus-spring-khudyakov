package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class AnswerDaoTest {

    final int ALL_ANSWERS_COUNT = 18;
    final int TESTING_QUESTION_UID = 1;
    final int EXPECTED_ANSWERS_COUNT = 4;
    final int CORRECT_ANSWER = 3;

    @Autowired
    private AnswerDao answerDao;

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