package ru.otus.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private static final String INDEX = "index";
    private static final String DETAILS = "book-details";
    private static final String EXPECTED_VIEW_AFTER_ADD = "redirect:/books/15";
    private static final String EXPECTED_VIEW_AFTER_EDIT = "redirect:/books/15";
    private static final String EXPECTED_VIEW_AFTER_DELETE = "redirect:/";
    private static final String INDEX_URL = "/";
    private static final String DETAILS_URL = "/books/15";
    private static final String ADD_BOOK_URL = "/books/add";
    private static final String EDIT_BOOK_URL = "/books/15/edit";
    private static final String DELETE_BOOK_URL = "/books/15/delete";
    private static final long TEST_BOOK_UID = 15L;
    private static final String TEST_BOOK_TITLE_0 = "Test book title 0";
    private static final String TEST_BOOK_TITLE_1 = "Test book title 1";
    private static final String TEST_BOOK_ISBN = "1234567891013";
    private static final String TEST_BOOK_PUBLISHING_YEAR = "2020";

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
                Long.parseLong(TEST_BOOK_ISBN),
                Integer.parseInt(TEST_BOOK_PUBLISHING_YEAR),
                authors,
                genres);
//        comments.add(new Comment(100500,
//                new Commentator("","","",""),
//                "",
//                null));

        //book.setComments(comments);
        books.add(book);
        given(bookService.findByUid(anyLong())).willReturn(book);
        given(bookService.findAll()).willReturn(books);
        given(bookService.save(any())).willReturn(book);
    }

    @Order(0)
    @Test
    void shouldGetIndexView() throws Exception {
        mvc.perform(get(INDEX_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX));
    }

    @Order(1)
    @Test
    void shouldShowIndexPage() throws Exception {
        mvc.perform(get(INDEX_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_BOOK_TITLE_0)));
    }

    @WithMockUser(
            authorities = {"ROLE_ANONYMOUS"}
    )
    @Order(2)
    @Test
    void shouldShowBookDetailsPage() throws Exception {
        mvc.perform(get(DETAILS_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_BOOK_TITLE_0)));
    }

    @Order(3)
    @Test
    void shouldAddNewBook() throws Exception {
        mvc.perform(post(ADD_BOOK_URL)
                .param("title", TEST_BOOK_TITLE_0)
                .param("isbn", TEST_BOOK_ISBN)
                .param("publicationYear", TEST_BOOK_PUBLISHING_YEAR))
                .andExpect(view().name(EXPECTED_VIEW_AFTER_ADD));
    }

    @Order(4)
    @Test
    void shouldEditExistingBook() throws Exception {
        mvc.perform(post(EDIT_BOOK_URL)
                .param("title", TEST_BOOK_TITLE_1)
                .param("isbn", TEST_BOOK_ISBN)
                .param("publicationYear", TEST_BOOK_PUBLISHING_YEAR))
                .andExpect(view().name(EXPECTED_VIEW_AFTER_EDIT));
    }

    @Order(5)
    @Test
    void shouldShowDeleteView() throws Exception {
        mvc.perform(get(DELETE_BOOK_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(DETAILS));
    }

    @Order(6)
    @Test
    void shouldDeleteExistingBook() throws Exception {
        mvc.perform(post(DELETE_BOOK_URL))
                .andExpect(view().name(EXPECTED_VIEW_AFTER_DELETE));
    }
}
