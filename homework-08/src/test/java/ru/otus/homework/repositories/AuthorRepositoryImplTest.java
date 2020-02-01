package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework.AbstractRepositoryTest;
import ru.otus.homework.models.Author;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами...")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    private static final String BOOK_UID = "15";
    private static final String NEW_AUTHOR_UID = "0";
    private static final String TEST_AUTHOR_1 = "31";
    private static final String TEST_AUTHOR_2 = "32";
    private static final String TEST_AUTHOR_UID = "39";
    private static final String TEST_AUTHOR_NAME = "Test author full name";

    @DisplayName("...должен сохранить запись")
    @Test
    @Order(1)
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
    @Order(2)
    void shouldEditAuthor(){
        Author author = authorRepository.findById(TEST_AUTHOR_UID).orElse(null);
        author.setFullName(TEST_AUTHOR_NAME);
        author.setPenName(TEST_AUTHOR_NAME);
        authorRepository.save(author);
        author = authorRepository.findById(TEST_AUTHOR_UID).orElse(null);
        assertThat(author.getFullName().equals(TEST_AUTHOR_NAME));
        assertThat(author.getPenName().equals(TEST_AUTHOR_NAME));
    }

    @DisplayName("...должен удалить запись и каскадно удалить автора в книгах")
    @Test
    @Order(9)
    void shouldDeleteByUid() {
        List<Author> authors = authorRepository.findAll();
        val authorsBefore = authors.size();
        Author author = authors.get(0);

        val countOfBookBefore = bookRepository.findBooksByAuthorsId(author.getId()).size();

        authorRepository.deleteById(author.getId());
        assertThat(authorRepository.count()).isEqualTo(authorsBefore-1);

        //Проверка каскадного удаления автора из книг
        val countOfBookAfter = bookRepository.findBooksByAuthorsId(author.getId()).size();
        assertThat(countOfBookBefore).isGreaterThan(0);
        assertThat(countOfBookAfter).isEqualTo(0);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    @Order(3)
    void shouldFindByUid() {
        assertThat(authorRepository.findById(TEST_AUTHOR_1)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    @Order(4)
    void shouldFindAll() {

        assertThat(authorRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("...должен связать авторов с книгами")
    @Test
    @Order(5)
    void shouldInsertAuthorsByBookUid() {
        int sizeBefore = authorRepository.findAuthorsByBookId(BOOK_UID).size();

        List<Author> authors = new ArrayList<>();

        authors.add(authorRepository.findById(TEST_AUTHOR_1).orElse(null));
        authors.add(authorRepository.findById(TEST_AUTHOR_2).orElse(null));

        authorRepository.appendAuthorsByBookId(BOOK_UID, authors);

        authors = authorRepository.findAuthorsByBookId(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size()).isEqualTo(sizeBefore + 2);
        System.out.println(authors);
    }

    @DisplayName("...должен отредактировать авторов книги")
    @Test
    @Order(6)
    void shouldEditAuthorsByBookUid() {
        List<Author> authors = authorRepository.findAuthorsByBookId(BOOK_UID);
        int sizeBefore = authors.size();
        authors.remove(0);

        authorRepository.setAuthorsByBookId(BOOK_UID, authors);

        authors = authorRepository.findAuthorsByBookId(BOOK_UID);
        assertThat(!authors.isEmpty());
        assertThat(authors.size()).isEqualTo(sizeBefore - 1);
    }

    @DisplayName("...должен удалить авторов книги")
    @Test
    @Order(10)
    void shouldDeleteAuthorsByBookUid() {
        List<Author> authors = authorRepository.findAuthorsByBookId(BOOK_UID);
        int countBefore = authorRepository.findAuthorsByBookId(BOOK_UID).size();

        authorRepository.resetAuthorsByBookId(BOOK_UID, authors);
        authors = authorRepository.findAuthorsByBookId(BOOK_UID);
        assertThat(authors.size()).isNotEqualTo(countBefore);
    }

    @DisplayName("...должен найти авторов книги по Uid книги")
    @Test
    @Order(7)
    void shouldFindAuthorsByBookUid() {
        List<Author> authors = authorRepository.findAuthorsByBookId(BOOK_UID);
        assertThat(authors.size()).isGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника > 0")
    @Test
    @Order(8)
    void shouldGetExpectedCountOfBook() {
        assertThat(authorRepository.count()).isGreaterThan(0);
    }
}