package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.homework.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {

    Flux<Book> findBooksByGenresId(String genreUid);

    Flux<Book> findBooksByAuthorsId(String authorUid);

    Flux<Book> findBooksByCommentsCommentatorId(String id);
}
