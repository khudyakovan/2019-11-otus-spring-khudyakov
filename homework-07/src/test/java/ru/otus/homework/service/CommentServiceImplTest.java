package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.entity.Commentator;
import ru.otus.homework.repository.CommentRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с жанрами...")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
@Import({CommentServiceImpl.class})
class CommentServiceImplTest {

    @MockBean
    CommentRepository commentRepository;

    @Autowired
    CommentServiceImpl commentService;

    private static final String COMMENTATOR_LOGIN = "anonymous";
    private static final String COMMENTATOR_PASSWORD = "strongpassword";
    private static final long COMMENT_UID = 1;
    private static final long RECORD_COUNT = 10;

    @BeforeEach
    void setUp() {
        List<Comment> comments = new ArrayList<>();
        Commentator commentator = new Commentator(COMMENTATOR_LOGIN, COMMENTATOR_PASSWORD, "", "");
        Comment comment = new Comment( commentator, "", new Date());
        comments.add(comment);
        given(commentRepository.findAll()).willReturn(comments);
        given(commentRepository.findById(anyLong())).willReturn(Optional.ofNullable(comment));
        given(commentRepository.save(any())).willReturn(comment);
        given(commentRepository.count()).willReturn(RECORD_COUNT);
    }

    @DisplayName("...должен записать объект")
    @Test
    void shouldSave() {
        Comment comment = new Comment("", new Date());
        comment = commentService.save(comment);
        assertThat(comment.getCommentator()).isNotNull();
        assertThat(comment.getCommentator().getLogin()).isEqualTo(COMMENTATOR_LOGIN);
    }

    @DisplayName("...должен найти запись по ее Uid")
    @Test
    void shouldFindByUid() {

        assertThat(commentService.findByUid(COMMENT_UID)).isNotNull();
    }

    @DisplayName("...должен извлечь все записи")
    @Test
    void shouldFindAll() {

        assertThat(commentService.findAll()).isNotNull().hasSizeGreaterThan(0);
    }

    @DisplayName("... должен вернуть количество записей справочника > 0")
    @Test
    void shouldGetExpectedCountOfBook() {
        assertThat(commentService.count()).isGreaterThan(0);
    }

}