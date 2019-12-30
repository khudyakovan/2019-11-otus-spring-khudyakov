package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Dao для работы со регистром книг и авторов")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
@Import({BookAuthorDaoJdbc.class, AuthorDaoJdbc.class})
class BookAuthorDaoJdbcTest {

    private final long BOOK_UID = 15;
    private final long AUTHOR_UID = 44;
    private final int EXPECTED_AUTHORS_COUNT = 3;
    private final int EXPECTED_BOOKS_COUNT = 2;
    final long TEST_AUTHOR_1 = 31;
    final long TEST_AUTHOR_2 = 32;

    @Autowired
    private BookAuthorDaoJdbc bookAuthorDaoJdbc;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @DisplayName("Добавляет связку книга-авторы в регистр")
    @Test
    void shouldAddBookAuthorsRelation() {
        List<Author> authors = new ArrayList<>();

        authors.add(authorDaoJdbc.getByUid(TEST_AUTHOR_1));
        authors.add(authorDaoJdbc.getByUid(TEST_AUTHOR_2));

        bookAuthorDaoJdbc.insertAuthorsByBookUid(BOOK_UID, authors);

        authors = bookAuthorDaoJdbc.getAuthorsByBookUid(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT + 2);
    }

    @DisplayName("Удаляет связку книга-авторы из регистра")
    @Test
    void shouldDeleteBookAuthorsRelation() {
        List<Author> authors = bookAuthorDaoJdbc.getAuthorsByBookUid(BOOK_UID);
        authors.remove(0);
        bookAuthorDaoJdbc.deleteAuthorsByBookUid(BOOK_UID, authors);

        authors = bookAuthorDaoJdbc.getAuthorsByBookUid(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT - 2);
    }

    @DisplayName("Редактирует связь книги с авторами")
    @Test
    void shouldEditBookAuthorsRelation() {
        List<Author> authors = bookAuthorDaoJdbc.getAuthorsByBookUid(BOOK_UID);
        authors.remove(0);
        bookAuthorDaoJdbc.editAuthorsByBookUid(BOOK_UID, authors);

        authors = bookAuthorDaoJdbc.getAuthorsByBookUid(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT - 1);
    }

    @DisplayName("Возвращает книги за авторством писателя")
    @Test
    void shouldGetBooksByAuthorUid() {
        List<Book> books = bookAuthorDaoJdbc.getBooksByAuthorUid(AUTHOR_UID);
        assertThat(!books.isEmpty());
        assertThat(books.size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Возвращает авторов книги")
    @Test
    void shouldGetAuthorsByBookUid() {
        List<Author> authors = bookAuthorDaoJdbc.getAuthorsByBookUid(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }
}