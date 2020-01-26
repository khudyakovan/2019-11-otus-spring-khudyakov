package ru.otus.homework.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework.AbstractRepositoryTest;
import ru.otus.homework.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Jpa для работы с авторами...")
class AuthorRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    TestEntityManager testEntityManager;

    private static final long BOOK_UID = 15;
    private static final long NEW_AUTHOR_UID = 0;
    private static final long TEST_AUTHOR_1 = 31;
    private static final long TEST_AUTHOR_2 = 32;
    private static final long TEST_AUTHOR_UID = 39;
    private static final String TEST_AUTHOR_NAME = "Test author full name";

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {
        val author = testEntityManager.find(Author.class, 31);
        assertThat(authorRepository.findById(TEST_AUTHOR_1)).isNotEmpty().isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {
        val authors = authorRepository.findAll();
        assertThat(authorRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }
}