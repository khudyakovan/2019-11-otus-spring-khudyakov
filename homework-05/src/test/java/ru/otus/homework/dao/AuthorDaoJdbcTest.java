package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы со справочником авторов")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
@Import(AuthorDaoJdbc.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHORS_COUNT = 22;
    private static final int EXPECTED_AUTHORS_COUNT_AFTER_INSERT = 23;
    private static final long TEST_AUTHOR_UID = 39;
    private static final String TEST_AUTHOR_NAME = "Test author full name";
    private static final String EXPECTED_AUTHOR_NAME = "Jerome Salinger";

    @Autowired
    private AuthorDaoJdbc jdbc;

    @DisplayName("Добавляет одну запись в БД и проверяет, что количество изменилось")
    @Test
    void shouldInsertNewRecord() {
        Author author = new Author(
                TEST_AUTHOR_NAME,
                TEST_AUTHOR_NAME);
        jdbc.insert(author);
        assertThat(author.getUid()).isGreaterThan(0);
        assertThat(jdbc.count()).isEqualTo(EXPECTED_AUTHORS_COUNT_AFTER_INSERT);
    }

    @DisplayName("Изменение и проверка изменения в справочнике")
    @Test
    void shouldEditRecord() {
        Author author = jdbc.getByUid(TEST_AUTHOR_UID);
        author.setFullName(TEST_AUTHOR_NAME);
        author.setPenName(TEST_AUTHOR_NAME);
        jdbc.edit(author);
        author = jdbc.getByUid(TEST_AUTHOR_UID);
        assertThat(author.getFullName().equals(TEST_AUTHOR_NAME));
        assertThat(author.getPenName().equals(TEST_AUTHOR_NAME));
    }

    @DisplayName("Удаляет 1 запись по uid")
    @Test
    void shouldDeleteRecordByUid() {
        jdbc.deleteByUid(TEST_AUTHOR_UID);
        assertThat(jdbc.count()).isEqualTo(EXPECTED_AUTHORS_COUNT -1);
    }

    @DisplayName("Возвращает 1 запись по uid")
    @Test
    void shouldGetBookByUid() {
        Author author = jdbc.getByUid(TEST_AUTHOR_UID);
        assertThat(author.getFullName().equals(EXPECTED_AUTHOR_NAME));
    }

    @DisplayName("Возвращает все записи справочника")
    @Test
    void shouldGetAllBook() {
        assertThat(!jdbc.getAll().isEmpty());
    }

    @DisplayName("Возвращает количество записей справочника")
    @Test
    void testShouldGetExpectedCountOfBook() {
        assertThat(jdbc.count()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

}