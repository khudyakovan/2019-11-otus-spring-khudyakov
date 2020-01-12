package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.entity.Commentator;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Jpa для работы с комментариями...")
@DataJpaTest
@Import({CommentRepositoryJpa.class, CommentatorRepositoryJpa.class})
class CommentRepositoryJpaTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    CommentRepositoryJpa commentRepository;

    @Autowired
    CommentatorRepositoryJpa commentatorRepository;

    private final long COMMENTATOR_UID = 1;
    private final long COMMENT_UID = 1;
    private final String COMMENT_TEXT = "Comment Text";

    @DisplayName("...должен сохранить запись")
    @Test
    void shouldSaveAuthor() {
        long countBefore = commentRepository.count();
        Commentator commentator = commentatorRepository.findByUid(COMMENTATOR_UID).get();
        Comment comment = new Comment(commentator, COMMENT_TEXT, new Date());
        commentRepository.save(comment);
        assertThat(commentRepository.count()).isEqualTo(countBefore + 1);
    }

    @DisplayName("...должен отредактировать запись")
    @Test
    void shouldEditAuthor() {
        Comment comment = commentRepository.findByUid(COMMENT_UID).get();
        comment.setCommentText(COMMENT_TEXT);
        comment = commentRepository.save(comment);
        assertThat(comment.getCommentText().equals(COMMENT_TEXT));
    }

    @DisplayName("...должен удалить запись")
    @Test
    void shouldDeleteByUid() {
        List<Comment> comments = commentRepository.findAll();
        long countBefore = comments.size();
        commentRepository.deleteByUid(comments.get(0).getUid());
        assertThat(commentRepository.count()).isEqualTo(countBefore - 1);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {
        assertThat(commentRepository.findByUid(COMMENT_UID)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {

        assertThat(commentRepository.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

}