package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework.dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @Test
    void shouldInsertAuthorsByBookUid() {
        int size = authorService.getAuthorsByBookUid(BOOK_UID).size();
        AuthorDto author0 = authorService.insert(new AuthorDto(AUTHOR_FULL_NAME_0, AUTHOR_PEN_NAME_0));
        AuthorDto author1 = authorService.insert(new AuthorDto(AUTHOR_FULL_NAME_1, AUTHOR_PEN_NAME_1));
        List<AuthorDto> authors = new ArrayList<>();
        authors.add(author0);
        authors.add(author1);
        authorService.insertAuthorsByBookUid(BOOK_UID, authors);
        assertEquals(size + 2, authorService.getAuthorsByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldEditAuthorsByBookUid() {
        AuthorDto author0 = authorService.insert(new AuthorDto(AUTHOR_FULL_NAME_0, AUTHOR_PEN_NAME_0));
        AuthorDto author1 = authorService.insert(new AuthorDto(AUTHOR_FULL_NAME_1, AUTHOR_PEN_NAME_1));
        List<AuthorDto> authors = new ArrayList<>();
        authors.add(author0);
        authors.add(author1);
        authorService.editAuthorsByBookUid(BOOK_UID, authors);
        assertEquals(EXPECTED_AUTHORS_COUNT_AFTER_EDIT, authorService.getAuthorsByBookUid(BOOK_UID).size());
    }

    @Test
    void shouldDeleteAuthorsByBookUid() {
        List<AuthorDto> authors = authorService.getAuthorsByBookUid(BOOK_UID);
        int size = authors.size();
        authors.remove(0);
        authorService.deleteAuthorsByBookUid(BOOK_UID, authors);
        assertEquals(EXPECTED_VALUE, authorService.getAuthorsByBookUid(BOOK_UID).size());
    }
}