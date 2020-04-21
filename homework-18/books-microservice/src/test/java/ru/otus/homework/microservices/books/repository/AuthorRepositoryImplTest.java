package ru.otus.homework.microservices.books.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.microservices.books.config.ApplicationProperties;
import ru.otus.homework.microservices.books.entity.Author;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Jpa для работы с авторами...")
@DataJpaTest
@Import(ApplicationProperties.class)
class AuthorRepositoryImplTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    AuthorRepository authorRepository;

    private static final long BOOK_UID = 15;
    private static final long NEW_AUTHOR_UID = 0;
    private static final long TEST_AUTHOR_1 = 31;
    private static final long TEST_AUTHOR_2 = 32;
    private static final long TEST_AUTHOR_UID = 39;
    private static final String TEST_AUTHOR_NAME = "Test author full name";


    @DisplayName("...должен сохранить запись")
    @Test
    void shouldSaveAuthor() {
        long countBefore = authorRepository.count();
        Author author = new Author(NEW_AUTHOR_UID,
                TEST_AUTHOR_NAME,
                TEST_AUTHOR_NAME);
        authorRepository.save(author);
        assertThat(authorRepository.count()).isEqualTo(countBefore + 1);
    }

    @DisplayName("...должен отредактировать запись")
    @Test
    void shouldEditAuthor(){
        Author author = authorRepository.findById(TEST_AUTHOR_UID).orElse(null);
        author.setFullName(TEST_AUTHOR_NAME);
        author.setPenName(TEST_AUTHOR_NAME);
        authorRepository.save(author);
        author = authorRepository.findById(TEST_AUTHOR_UID).orElse(null);
        assertThat(author.getFullName().equals(TEST_AUTHOR_NAME));
        assertThat(author.getPenName().equals(TEST_AUTHOR_NAME));
    }

    @DisplayName("...должен удалить запись")
    @Test
    void shouldDeleteByUid() {
        List<Author> authors = authorRepository.findAll();
        long countBefore = authors.size();

        authorRepository.deleteById(authors.get(0).getUid());
        assertThat(authorRepository.count()).isEqualTo(countBefore-1);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {
        assertThat(authorRepository.findById(TEST_AUTHOR_1)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {

        assertThat(authorRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("...должен связать авторов с книгами")
    @Test
    void shouldInsertAuthorsByBookUid() {
        int sizeBefore = authorRepository.findAuthorsByBookUid(BOOK_UID).size();

        List<Author> authors = new ArrayList<>();

        authors.add(authorRepository.findById(TEST_AUTHOR_1).orElse(null));
        authors.add(authorRepository.findById(TEST_AUTHOR_2).orElse(null));

        authorRepository.appendAuthorsByBookUid(BOOK_UID, authors);

        authors = authorRepository.findAuthorsByBookUid(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size()).isEqualTo(sizeBefore + 2);
        System.out.println(authors);
    }

    @DisplayName("...должен отредактировать авторов книги")
    @Test
    void shouldEditAuthorsByBookUid() {
        List<Author> authors = authorRepository.findAuthorsByBookUid(BOOK_UID);
        int sizeBefore = authors.size();
        authors.remove(0);

        authorRepository.setAuthorsByBookUid(BOOK_UID, authors);

        authors = authorRepository.findAuthorsByBookUid(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size()).isEqualTo(sizeBefore - 1);
    }

    @DisplayName("...должен удалить авторов книги")
    @Test
    void shouldDeleteAuthorsByBookUid() {
        List<Author> authors = authorRepository.findAuthorsByBookUid(BOOK_UID);
        int countBefore = authorRepository.findAuthorsByBookUid(BOOK_UID).size();

        authorRepository.resetAuthorsByBookUid(BOOK_UID, authors);
        authors = authorRepository.findAuthorsByBookUid(BOOK_UID);
        assertThat(authors.size()).isNotEqualTo(countBefore);
    }

    @DisplayName("...должен найти авторов книги по Uid книги")
    @Test
    void shouldFindAuthorsByBookUid() {
        List<Author> authors = authorRepository.findAuthorsByBookUid(BOOK_UID);
        assertThat(authors.size()).isGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника > 0")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(authorRepository.count()).isGreaterThan(0);
    }
}