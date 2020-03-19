package ru.otus.homework.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.entity.mysql.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b join b.genres g where g.id = :genreUid")
    List<Book> findBooksByGenreId(@Param("genreUid") long genreUid);

    @Query("select b from Book b join b.authors a where a.uid = :authorUid")
    List<Book> findBooksByAuthorUid(@Param("authorUid") long authorUid);
}
