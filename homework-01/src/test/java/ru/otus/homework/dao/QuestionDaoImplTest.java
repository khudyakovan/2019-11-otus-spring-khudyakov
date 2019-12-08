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
    final int ALL_QUESTIONS_COUNT = 5;
    final int TESTING_QUESTION_UID = 2;
    final int EXPECTED_ANSWERS_COUNT = 3;

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
                ()->assertEquals(ALL_QUESTIONS_COUNT, questionDao.getAllQuestions().size())
        );
    }

    @DisplayName("Выборка вопроса и связанных ответов")
    @Test
    void shouldGetQuestionByUid() {
        assertAll(
                ()->assertNotNull(questionDao.getQuestionByUid(2)),
                ()->assertEquals(EXPECTED_ANSWERS_COUNT, questionDao.getQuestionByUid(TESTING_QUESTION_UID).getAnswers().size())
        );
    }
}