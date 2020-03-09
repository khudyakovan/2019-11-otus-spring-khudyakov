package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Jpa для работы с жанрами...")
@DataJpaTest
@Import(ApplicationProperties.class)
class GenreRepositoryJpaTest {

    @Autowired
    GenreRepository genreRepository;

    private static final long NEW_GENRE_UID = 0;
    private static final String NEW_GENRE_NAME = "Test Genre Name";
    private static final long TEST_GENRE_1 = 2;
    private static final long TEST_GENRE_2 = 3;
    private static final long BOOK_UID = 19;

    @DisplayName("...должен сохранить запись")
    @Test
    void shouldSave() {
        long countBefore = genreRepository.count();
        Genre genre = new Genre(NEW_GENRE_UID, NEW_GENRE_NAME);
        genreRepository.save(genre);
        assertThat(genreRepository.count()).isEqualTo(countBefore + 1);
    }

    @DisplayName("...должен отредактировать запись")
    @Test
    void shouldEdit() {
        Genre genre = genreRepository.findById(TEST_GENRE_1).orElse(null);
        genre.setName(NEW_GENRE_NAME);
        genreRepository.save(genre);
        genre = genreRepository.findById(TEST_GENRE_1).orElse(null);
        assertThat(genre.getName()).isEqualTo(NEW_GENRE_NAME);
    }

    @DisplayName("...должен удалить запись")
    @Test
    void shouldDeleteByUid() {
        List<Genre> genres = genreRepository.findAll();
        long countBefore = genres.size();
        genreRepository.deleteById(genres.get(0).getUid());
        assertThat(genreRepository.count()).isEqualTo(countBefore-1);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {
        assertThat(genreRepository.findById(TEST_GENRE_1)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {
        assertThat(genreRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("...должен связать жанры с книгами")
    @Test
    void shouldInsertGenresByBookUid() {
        int sizeBefore = genreRepository.findGenresByBookUid(BOOK_UID).size();

        List<Genre> genres = new ArrayList<>();

        genres.add(genreRepository.findById(TEST_GENRE_1).orElse(null));
        genres.add(genreRepository.findById(TEST_GENRE_2).orElse(null));

        genreRepository.appendGenresByBookUid(BOOK_UID, genres);

        genres = genreRepository.findGenresByBookUid(BOOK_UID);
        assertThat(!genres.isEmpty());
        assertThat(genres.size()).isEqualTo(sizeBefore + 2);
        System.out.println(genres);
    }

    @DisplayName("...должен отредактировать жанры книги")
    @Test
    void shouldEditGenresByBookUid() {
        List<Genre> genres = genreRepository.findGenresByBookUid(BOOK_UID);
        int sizeBefore = genres.size();
        genres.remove(0);

        genreRepository.setGenresByBookUid(BOOK_UID, genres);

        genres = genreRepository.findGenresByBookUid(BOOK_UID);
        assertThat(!genres.isEmpty());
        assertThat(genres.size()).isEqualTo(sizeBefore - 1);
    }

    @DisplayName("...должен удалить жанры книги")
    @Test
    void shouldDeleteGenresByBookUid() {
        List<Genre> genres = genreRepository.findGenresByBookUid(BOOK_UID);
        int countBefore = genreRepository.findGenresByBookUid(BOOK_UID).size();

        genreRepository.resetGenresByBookUid(BOOK_UID, genres);
        genres = genreRepository.findGenresByBookUid(BOOK_UID);
        assertThat(genres.size()).isNotEqualTo(countBefore);
    }

    @DisplayName("...должен найти жанры книги по Uid книги")
    @Test
    void shouldFindGenresByBookUid() {
        List<Genre> genres = genreRepository.findGenresByBookUid(BOOK_UID);
        assertThat(genres.size()).isGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника > 0")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(genreRepository.count()).isGreaterThan(0);
    }
}
