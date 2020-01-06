package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы со справочником книг")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 20;
    private static final int EXPECTED_BOOKS_COUNT_AFTER_INSERT = 21;
    private static final long TEST_BOOK_UID = 10;
    private static final long TEST_BOOK_ISBN = 1234567891013L;
    private static final int TEST_BOOK_PUBLISHING_YEAR = 21;
    private static final String TEST_BOOK_TITLE = "Test book title";
    private static final String EXPECTED_BOOK_TITLE = "The Catcher in the Rye";

    @Autowired
    private BookDaoJdbc jdbc;

    @DisplayName("Записывает 1 книгу в БД и проверяет, что количество изменилось")
    @Test
    void shouldInsertNewRecord() {
        Book book = new Book(
                TEST_BOOK_TITLE,
                TEST_BOOK_ISBN,
                TEST_BOOK_PUBLISHING_YEAR);
        jdbc.insert(book);
        assertThat(jdbc.count()).isEqualTo(EXPECTED_BOOKS_COUNT_AFTER_INSERT);
    }

    @DisplayName("Изменение и проверка изменения в справочнике книг")
    @Test
    void shouldEditRecord() {
        Book book = jdbc.getByUid(TEST_BOOK_UID);
        book.setIsbn(TEST_BOOK_ISBN);
        jdbc.edit(book);
        book = jdbc.getByUid(TEST_BOOK_UID);
        assertThat(book.getIsbn() == TEST_BOOK_ISBN);
    }

    @DisplayName("Удаляет 1 запись по uid")
    @Test
    void shouldDeleteRecordByUid() {
        jdbc.deleteByUid(TEST_BOOK_UID);
        assertThat(jdbc.count()).isEqualTo(EXPECTED_BOOKS_COUNT-1);
    }

    @DisplayName("Возвращает 1 запись по uid")
    @Test
    void shouldGetBookByUid() {
        Book book = jdbc.getByUid(TEST_BOOK_UID);
        assertThat(book.getTitle().equals(EXPECTED_BOOK_TITLE));
        assertThat(book.getAuthors()).hasSizeGreaterThan(0);
        assertThat(book.getGenres()).hasSizeGreaterThan(0);
    }

    @DisplayName("Возвращает все записи справочника")
    @Test
    void shouldGetAllBook() {
        List<Author> authors = jdbc.getAll().get(0).getAuthors();
        List<Genre> genres = jdbc.getAll().get(0).getGenres();
        assertThat(!jdbc.getAll().isEmpty());
        assertThat(authors).isNotNull().hasSizeGreaterThan(0);
        assertThat(genres).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("Возвращает количество записей справочника")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(jdbc.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }
}