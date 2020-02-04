package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findBooksByGenresId(String genreUid);

    List<Book> findBooksByAuthorsId(String authorUid);

    List<Book> findBooksByCommentsCommentatorId(String id);
}
