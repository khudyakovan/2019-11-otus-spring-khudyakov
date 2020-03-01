package ru.otus.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.models.Author;
import ru.otus.homework.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorRepository authorRepository;

    @GetMapping("/api/v1/authors")
    public Flux<Author> getAuthors() {
        return authorRepository.findAll();
    }
}
