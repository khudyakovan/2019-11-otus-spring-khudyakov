package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами...")
@DataJpaTest
@Import({AuthorRepositoryJpa.class})
class AuthorRepositoryJpaTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    AuthorRepositoryJpa jpa;

    @Test
    void shouldSaveOrUpdateAuthor() {
    }

    @Test
    void shouldDeleteByUid() {
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {
        System.out.println(jpa.findAll());
    }

    @DisplayName("...должен связать авторов с книгами")
    @Test
    void shouldInsertAuthorsByBookUid() {
    }

    @Test
    void shouldEditAuthorsByBookUid() {
    }

    @Test
    void shouldDeleteAuthorsByBookUid() {
    }

    @Test
    void shouldFindAuthorsByBookUid() {

    }

    @DisplayName("... должен вернуть количество записей справочника")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(jpa.count()).isGreaterThan(0);
    }
}