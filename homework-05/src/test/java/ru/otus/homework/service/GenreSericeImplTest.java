package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы со справочником книг")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class GenreSericeImplTest {

    private final long TEST_GENRE_UID = 2;
    private final int EXPECTED_BOOKS_COUNT = 5;
    private final String EXPECTED_GENRE_NAME = "Science fiction";

    @Autowired
    private GenreService genreService;

    @DisplayName("Выборка жанра по Uid и книг этого жанра")
    @Test
    void shouldGetGenreByUid() {
        Genre genre = genreService.getByUid(TEST_GENRE_UID);
        System.out.println(genre);
        assertAll(
                () -> assertNotNull(genre),
                () -> assertEquals(EXPECTED_BOOKS_COUNT, genre.getBooks().size()),
                () -> assertEquals(EXPECTED_GENRE_NAME, genre.getName())
        );
    }
}