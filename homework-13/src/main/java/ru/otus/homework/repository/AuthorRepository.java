package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.entity.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>, AuthorRepositoryCustom{

    @Query("select b.authors from Book b where b.uid = :bookUid")
    List<Author> findAuthorsByBookUid(@Param("bookUid") long bookUid);
}
