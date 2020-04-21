package ru.otus.homework.microservices.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.microservices.books.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b join b.genres g where g.uid = :genreUid")
    List<Book> findBooksByGenreUid(@Param("genreUid") long genreUid);

    @Query("select b from Book b join b.authors a where a.uid = :authorUid")
    List<Book> findBooksByAuthorUid(@Param("authorUid") long authorUid);
}
