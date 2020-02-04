package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework.AbstractRepositoryTest;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Commentator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментаторами...")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentatorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    CommentatorRepository commentatorRepository;
    @Autowired
    BookRepository bookRepository;

    private static final String UID = "1";
    private static final String NEW_LOGIN = "login";
    private static final String LOGIN = "ac1";
    private static final String PASSWORD = "password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";

    @DisplayName("...должен сохранить запись")
    @Test
    @Order(1)
    void shouldSave() {
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
    @Order(2)
    void shouldEdit(){
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
    @Order(6)
    void shouldDeleteByUid() {
        List<Commentator> commentators = commentatorRepository.findAll();
        List<Book> books = bookRepository.findAll();
        val commentsBefore = books.get(0).getComments().size();
        val countBefore = commentators.size();

        commentatorRepository.deleteById(UID);
        assertThat(commentatorRepository.count()).isEqualTo(countBefore-1);
        //Проверка каскадного удаления
        //Поскольку комментатор у нас только один, то должны удалиться все комментарии во всех книгах
        assertThat(commentsBefore).isGreaterThan(0);

        books = bookRepository.findAll();
        books.forEach(book -> {
            assertThat(book.getComments().size()).isEqualTo(0);
        });
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    @Order(3)
    void shouldFindByUid() {

        assertThat(commentatorRepository.findById(UID)).isNotNull();
    }

    @DisplayName("...должен найти запись по логину")
    @Test
    @Order(4)
    void shouldFindByLogin() {
        assertThat(commentatorRepository.findByLogin(LOGIN)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    @Order(5)
    void shouldFindAll() {
        assertThat(commentatorRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }
}