package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Answer")
class AnswerTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor(){
        Answer answer = new Answer(1, 1, 1, "Неправильный ответ", false);
        assertAll(
                ()->assertFalse(answer.isCorrect()),
                ()->assertEquals("Неправильный ответ", answer.getAnswer())
        );
    }

}