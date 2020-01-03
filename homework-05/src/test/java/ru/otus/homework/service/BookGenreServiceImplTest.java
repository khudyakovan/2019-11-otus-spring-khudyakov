package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Сервис для работы с регистром книг и их жанров")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class BookGenreServiceImplTest {

    private final long BOOK_UID = 15;
    private final long GENRE_UID = 20;
    private final String GENRE_NAME_0 = "Modern Genre";
    private final String GENRE_NAME_1 = "Another Modern Genre";
    private final int EXPECTED_GENRES_COUNT_AFTER_EDIT = 2;
    private final int UNEXPECTED_VALUE = 0;
    private final int EXPECTED_VALUE = 1;

    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;

    @Test
    void shouldInsertGenresByBookUid() {
        int size = genreService.getGenresByBookUid(BOOK_UID).size();
        GenreDto genre0 = genreService.insert(new GenreDto(GENRE_NAME_0));
        GenreDto genre1 = genreService.insert(new GenreDto(GENRE_NAME_1));
        List<GenreDto> genres = new ArrayList<>();
        genres.add(genre0);
        genres.add(genre1);
        genreService.insertGenresByBookUid(BOOK_UID, genres);
        assertEquals(size + 2, genreService.getGenresByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldEditGenresByBookUid() {
        GenreDto genre0 = genreService.insert(new GenreDto(GENRE_NAME_0));
        GenreDto genre1 = genreService.insert(new GenreDto(GENRE_NAME_1));
        List<GenreDto> genres = new ArrayList<>();
        genres.add(genre0);
        genres.add(genre1);
        genreService.editGenresByBookUid(BOOK_UID, genres);
        assertEquals(EXPECTED_GENRES_COUNT_AFTER_EDIT, genreService.getGenresByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldDeleteGenresByBookUid() {
        List<GenreDto> genres = genreService.getGenresByBookUid(BOOK_UID);
        int size = genres.size();
        genres.remove(0);
        genreService.deleteGenresByBookUid(BOOK_UID, genres);
        assertEquals(EXPECTED_VALUE, genreService.getGenresByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldGetBooksByGenreUid() {
        assertNotEquals(UNEXPECTED_VALUE, bookService.getBooksByGenreUid(GENRE_UID));
    }

    @Test
    void shouldGetGenresByBookUid() {
        assertNotEquals(UNEXPECTED_VALUE, genreService.getGenresByBookUid(BOOK_UID));
    }
}