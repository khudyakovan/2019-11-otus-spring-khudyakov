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
class QuestionDaoTest {

    private final int ALL_QUESTIONS_COUNT = 5;
    private final int TESTING_QUESTION_UID = 2;
    private final int EXPECTED_ANSWERS_COUNT = 3;

    @Autowired
    QuestionDao questionDao;

    @DisplayName("Класс создан")
    @Test
    void shouldHaveCorrectConstructor() {
        assertNotNull(questionDao);
    }

    @DisplayName("Вопросов больше нуля и их количество 5")
    @Test
    void shouldGetAllQuestions() {
        assertAll(
                () -> assertTrue(questionDao.getAllQuestions().size() > 0),
                () -> assertEquals(ALL_QUESTIONS_COUNT, questionDao.getAllQuestions().size())
        );
    }

    @DisplayName("Выборка вопроса и связанных ответов")
    @Test
    void shouldGetQuestionByUid() {
        assertAll(
                () -> assertNotNull(questionDao.getQuestionByUid(2)),
                () -> assertEquals(EXPECTED_ANSWERS_COUNT, questionDao.getQuestionByUid(TESTING_QUESTION_UID).getAnswers().size())
        );
    }
}