package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


@DisplayName("Dao для работы со регистром книг и авторов")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
@Import(BookAuthorDaoJdbc.class)
class BookAuthorDaoJdbcTest {

    private final long BOOK_UID = 15;
    private final long AUTHOR_UID = 44;
    private final int EXPECTED_AUTHORS_COUNT=3;
    private final int EXPECTED_BOOKS_COUNT=2;

    @Autowired
    private BookAuthorDaoJdbc jdbc;

    @DisplayName("Возвращает книги за авторством писателя")
    @Test
    void shouldGetBooksByAuthorUid() {
        List<Book> books = jdbc.getBooksByAuthorUid(AUTHOR_UID);
        assertThat(!books.isEmpty());
        assertThat(books.size() == EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Возвращает авторов книги")
    @Test
    void shouldGetAuthorsByBookUid() {
        List<Author> authors = jdbc.getAuthorsByBookUid(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size() == EXPECTED_AUTHORS_COUNT);
    }
}