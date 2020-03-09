package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.homework.entity.Author;
import ru.otus.homework.entity.Book;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.entity.Genre;
import ru.otus.homework.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@DisplayName("Сервис для работы с книгами...")
@SpringBootTest
@Import({BookServiceImpl.class})
class BookServiceImplTest {

    @MockBean
    BookRepository bookRepository;

    @Autowired
    BookServiceImpl bookService;

    private static final long EXPECTED_BOOKS_COUNT = 20;
    private static final long TEST_BOOK_UID = 10;
    private static final long TEST_BOOK_ISBN = 1234567891013L;
    private static final int TEST_BOOK_PUBLISHING_YEAR = 21;
    private static final String TEST_BOOK_TITLE = "Test book title";
    private static final long AUTHOR_UID = 44;
    private static final long GENRE_UID = 1;

    @BeforeEach
    void setUp(){
        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        Book book = new Book(TEST_BOOK_TITLE, TEST_BOOK_ISBN, TEST_BOOK_PUBLISHING_YEAR);
        genres.add(new Genre(100500, ""));
        authors.add(new Author(100500,"",""));
//        comments.add(new Comment(100500,
//                new Commentator("","","",""),
//                "",
//                null));
        book.setAuthors(authors);
        book.setGenres(genres);
        //book.setComments(comments);
        books.add(book);
        given(bookRepository.findById(anyLong())).willReturn(Optional.ofNullable(book));
        given(bookRepository.findAll()).willReturn(books);
        given(bookRepository.findBooksByAuthorUid(anyLong())).willReturn(books);
        given(bookRepository.findBooksByGenreId(anyLong())).willReturn(books);
        given(bookRepository.count()).willReturn(EXPECTED_BOOKS_COUNT);
    }


    @DisplayName("...должен вернуть запись по ее Uid")
    @Test
    void shouldFindBookByUid() {
        Book book = bookService.findByUid(TEST_BOOK_UID);
        assertThat(book.getAuthors()).isNotNull().hasSizeGreaterThan(0);
        assertThat(book.getGenres()).isNotNull().hasSizeGreaterThan(0);
        //assertThat(book.getComments()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("...должен вернуть все записи")
    @Test
    void shouldFindAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSizeGreaterThan(0);
        List<Author> authors = books.get(0).getAuthors();
        List<Genre> genres = books.get(0).getGenres();
        //List<Comment> comments = books.get(0).getComments();
        assertThat(authors).isNotNull().hasSizeGreaterThan(0);
        assertThat(genres).isNotNull().hasSizeGreaterThan(0);
        //assertThat(comments).isNotNull().hasSizeGreaterThan(0);
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
        List<Book> books = bookRepository.findBooksByGenreId(GENRE_UID);
        assertThat(books).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(bookRepository.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }
}
