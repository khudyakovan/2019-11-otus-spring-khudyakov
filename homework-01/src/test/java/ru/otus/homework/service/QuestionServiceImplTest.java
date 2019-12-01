package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.homework.dao.AnswerDaoImpl;
import ru.otus.homework.dao.QuestionDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceImplTest {

    private static QuestionServiceImpl questionService;
    private static AnswerServiceImpl answerService;
    private static AnswerDaoImpl answerDao;
    private static Resource answerFilePath;
    private static QuestionDaoImpl questionDao;
    private static Resource questionFilePath;

    @BeforeAll
    static void setUp() {
        answerFilePath = new ClassPathResource("answers.csv");
        answerDao = new AnswerDaoImpl(answerFilePath);
        questionFilePath = new ClassPathResource("questions.csv");
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