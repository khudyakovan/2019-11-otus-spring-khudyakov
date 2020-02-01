package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework.AbstractRepositoryTest;
import ru.otus.homework.models.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с жанрами...")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    GenreRepository genreRepository;
    @Autowired
    BookRepository bookRepository;

    private static final String NEW_GENRE_UID = "0";
    private static final String NEW_GENRE_NAME = "Test Genre Name";
    private static final String TEST_GENRE_1 = "2";
    private static final String TEST_GENRE_2 = "3";
    private static final String BOOK_UID = "19";

    @DisplayName("...должен сохранить запись")
    @Test
    @Order(1)
    void shouldSave() {
        long countBefore = genreRepository.count();
        Genre genre = new Genre(NEW_GENRE_UID, NEW_GENRE_NAME);
        genreRepository.save(genre);
        assertThat(genreRepository.count()).isEqualTo(countBefore + 1);
    }

    @DisplayName("...должен отредактировать запись")
    @Test
    @Order(2)
    void shouldEdit() {
        Genre genre = genreRepository.findById(TEST_GENRE_1).orElse(null);
        genre.setName(NEW_GENRE_NAME);
        genreRepository.save(genre);
        genre = genreRepository.findById(TEST_GENRE_1).orElse(null);
        assertThat(genre.getName()).isEqualTo(NEW_GENRE_NAME);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    @Order(3)
    void shouldFindByUid() {
        assertThat(genreRepository.findById(TEST_GENRE_1)).isNotEmpty();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    @Order(3)
    void shouldFindAll() {
        assertThat(genreRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("...должен отредактировать жанры книги")
    @Test
    @Order(4)
    void shouldEditGenresByBookUid() {
        List<Genre> genres = genreRepository.findGenresByBookUid(BOOK_UID);
        int sizeBefore = genres.size();
        genres.remove(0);

        genreRepository.setGenresByBookUid(BOOK_UID, genres);

        genres = genreRepository.findGenresByBookUid(BOOK_UID);
        assertThat(!genres.isEmpty());
        assertThat(genres.size()).isEqualTo(sizeBefore - 1);
    }

    @DisplayName("...должен связать жанры с книгами")
    @Test
    @Order(5)
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

    @DisplayName("...должен найти жанры книги по Uid книги")
    @Test
    @Order(5)
    void shouldFindGenresByBookUid() {
        List<Genre> genres = genreRepository.findGenresByBookUid(BOOK_UID);
        assertThat(genres.size()).isGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника > 0")
    @Test
    @Order(6)
    void shouldGetExpectedCountOfBook() {
        assertThat(genreRepository.count()).isGreaterThan(0);
    }

    @DisplayName("...должен удалить запись")
    @Test
    @Order(7)
    void shouldDeleteByUid() {
        List<Genre> genres = genreRepository.findAll();
        val countBefore = genres.size();
        Genre genre = genres.get(0);

        val countOfBookBefore = bookRepository.findBooksByGenresId(genre.getId()).size();
        genreRepository.deleteById(genre.getId());

        assertThat(genreRepository.count()).isEqualTo(countBefore-1);

        //Проверка каскадного удаления жанра из книг
        val countOfBookAfter = bookRepository.findBooksByGenresId(genre.getId()).size();
        assertThat(countOfBookBefore).isGreaterThan(0);
        assertThat(countOfBookAfter).isEqualTo(0);
    }

    @DisplayName("...должен удалить жанры книги")
    @Test
    @Order(8)
    void shouldDeleteGenresByBookUid() {
        List<Genre> genres = genreRepository.findGenresByBookUid(BOOK_UID);
        int countBefore = genreRepository.findGenresByBookUid(BOOK_UID).size();

        genreRepository.resetGenresByBookUid(BOOK_UID, genres);
        genres = genreRepository.findGenresByBookUid(BOOK_UID);
        assertThat(genres.size()).isNotEqualTo(countBefore);
    }
}