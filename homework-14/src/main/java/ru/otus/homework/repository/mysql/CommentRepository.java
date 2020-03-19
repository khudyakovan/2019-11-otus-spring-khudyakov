package ru.otus.homework.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.entity.mysql.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join c.book b join fetch c.user where b.uid = :bookUid")
    List<Comment> findCommentsByBookUid(@Param("bookUid") long bookUid);

    @Query("select c from Comment c join c.book b join fetch c.user u where u.id = :userId")
    List<Comment> findCommentByUserId(@Param("userId") long userId);
}
