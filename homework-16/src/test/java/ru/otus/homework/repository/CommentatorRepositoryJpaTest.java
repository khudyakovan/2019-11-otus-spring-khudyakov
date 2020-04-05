package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Commentator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Jpa для работы с комментаторами...")
@DataJpaTest
@Import(ApplicationProperties.class)
class CommentatorRepositoryJpaTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    CommentatorRepository commentatorRepository;

    private static final long UID = 1;
    private static final String NEW_LOGIN = "login";
    private static final String LOGIN = "ac1";
    private static final String PASSWORD = "password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";

    @DisplayName("...должен сохранить запись")
    @Test
    void shouldSaveAuthor() {
        long countBefore = commentatorRepository.count();
        Commentator commentator = new Commentator(NEW_LOGIN,
                PASSWORD,
                FIRST_NAME,
                LAST_NAME);
        commentatorRepository.save(commentator);
        assertThat(commentatorRepository.count()).isEqualTo(countBefore + 1);
    }

    @DisplayName("...должен отредактировать запись")
    @Test
    void shouldEditAuthor(){
        Commentator commentator = commentatorRepository.findById(UID).orElse(null);
        commentator.setLogin(NEW_LOGIN);
        commentator.setPassword(NEW_PASSWORD);
        commentator.setFirstName(FIRST_NAME);
        commentator.setLastName(LAST_NAME);
        commentatorRepository.save(commentator);
        commentator = commentatorRepository.findById(UID).orElse(null);
        assertThat(commentator.getPassword().equals(NEW_PASSWORD));
    }

    @DisplayName("...должен удалить запись")
    @Test
    void shouldDeleteByUid() {
        List<Commentator> commentators = commentatorRepository.findAll();
        long countBefore = commentators.size();

        commentatorRepository.deleteById(UID);
        assertThat(commentatorRepository.count()).isEqualTo(countBefore-1);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {

        assertThat(commentatorRepository.findById(UID)).isNotNull();
    }

    @DisplayName("...должен найти запись по логину")
    @Test
    void shouldFindByLogin() {
        assertThat(commentatorRepository.findByLogin(LOGIN)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {

        assertThat(commentatorRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

}