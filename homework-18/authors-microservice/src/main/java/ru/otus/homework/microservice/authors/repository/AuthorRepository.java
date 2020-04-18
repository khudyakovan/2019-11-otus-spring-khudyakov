package ru.otus.homework.microservice.authors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.microservice.authors.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

}
