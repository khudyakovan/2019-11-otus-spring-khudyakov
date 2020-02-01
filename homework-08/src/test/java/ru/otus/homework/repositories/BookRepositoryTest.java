package ru.otus.homework.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework.AbstractRepositoryTest;
import ru.otus.homework.models.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами...")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest extends AbstractRepositoryTest {

    private static final String TEST_BOOK_UID = "10";
    private static final long TEST_BOOK_ISBN = 1234567891013L;
    private static final int TEST_BOOK_PUBLISHING_YEAR = 21;
    private static final String TEST_BOOK_TITLE = "Test book title";
    private static final String EXPECTED_BOOK_TITLE = "The Catcher in the Rye";
    private static final String AUTHOR_UID = "44";
    private static final String GENRE_UID = "1";

    @Autowired
    BookRepository bookRepository;

    @DisplayName("... должен записать 1 книгу в БД и проверить, что количество изменилось")
    @Test
    @Order(1)
    void shouldSaveBook() {
        var initialSize = bookRepository.count();
        Book book = new Book(
                TEST_BOOK_TITLE,
                TEST_BOOK_ISBN,
                TEST_BOOK_PUBLISHING_YEAR);
        bookRepository.save(book);
        assertThat(bookRepository.findAll().size()).isEqualTo(initialSize + 1);
    }

    @DisplayName("... должен измененить и проверить изменение в справочнике книг")
    @Test
    @Order(2)
    void shouldEditRecord() {
        Book book = bookRepository.findById(TEST_BOOK_UID).orElseThrow(null);
        book.setIsbn(TEST_BOOK_ISBN);
        book = bookRepository.save(book);
        assertThat(book.getIsbn()).isEqualTo(TEST_BOOK_ISBN);
    }

    @DisplayName("... должен удалить 1 запись по uid")
    @Test
    @Order(8)
    void shouldDeleteRecordByUid() {
        var initialSize = bookRepository.count();
        bookRepository.deleteById(TEST_BOOK_UID);
        assertThat(bookRepository.findAll().size()).isEqualTo(initialSize - 1);
    }

    @DisplayName("...должен вернуть запись по ее Uid")
    @Test
    @Order(3)
    void shouldGetBookByUid() {
        Book book = bookRepository.findById(TEST_BOOK_UID).orElse(null);
        assertThat(book.getTitle()).isEqualTo(EXPECTED_BOOK_TITLE);
        assertThat(book.getAuthors()).hasSizeGreaterThan(0);
        assertThat(book.getGenres()).hasSizeGreaterThan(0);
        assertThat(book.getComments()).hasSizeGreaterThan(0);
        System.out.println(book);
        System.out.println(book.getAuthors());
        System.out.println(book.getGenres());
    }

    @DisplayName("...должен вернуть все записи")
    @Test
    @Order(4)
    void shouldFindAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSizeGreaterThan(0);
    }


    @DisplayName("... должен вернуть книги за авторством писателя")
    @Test
    @Order(5)
    void shouldGetBooksByAuthorUid() {
        List<Book> books = bookRepository.findBooksByAuthorsId(AUTHOR_UID);
        assertThat(books).isNotEmpty().isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("... должен вернуть книги определенного жанра")
    @Test
    @Order(6)
    void shouldGetBooksByGenreUid() {
        List<Book> books = bookRepository.findBooksByGenresId(GENRE_UID);
        assertThat(!books.isEmpty());
        assertThat(books.size()).isGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника")
    @Test
    @Order(0)
    void shouldGetExpectedCountOfBook() {
        assertThat(bookRepository.count()).isGreaterThan(0);
    }
}