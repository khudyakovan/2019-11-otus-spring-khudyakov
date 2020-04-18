package ru.otus.homework.microservice.genres.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.microservice.genres.config.ApplicationProperties;
import ru.otus.homework.microservice.genres.entity.Genre;

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

    @DisplayName("... должен вернуть количество записей справочника > 0")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(genreRepository.count()).isGreaterThan(0);
    }
}
