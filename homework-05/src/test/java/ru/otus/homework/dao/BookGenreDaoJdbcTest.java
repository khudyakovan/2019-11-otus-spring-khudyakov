package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы со регистром книг и жанров")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
@Import({BookDaoJdbc.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
class BookGenreDaoJdbcTest {

    private final long BOOK_UID = 19;
    private final long GENRE_UID = 1;
    private final long TEST_GENRE_1 = 2;
    private final long TEST_GENRE_2 = 3;
    private final int EXPECTED_GENRES_COUNT = 3;
    private final int EXPECTED_BOOKS_COUNT = 4;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;
    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @DisplayName("Добавляет связку книга-жанры в регистр")
    @Test
    void shouldAddBookGenresRelation() {
        List<Genre> genres = new ArrayList<>();
        genres.add(genreDaoJdbc.getByUid(TEST_GENRE_1));
        genres.add(genreDaoJdbc.getByUid(TEST_GENRE_2));
        genreDaoJdbc.insertGenresByBookUid(BOOK_UID, genres);

        genres = genreDaoJdbc.getGenresByBookUid(BOOK_UID);
        assertThat(!genres.isEmpty());
        assertThat(genres.size()).isEqualTo(EXPECTED_GENRES_COUNT + 2);
    }

    @DisplayName("Редактирует связку книга-жанры")
    @Test
    void shouldEditBookGenresRelation() {
        List<Genre> genres = genreDaoJdbc.getGenresByBookUid(BOOK_UID);
        genres.remove(0);
        genreDaoJdbc.editGenresByBookUid(BOOK_UID, genres);

        genres = genreDaoJdbc.getGenresByBookUid(BOOK_UID);
        assertThat(!genres.isEmpty());
        assertThat(genres.size()).isEqualTo(EXPECTED_GENRES_COUNT - 1);
    }

    @DisplayName("Удаляет связку книга-авторы из регистра")
    @Test
    void shouldDeleteBookGenresRelation() {
        List<Genre> genres = genreDaoJdbc.getGenresByBookUid(BOOK_UID);
        genres.remove(0);
        genreDaoJdbc.deleteGenresByBookUid(BOOK_UID, genres);

        genres = genreDaoJdbc.getGenresByBookUid(BOOK_UID);
        assertThat(!genres.isEmpty());
        assertThat(genres.size()).isEqualTo(EXPECTED_GENRES_COUNT - 2);
    }

    @DisplayName("Возвращает книги определенного жанра")
    @Test
    void shouldGetBooksByGenreUid() {
        List<Book> books = bookDaoJdbc.getBooksByGenreUid(GENRE_UID);
        assertThat(!books.isEmpty());
        assertThat(books.size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Возвращает жанры книги")
    @Test
    void shouldGetGenresByBookUid() {
        List<Genre> genres = genreDaoJdbc.getGenresByBookUid(BOOK_UID);
        assertThat(!genres.isEmpty());
        assertThat(genres.size()).isEqualTo(EXPECTED_GENRES_COUNT);
    }
}