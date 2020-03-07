package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Jpa для работы с комментаторами...")
@DataJpaTest
@Import(ApplicationProperties.class)
class UserRepositoryJpaTest {

//    @Autowired
//    TestEntityManager em;

    @Autowired
    UserRepository userRepository;

    private static final long UID = 1L;
    private static final String NEW_LOGIN = "login";
    private static final String LOGIN = "ac1";
    private static final String PASSWORD = "password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";

    @DisplayName("...должен сохранить запись")
    @Test
    void shouldSaveAuthor() {
        long countBefore = userRepository.count();
        User user = new User(NEW_LOGIN,
                PASSWORD,
                FIRST_NAME,
                LAST_NAME);
        userRepository.save(user);
        assertThat(userRepository.count()).isEqualTo(countBefore + 1);
    }

    @DisplayName("...должен отредактировать запись")
    @Test
    void shouldEditAuthor(){
        User user = userRepository.findById(UID).orElse(null);
        user.setUsername(NEW_LOGIN);
        user.setPassword(NEW_PASSWORD);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        userRepository.save(user);
        user = userRepository.findById(UID).orElse(null);
        assertThat(user.getPassword().equals(NEW_PASSWORD));
    }

    @DisplayName("...должен удалить запись")
    @Test
    void shouldDeleteByUid() {
        List<User> users = userRepository.findAll();
        long countBefore = users.size();

        userRepository.deleteById(UID);
        assertThat(userRepository.count()).isEqualTo(countBefore-1);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {

        assertThat(userRepository.findById(UID)).isNotNull();
    }

    @DisplayName("...должен найти запись по логину")
    @Test
    void shouldFindByLogin() {
        assertThat(userRepository.findByUsername(LOGIN)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {

        assertThat(userRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

}
