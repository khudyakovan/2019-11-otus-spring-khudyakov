package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {

    List<Book> findBooksByGenresUid(long genreUid);

    List<Book> findBooksByAuthorsUid(long authorUid);
}
