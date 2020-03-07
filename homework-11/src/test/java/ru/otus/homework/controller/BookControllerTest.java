package ru.otus.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.controllers.BookController;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Comment;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(value = BookController.class)
class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    private static final String BASE_URL = "/api/v1/books";
    private static final String DETAILS_URL = "/api/v1/books/15";
    private static final String MODIFY_BOOK_URL = "/api/v1/books";
    private static final String TEST_BOOK_UID = "15";
    private static final String TEST_BOOK_TITLE_0 = "Test book title 0";
    private static final long TEST_BOOK_ISBN = 1234567891013L;
    private static final int TEST_BOOK_PUBLISHING_YEAR = 2020;


    @BeforeEach
    void setUp(){
        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        genres.add(new Genre("100500", ""));
        authors.add(new Author("100500","",""));
        Book book = new Book(TEST_BOOK_UID,
                TEST_BOOK_TITLE_0,
                TEST_BOOK_ISBN,
                TEST_BOOK_PUBLISHING_YEAR,
                authors,
                genres,
                comments);
        books.add(book);
        Mono<Book> bookMono = Mono.just(book);
        Flux<Book> bookFlux = Flux.fromIterable(books);

        given(bookRepository.findById(anyString())).willReturn(bookMono);
        given(bookRepository.findAll()).willReturn(bookFlux);
        given(bookRepository.save(any())).willReturn(bookMono);
        given(bookRepository.deleteById(anyString())).willReturn(Mono.empty());
    }

    @Test
    void shouldGetBooks() throws Exception {
        webTestClient.get().uri(BASE_URL)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    @Test
    void shouldGetBookDetails() throws Exception {
        webTestClient.get().uri(DETAILS_URL)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    @Test
    void shouldAddNewBook() throws Exception{
        Book book = new Book(TEST_BOOK_TITLE_0,TEST_BOOK_ISBN,TEST_BOOK_PUBLISHING_YEAR);

        webTestClient
                .post()
                .uri(MODIFY_BOOK_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(book), BodyInserter.class)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void shouldEditBook() throws Exception{
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        genres.add(new Genre("100500", ""));
        authors.add(new Author("100500","",""));
        Book book = new Book(TEST_BOOK_UID,
                TEST_BOOK_TITLE_0,
                TEST_BOOK_ISBN,
                TEST_BOOK_PUBLISHING_YEAR,
                authors,
                genres,
                comments);
        webTestClient
                .post()
                .uri(MODIFY_BOOK_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(book), BodyInserter.class)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void shouldDeleteBook() throws Exception{
        webTestClient
                .delete()
                .uri(DETAILS_URL)
                .exchange()
                .expectStatus().isOk();
    }
}
