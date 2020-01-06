package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework.domain.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы со справочником книг")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class BookServiceImplTest {

    private final long BOOK_UID = 15;
    private final long AUTHOR_UID = 44;
    private final long GENRE_UID = 20;
    private final String EXPECTED_BOOK_TITLE = "Nineteen Eighty-Four";
    private final int EXPECTED_AUTHORS_COUNT = 3;
    private final int EXPECTED_GENRES_COUNT = 3;
    private final int UNEXPECTED_VALUE = 0;

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @DisplayName("Получает книгу, ее жанр и авторов по Uid")
    @Test
    void shouldGetBookByUid() {
        Book book = bookService.getByUid(BOOK_UID);
        assertAll(
                () -> assertNotNull(book),
                () -> assertNotNull(book.getGenres()),
                () -> assertNotNull(book.getAuthors()),
                () -> assertEquals(EXPECTED_AUTHORS_COUNT, book.getAuthors().size()),
                () -> assertEquals(EXPECTED_GENRES_COUNT, book.getGenres().size()),
                () -> assertEquals(EXPECTED_BOOK_TITLE, book.getTitle())
        );
    }

    @DisplayName("Получает список книг по Uid, проверяет наличие авторов и жанров")
    @Test
    void shouldGetBooksByAuthorUid() {
        List<Book> books = bookService.getBooksByAuthorUid(AUTHOR_UID);
        Book book = books.stream()
                .filter(b -> b.getUid() == BOOK_UID)
                .findAny()
                .orElse(null);
        assertAll(
                () -> assertNotNull(books),
                () -> assertNotEquals(UNEXPECTED_VALUE, books.size()),
                () -> assertNotNull(book),
                () -> assertNotNull(book.getGenres()),
                () -> assertNotNull(book.getAuthors()),
                () -> assertEquals(EXPECTED_AUTHORS_COUNT, book.getAuthors().size()),
                () -> assertEquals(EXPECTED_GENRES_COUNT, book.getGenres().size()),
                () -> assertEquals(EXPECTED_BOOK_TITLE, book.getTitle())
        );
    }

    @DisplayName("Получает список книг по Uid, проверяет наличие жанров и авторов")
    @Test
    void shouldGetGenresByBookUid() {
        List<Book> books = bookService.getBooksByGenreUid(GENRE_UID);
        Book book = books.stream()
                .filter(b -> b.getUid() == BOOK_UID)
                .findAny()
                .orElse(null);
        assertAll(
                () -> assertNotNull(books),
                () -> assertNotEquals(UNEXPECTED_VALUE, books.size()),
                () -> assertNotNull(book),
                () -> assertNotNull(book.getGenres()),
                () -> assertNotNull(book.getAuthors()),
                () -> assertEquals(3, book.getAuthors().size()),
                () -> assertEquals(3, book.getGenres().size()),
                () -> assertEquals(EXPECTED_BOOK_TITLE, book.getTitle())
        );
    }
}