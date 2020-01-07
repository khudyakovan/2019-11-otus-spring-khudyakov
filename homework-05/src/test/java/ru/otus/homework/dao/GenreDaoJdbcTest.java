package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы со справочником жанров")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 26;
    private static final int EXPECTED_GENRES_COUNT_AFTER_INSERT = 27;
    private static final long TEST_GENRE_UID = 2;
    private static final String TEST_GENRE_NAME = "Test Genre Name";
    private static final String EXPECTED_GENRE_NAME = "Science fiction";

    @Autowired
    private GenreDaoJdbc jdbc;

    @DisplayName("Добавляет одну запись в БД и проверяет, что количество изменилось")
    @Test
    void shouldInsertNewRecord() {
        Genre genre = new Genre(TEST_GENRE_NAME);
        jdbc.insert(genre);
        assertThat(jdbc.count()).isEqualTo(EXPECTED_GENRES_COUNT_AFTER_INSERT);
    }

    @DisplayName("Изменение и проверка изменения в справочнике")
    @Test
    void shouldEditRecord() {
        Genre genre = jdbc.getByUid(TEST_GENRE_UID);
        genre.setName(TEST_GENRE_NAME);
        jdbc.edit(genre);
        genre = jdbc.getByUid(TEST_GENRE_UID);
        assertThat(genre.getName().equals(TEST_GENRE_NAME));
    }

    @DisplayName("Удаляет 1 запись по uid")
    @Test
    void shouldDeleteRecordByUid() {
        jdbc.deleteByUid(TEST_GENRE_UID);
        assertThat(jdbc.count()).isEqualTo(EXPECTED_GENRES_COUNT-1);
    }

    @DisplayName("Возвращает 1 запись по uid")
    @Test
    void shouldGetBookByUid() {
        Genre genre = jdbc.getByUid(TEST_GENRE_UID);
        assertThat(genre.getName().equals(EXPECTED_GENRE_NAME));
    }

    @DisplayName("Возвращает все записи справочника")
    @Test
    void shouldGetAllBook() {
        assertThat(!jdbc.getAll().isEmpty());
    }

    @DisplayName("Возвращает количество записей справочника")
    @Test
    void testShouldGetExpectedCountOfBook() {
        assertThat(jdbc.count()).isEqualTo(EXPECTED_GENRES_COUNT);
    }

}