package ru.otus.homework.service;

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
class QuestionServiceImplTest {

    @Autowired
    QuestionService questionService;

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