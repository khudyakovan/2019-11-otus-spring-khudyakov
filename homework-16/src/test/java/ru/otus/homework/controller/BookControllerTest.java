package ru.otus.homework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.entity.Author;
import ru.otus.homework.entity.Book;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.entity.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;

    private static final String BASE_URL = "/api/v1/books";
    private static final String DETAILS_URL = "/api/v1/books/15";
    private static final String MODIFY_BOOK_URL = "/api/v1/books";
    private static final long TEST_BOOK_UID = 15L;
    private static final String TEST_BOOK_TITLE_0 = "Test book title 0";
    private static final String TEST_BOOK_TITLE_1 = "Test book title 1";
    private static final long TEST_BOOK_ISBN = 1234567891013L;
    private static final int TEST_BOOK_PUBLISHING_YEAR = 2020;


    @BeforeEach
    void setUp(){
        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        genres.add(new Genre(100500, ""));
        authors.add(new Author(100500,"",""));
        Book book = new Book(TEST_BOOK_UID,
                TEST_BOOK_TITLE_0,
                TEST_BOOK_ISBN,
                TEST_BOOK_PUBLISHING_YEAR,
                authors,
                genres);
        books.add(book);
        given(bookService.findByUid(anyLong())).willReturn(book);
        given(bookService.findAll()).willReturn(books);
        given(bookService.save(any())).willReturn(book);
    }

    @Test
    void shouldGetBooks() throws Exception {
        mvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mvc.perform(get(MODIFY_BOOK_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldGetBookDetails() throws Exception {
        mvc.perform(get(DETAILS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddNewBook() throws Exception{
        Book book = new Book(TEST_BOOK_TITLE_0,TEST_BOOK_ISBN,TEST_BOOK_PUBLISHING_YEAR);
        String inputJson = this.mapToJson(book);
        mvc.perform(post(MODIFY_BOOK_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldEditBook() throws Exception{
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        genres.add(new Genre(100500, ""));
        authors.add(new Author(100500,"",""));
        Book book = new Book(TEST_BOOK_UID,
                TEST_BOOK_TITLE_0,
                TEST_BOOK_ISBN,
                TEST_BOOK_PUBLISHING_YEAR,
                authors,
                genres);
        String inputJson = this.mapToJson(book);
        mvc.perform(put(MODIFY_BOOK_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldDeleteBook() throws Exception{
        mvc.perform(delete(DETAILS_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

     String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
