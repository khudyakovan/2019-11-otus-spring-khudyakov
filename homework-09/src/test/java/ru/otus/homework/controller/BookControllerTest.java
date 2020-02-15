package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.entity.Book;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.service.BookService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    private static final String EXPECTED_VIEW_NAME = "index";
    private static final String EXPECTED_STRING = "The Catcher in the Rye";
    private static final String INDEX_URL = "/";
    private static final String DETAILS_URL = "/books/10";
    private static final String ADD_BOOK_URL = "/books/add";
    private static final String EDIT_BOOK_URL = "/books/15/edit";
    private static final String DELETE_BOOK_URL = "/books/16/delete";
    private static final long TEST_BOOK_UID = 15L;
    private static final String TEST_BOOK_TITLE_0 = "Test book title 0";
    private static final String TEST_BOOK_TITLE_1 = "Test book title 1";
    private static final String TEST_BOOK_ISBN = "1234567891013";
    private static final String TEST_BOOK_PUBLISHING_YEAR = "2020";

    @Test
    void shouldGetIndexView() throws Exception {
        mvc.perform(get(INDEX_URL))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(EXPECTED_VIEW_NAME));
    }

    @Test
    void shouldShowIndexPage() throws Exception {
        mvc.perform(get(INDEX_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_STRING)));
    }

    @Test
    void shouldShowBookDetailsPage() throws Exception {
        mvc.perform(get(DETAILS_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_STRING)));
    }


    @Test
    void shouldAddNewBook() throws Exception {
        var booksCountBefore = bookService.count();
        mvc.perform(post(ADD_BOOK_URL)
                .param("title", TEST_BOOK_TITLE_0)
                .param("isbn", TEST_BOOK_ISBN)
                .param("publicationYear", TEST_BOOK_PUBLISHING_YEAR));
        //.andDo(print());
        var booksCountAfter = bookService.count();
        assertThat(booksCountBefore).isLessThan(booksCountAfter);
    }

    @Test
    void shouldEditExistingBook() throws Exception {
        Book book = bookRepository.findById(TEST_BOOK_UID).get();
        var title = book.getTitle();
        var isbn = book.getIsbn();
        var year = book.getPublicationYear();

        mvc.perform(post(EDIT_BOOK_URL)
                .param("title", TEST_BOOK_TITLE_1)
                .param("isbn", TEST_BOOK_ISBN)
                .param("publicationYear", TEST_BOOK_PUBLISHING_YEAR)
        );
        book = bookService.findByUid(TEST_BOOK_UID);

        assertThat(title).isNotEqualTo(book.getTitle());
        assertThat(isbn).isNotEqualTo(book.getIsbn());
        assertThat(year).isNotEqualTo(book.getPublicationYear());
    }

    @Test
    void shouldDeleteExistingBook() throws Exception{
        var booksCountBefore = bookService.count();
        mvc.perform(post(DELETE_BOOK_URL));
        var booksCountAfter = bookService.count();
        assertThat(booksCountBefore).isGreaterThan(booksCountAfter);
    }
}