package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework.dao.AnswerDaoImpl;
import ru.otus.homework.dao.QuestionDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class QuestionServiceImplTest {

    private static QuestionServiceImpl questionService;
    private static AnswerServiceImpl answerService;
    private static AnswerDaoImpl answerDao;
    private static Resource answerFilePath;
    private static QuestionDaoImpl questionDao;
    private static Resource questionFilePath;
    private static final String LANGUAGE_POSTFIX = "ru";

    @BeforeAll
    static void setUp() {
        answerFilePath = new ClassPathResource("answers_" + LANGUAGE_POSTFIX + ".csv");
        answerDao = new AnswerDaoImpl(answerFilePath);
        questionFilePath = new ClassPathResource("questions_" + LANGUAGE_POSTFIX + ".csv");
        questionDao = new QuestionDaoImpl(questionFilePath, answerDao);

        questionService = new QuestionServiceImpl(questionDao);
    }

    @DisplayName("Класс создан")
    @Test
    void shouldHaveCorrectConstructor() {
        assertNotNull(questionService);
    }

    @DisplayName("Вопросов больше нуля и их количество 5")
    @Test
    void shouldGetAllQuestions() {
        assertAll(
                ()->assertTrue(questionService.getAllQuestions().size()>0),
                ()->assertEquals(5, questionService.getAllQuestions().size())
        );
    }

    @DisplayName("Выборка первого вопроса и связанных ответов")
    @Test
    void shouldGetQuestionByUid() {
        assertAll(
                ()->assertNotNull(questionService.getQuestionByUid(1)),
                ()->assertTrue(questionService.getQuestionByUid(1).getAnswers().size()>0)
        );
    }
}