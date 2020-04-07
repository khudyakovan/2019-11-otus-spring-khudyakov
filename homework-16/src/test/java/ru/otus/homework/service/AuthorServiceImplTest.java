package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.homework.entity.Author;
import ru.otus.homework.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с авторами...")
@SpringBootTest
@Import({AuthorServiceImpl.class})
class AuthorServiceImplTest {

    @MockBean
    AuthorRepository authorRepository;

    @Autowired
    AuthorServiceImpl authorService;

    private static final long BOOK_UID = 15;
    private static final long NEW_AUTHOR_UID = 100500;
    private static final long TEST_AUTHOR_1 = 31;
    private static final String TEST_AUTHOR_NAME = "Test author full name";

    @BeforeEach
    void setUp(){
        List<Author> authors = new ArrayList<>();
        Author author = new Author(NEW_AUTHOR_UID,TEST_AUTHOR_NAME, TEST_AUTHOR_NAME);
        authors.add(author);
        given(authorRepository.findAll()).willReturn(authors);
        given(authorRepository.findById(anyLong())).willReturn(Optional.ofNullable(author));
        given(authorRepository.findAuthorsByBookUid(anyLong())).willReturn(authors);
        given(authorRepository.count()).willReturn(NEW_AUTHOR_UID);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {
        assertThat(authorService.findByUid(TEST_AUTHOR_1)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {

        assertThat(authorService.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("...должен найти авторов книги по Uid книги")
    @Test
    void shouldFindAuthorsByBookUid() {
        List<Author> authors = authorService.findAuthorsByBookUid(BOOK_UID);
        assertThat(authors.size()).isGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника > 0")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(authorService.count()).isGreaterThan(0);
    }

}