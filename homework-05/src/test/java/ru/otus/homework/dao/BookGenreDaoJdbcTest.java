package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы со регистром книг и авторов")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
@Import(BookGenreDaoJdbc.class)
class BookGenreDaoJdbcTest {

    private final long BOOK_UID = 19;
    private final long GENRE_UID = 1;
    private final int EXPECTED_GENRES_COUNT=3;
    private final int EXPECTED_BOOKS_COUNT=4;

    @Autowired
    private BookGenreDaoJdbc jdbc;

    @DisplayName("Возвращает книги определенного жанра")
    @Test
    void shouldGetBooksByGenreUid() {
        List<Book> books = jdbc.getBooksByGenreUid(GENRE_UID);
        assertThat(!books.isEmpty());
        assertThat(books.size() == EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Возвращает жанры книги")
    @Test
    void shouldGetGenresByBookUid() {
        List<Genre> genres = jdbc.getGenresByBookUid(BOOK_UID);
        assertThat(!genres.isEmpty());
        assertThat(genres.size() == EXPECTED_GENRES_COUNT);
    }
}