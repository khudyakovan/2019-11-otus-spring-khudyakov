package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.homework.entity.Genre;
import ru.otus.homework.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@DisplayName("Сервис для работы с жанрами...")
@SpringBootTest
class GenreServiceImplTest {

    @MockBean
    GenreRepository genreRepository;

    @Autowired
    GenreServiceImpl genreService;

    private static final long NEW_GENRE_UID = 100500;
    private static final String NEW_GENRE_NAME = "Test Genre Name";
    private static final long TEST_GENRE_1 = 2;
    private static final long BOOK_UID = 19;

    @BeforeEach
    void setUp() {
        List<Genre> genres = new ArrayList<>();
        Genre genre = new Genre(NEW_GENRE_UID, NEW_GENRE_NAME);
        genres.add(genre);
        given(genreRepository.findAll()).willReturn(genres);
        given(genreRepository.findById(anyLong())).willReturn(Optional.ofNullable(genre));
        given(genreRepository.findGenresByBookUid(anyLong())).willReturn(genres);
        given(genreRepository.count()).willReturn(NEW_GENRE_UID);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {
        assertThat(genreService.findByUid(TEST_GENRE_1)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {

        assertThat(genreService.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("...должен найти жанры книги по Uid книги")
    @Test
    void shouldFindGenresByBookUid() {
        List<Genre> genres = genreService.findGenresByBookUid(BOOK_UID);
        assertThat(genres.size()).isGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника > 0")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(genreService.count()).isGreaterThan(0);
    }

}
