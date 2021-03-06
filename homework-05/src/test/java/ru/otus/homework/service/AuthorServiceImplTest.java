package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.dto.AuthorDto;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы со справочником авторов")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthorServiceImplTest {

    private final long TEST_AUTHOR_UID = 39;
    private final String EXPECTED_AUTHOR_NAME = "Jerome Salinger";
    private final int EXPECTED_BOOKS_COUNT = 1;

    @Autowired
    private AuthorServiceImpl authorService;

    @DisplayName("Выборка автора по Uid и его книг")
    @Test
    void shouldGetAuthorByUid() {

        AuthorDto author = authorService.getByUid(TEST_AUTHOR_UID);
        assertAll(
                () -> assertNotNull(author),
                () -> assertEquals(EXPECTED_BOOKS_COUNT, author.getBooks().size()),
                () -> assertEquals(EXPECTED_AUTHOR_NAME, author.getFullName())
        );
    }
}