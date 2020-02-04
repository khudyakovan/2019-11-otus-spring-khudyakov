package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String>, AuthorRepositoryCustom{

}
