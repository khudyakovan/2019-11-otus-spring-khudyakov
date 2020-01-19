package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.entity.Commentator;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Jpa для работы с комментариями...")
@DataJpaTest
@Import(ApplicationProperties.class)
class CommentRepositoryJpaTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentatorRepository commentatorRepository;

    @Autowired
    BookRepository bookRepository;

    private static final long COMMENTATOR_UID = 1;
    private static final long COMMENT_UID = 1;
    private static final String COMMENT_TEXT = "Comment Text";
    private static final long BOOK_UID = 14;

    @DisplayName("...должен сохранить запись")
    @Test
    void shouldSaveAuthor() {
        long countBefore = commentRepository.count();
        Commentator commentator = commentatorRepository.findById(COMMENTATOR_UID).orElse(null);
        Comment comment = new Comment(commentator, COMMENT_TEXT, new Date());
        comment.setBook(bookRepository.findById(BOOK_UID).orElse(null));
        commentRepository.save(comment);
        assertThat(commentRepository.count()).isEqualTo(countBefore + 1);
    }

    @DisplayName("...должен отредактировать запись")
    @Test
    void shouldEditAuthor() {
        Comment comment = commentRepository.findById(COMMENT_UID).orElse(null);
        comment.setCommentText(COMMENT_TEXT);
        comment = commentRepository.save(comment);
        assertThat(comment.getCommentText().equals(COMMENT_TEXT));
    }

    @DisplayName("...должен удалить запись")
    @Test
    void shouldDeleteByUid() {
        List<Comment> comments = commentRepository.findAll();
        long countBefore = comments.size();
        commentRepository.deleteById(comments.get(0).getUid());
        assertThat(commentRepository.count()).isEqualTo(countBefore - 1);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {

        Optional<Comment> comment = commentRepository.findById(COMMENT_UID);
        assertThat(comment).isNotNull();
        assertThat(comment.orElse(null).getBook()).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {
        assertThat(commentRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindCommentsByBookUid(){
        List<Comment> comments = commentRepository.findCommentsByBookUid(BOOK_UID);
        assertThat(comments).isNotNull();
        assertThat(comments.get(0).getBook()).isNotNull();
        assertThat(comments.get(0).getCommentator()).isNotNull();
    }

}