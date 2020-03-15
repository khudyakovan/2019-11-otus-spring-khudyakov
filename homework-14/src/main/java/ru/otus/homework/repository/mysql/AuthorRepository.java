package ru.otus.homework.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.entity.mysql.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>{

    @Query("select b.authors from Book b where b.uid = :bookUid")
    List<Author> findAuthorsByBookUid(@Param("bookUid") long bookUid);
}
