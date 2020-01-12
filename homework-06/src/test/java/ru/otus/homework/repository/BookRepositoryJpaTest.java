package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Author;
import ru.otus.homework.entity.Book;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.entity.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Jpa для работы с авторами...")
@DataJpaTest
@Import({BookRepositoryJpa.class, ApplicationProperties.class})
class BookRepositoryJpaTest {

    private final int EXPECTED_BOOKS_COUNT = 20;
    private final long TEST_BOOK_UID = 10;
    private final long TEST_BOOK_ISBN = 1234567891013L;
    private final int TEST_BOOK_PUBLISHING_YEAR = 21;
    private final String TEST_BOOK_TITLE = "Test book title";
    private final String EXPECTED_BOOK_TITLE = "The Catcher in the Rye";
    private final long AUTHOR_UID = 44;
    private final long GENRE_UID = 1;

    @Autowired
    TestEntityManager em;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    BookRepositoryJpa bookRepository;

    @DisplayName("... должен записать 1 книгу в БД и проверить, что количество изменилось")
    @Test
    void shouldSaveBook() {
        long initialSize = bookRepository.count();
        Book book = new Book(
                TEST_BOOK_TITLE,
                TEST_BOOK_ISBN,
                TEST_BOOK_PUBLISHING_YEAR);
        bookRepository.save(book);
        assertThat(bookRepository.findAll().size()).isEqualTo(initialSize + 1);
    }

    @DisplayName("... должен измененить и проверить изменение в справочнике книг")
    @Test
    void shouldEditRecord() {
        Book book = bookRepository.findByUid(TEST_BOOK_UID).orElseThrow(null);
        book.setIsbn(TEST_BOOK_ISBN);
        book = bookRepository.save(book);
        assertThat(book.getIsbn()).isEqualTo(TEST_BOOK_ISBN);
    }

    @DisplayName("... должен удалить 1 запись по uid")
    @Test
    void shouldDeleteRecordByUid() {
        long initialSize = bookRepository.count();
        bookRepository.deleteByUid(TEST_BOOK_UID);
        assertThat(bookRepository.findAll().size()).isEqualTo(initialSize - 1);
    }

    @DisplayName("...должен вернуть запись по ее Uid")
    @Test
    void shouldGetBookByUid() {
        Book book = bookRepository.findByUid(TEST_BOOK_UID).orElse(null);
        assertThat(book.getTitle()).isEqualTo(EXPECTED_BOOK_TITLE);
        assertThat(book.getAuthors()).hasSizeGreaterThan(0);
        assertThat(book.getGenres()).hasSizeGreaterThan(0);
        assertThat(book.getComments()).hasSizeGreaterThan(0);
        System.out.println(book);
        System.out.println(book.getAuthors());
        System.out.println(book.getGenres());
        System.out.println(book.getComments());
    }

    @DisplayName("...должен вернуть все записи")
    @Test
    void shouldFindAll() {
        List<Book> books = bookRepository.findAll();
        List<Author> authors = books.get(0).getAuthors();
        List<Genre> genres = books.get(0).getGenres();
        List<Comment> comments = books.get(0).getComments();
        assertThat(books).isNotNull().hasSizeGreaterThan(0);
        assertThat(authors).isNotNull().hasSizeGreaterThan(0);
        assertThat(genres).isNotNull().hasSizeGreaterThan(0);
        assertThat(comments).isNotNull().hasSizeGreaterThan(0);
    }


    @DisplayName("... должен вернуть книги за авторством писателя")
    @Test
    void shouldGetBooksByAuthorUid() {
        List<Book> books = bookRepository.findBooksByAuthorUid(AUTHOR_UID);
        assertThat(books).isNotEmpty().isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("... должен вернуть книги определенного жанра")
    @Test
    void shouldGetBooksByGenreUid() {
        List<Book> books = bookRepository.findBooksByGenreUid(GENRE_UID);
        assertThat(!books.isEmpty());
        assertThat(books.size()).isGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(bookRepository.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }
}