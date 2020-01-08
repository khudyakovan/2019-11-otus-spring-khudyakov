package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Author;
import ru.otus.homework.entity.Book;
import ru.otus.homework.entity.Genre;
import ru.otus.homework.repository.BookRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с книгами...")
@DataJpaTest
@Import({BookServiceImpl.class, BookRepositoryJpa.class, ApplicationProperties.class})
class BookServiceImplTest {

    @Autowired
    BookServiceImpl bookService;

    private final int EXPECTED_BOOKS_COUNT = 20;
    private final long TEST_BOOK_UID = 10;
    private final long TEST_BOOK_ISBN = 1234567891013L;
    private final int TEST_BOOK_PUBLISHING_YEAR = 21;
    private final String TEST_BOOK_TITLE = "Test book title";
    private final String EXPECTED_BOOK_TITLE = "The Catcher in the Rye";
    private final long AUTHOR_UID = 44;
    private final long GENRE_UID = 1;

    @DisplayName("...должен вернуть запись по ее Uid")
    @Test
    void shouldGetBookByUid() {
        Book book = bookService.findByUid(TEST_BOOK_UID);
        assertThat(book.getTitle().equals(EXPECTED_BOOK_TITLE));
        assertThat(book.getAuthors()).hasSizeGreaterThan(0);
        assertThat(book.getGenres()).hasSizeGreaterThan(0);
    }

    @DisplayName("...должен вернуть все записи")
    @Test
    void shouldFindAll() {
        List<Author> authors = bookService.findAll().get(0).getAuthors();
        List<Genre> genres = bookService.findAll().get(0).getGenres();
        assertThat(bookService.findAll()).isNotNull().hasSizeGreaterThan(0);
        assertThat(authors).isNotNull().hasSizeGreaterThan(0);
        assertThat(genres).isNotNull().hasSizeGreaterThan(0);
    }
}