package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Author;

public interface AuthorRepository extends MongoRepository<Author, Long>, AuthorRepositoryCustom{

    //List<Author> findAuthorsByBookUid(long bookUid);
}
