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
    private BookGenreServiceImpl bookGenreService;
    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;

    @Test
    void shouldInsertGenresByBookUid() {
        int size = bookGenreService.getGenresByBookUid(BOOK_UID).size();
        GenreDto genre0 = genreService.insert(new GenreDto(GENRE_NAME_0));
        GenreDto genre1 = genreService.insert(new GenreDto(GENRE_NAME_1));
        List<GenreDto> genres = new ArrayList<>();
        genres.add(genre0);
        genres.add(genre1);
        bookGenreService.insertGenresByBookUid(BOOK_UID, genres);
        assertEquals(size + 2, bookGenreService.getGenresByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldEditGenresByBookUid() {
        GenreDto genre0 = genreService.insert(new GenreDto(GENRE_NAME_0));
        GenreDto genre1 = genreService.insert(new GenreDto(GENRE_NAME_1));
        List<GenreDto> genres = new ArrayList<>();
        genres.add(genre0);
        genres.add(genre1);
        bookGenreService.editGenresByBookUid(BOOK_UID, genres);
        assertEquals(EXPECTED_GENRES_COUNT_AFTER_EDIT, bookGenreService.getGenresByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldDeleteGenresByBookUid() {
        List<GenreDto> genres = bookGenreService.getGenresByBookUid(BOOK_UID);
        int size = genres.size();
        genres.remove(0);
        bookGenreService.deleteGenresByBookUid(BOOK_UID, genres);
        assertEquals(EXPECTED_VALUE, bookGenreService.getGenresByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldGetBooksByGenreUid() {
        assertNotEquals(UNEXPECTED_VALUE, bookGenreService.getBooksByGenreUid(GENRE_UID));
    }

    @Test
    void shouldGetGenresByBookUid() {
        assertNotEquals(UNEXPECTED_VALUE, bookGenreService.getGenresByBookUid(BOOK_UID));
    }
}