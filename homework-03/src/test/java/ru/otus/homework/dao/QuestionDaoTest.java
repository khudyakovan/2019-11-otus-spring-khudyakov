package ru.otus.homework.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionDaoTest {

    private static QuestionDaoImpl questionDao;
    private static Resource questionFilePath;
    private static AnswerDaoImpl answerDao;
    private static Resource answerFilePath;
    private final int ALL_QUESTIONS_COUNT = 5;
    private final int TESTING_QUESTION_UID = 2;
    private final int EXPECTED_ANSWERS_COUNT = 3;
    private static final String LANGUAGE_POSTFIX = "en";

    @BeforeAll
    static void setup() {
        answerFilePath = new ClassPathResource("answers_" + LANGUAGE_POSTFIX + ".csv");
        answerDao = new AnswerDaoImpl(answerFilePath);
        questionFilePath = new ClassPathResource("questions_" + LANGUAGE_POSTFIX + ".csv");
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