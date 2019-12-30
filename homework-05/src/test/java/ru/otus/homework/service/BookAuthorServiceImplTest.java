package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework.domain.Author;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Сервис для работы с регистром книг и их авторов")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class BookAuthorServiceImplTest {

    private final long BOOK_UID = 15;
    private final long AUTHOR_UID = 44;
    private final String AUTHOR_FULL_NAME_0 = "Modern Author";
    private final String AUTHOR_PEN_NAME_0 = "Modern Author's Pen Name";
    private final String AUTHOR_FULL_NAME_1 = "Another Modern Author";
    private final String AUTHOR_PEN_NAME_1 = "Another Modern Author's Pen Name";
    private final int EXPECTED_AUTHORS_COUNT_AFTER_EDIT = 2;
    private final int UNEXPECTED_VALUE = 0;
    private final int EXPECTED_VALUE = 1;

    @Autowired
    private BookAuthorServiceImpl bookAuthorService;
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @Test
    void shouldInsertAuthorsByBookUid() {
        int size = bookAuthorService.getAuthorsByBookUid(BOOK_UID).size();
        Author author0 = authorService.insert(new Author(AUTHOR_FULL_NAME_0, AUTHOR_PEN_NAME_0));
        Author author1 = authorService.insert(new Author(AUTHOR_FULL_NAME_1, AUTHOR_PEN_NAME_1));
        List<Author> authors = new ArrayList<>();
        authors.add(author0);
        authors.add(author1);
        bookAuthorService.insertAuthorsByBookUid(BOOK_UID, authors);
        assertEquals(size + 2, bookAuthorService.getAuthorsByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldEditAuthorsByBookUid() {
        Author author0 = authorService.insert(new Author(AUTHOR_FULL_NAME_0, AUTHOR_PEN_NAME_0));
        Author author1 = authorService.insert(new Author(AUTHOR_FULL_NAME_1, AUTHOR_PEN_NAME_1));
        List<Author> authors = new ArrayList<>();
        authors.add(author0);
        authors.add(author1);
        bookAuthorService.editAuthorsByBookUid(BOOK_UID, authors);
        assertEquals(EXPECTED_AUTHORS_COUNT_AFTER_EDIT, bookAuthorService.getAuthorsByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldDeleteAuthorsByBookUid() {
        List<Author> authors = bookAuthorService.getAuthorsByBookUid(BOOK_UID);
        int size = authors.size();
        authors.remove(0);
        bookAuthorService.deleteAuthorsByBookUid(BOOK_UID, authors);
        assertEquals(EXPECTED_VALUE, bookAuthorService.getAuthorsByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldGetBooksByAuthorUid() {
        assertNotEquals(UNEXPECTED_VALUE, bookAuthorService.getBooksByAuthorUid(AUTHOR_UID).size());
    }

    @Test
    void shouldGetAuthorsByBookUid() {
        assertNotEquals(UNEXPECTED_VALUE, bookAuthorService.getAuthorsByBookUid(BOOK_UID));
    }
}