package ru.otus.homework.microservices.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.microservices.comments.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join c.book b join fetch c.commentator where b.uid = :bookUid")
    List<Comment> findCommentsByBookUid(@Param("bookUid") long bookUid);
}
