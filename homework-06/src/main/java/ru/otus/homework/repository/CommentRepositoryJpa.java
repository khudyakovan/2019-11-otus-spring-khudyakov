package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getUid() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteByUid(long uid) {
        Query query = em.createQuery("delete from Comment c where c.uid = :uid");
        query.setParameter("uid", uid);
        query.executeUpdate();
    }

    @Override
    public Optional<Comment> findByUid(long uid) {

        return Optional.ofNullable(em.find(Comment.class, uid));
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("select count(c) from Comment c", Long.class).getSingleResult();
    }

    @Override
    public List<Comment> findCommentsByBookUid(long bookUid) {
        return em.createQuery("select c from Comment c join c.books b where b.uid = :bookUid")
                .setParameter("bookUid", bookUid)
                .getResultList();
    }
}
