package ru.otus.homework.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoImplTest {

    private static QuestionDaoImpl questionDao;
    private static Resource questionFilePath;
    private static AnswerDaoImpl answerDao;
    private static Resource answerFilePath;

    @BeforeAll
    static void setup() {
        answerFilePath = new ClassPathResource("answers.csv");
        answerDao = new AnswerDaoImpl(answerFilePath);
        questionFilePath = new ClassPathResource("questions.csv");
        questionDao = new QuestionDaoImpl(questionFilePath, answerDao);
    }

    @DisplayName("Класс и связанные классы созданы")
    @Test
    void shouldHaveCorrectConstructor() {
        assertNotNull(questionDao);
        assertNotNull(answerDao);
    }

    @DisplayName("Вопросов больше нуля и их количество 5")
    @Test
    void shouldGetAllQuestions() {
        assertAll(
                ()->assertTrue(questionDao.getAllQuestions().size()>0),
                ()->assertEquals(5, questionDao.getAllQuestions().size())
        );
    }

    @DisplayName("Выборка второго вопроса и связанных ответов")
    @Test
    void shouldGetQuestionByUid() {
        assertAll(
                ()->assertNotNull(questionDao.getQuestionByUid(2)),
                ()->assertEquals(3, questionDao.getQuestionByUid(2).getAnswers().size())
        );
    }
}