package ru.otus.homework.microservices.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.microservices.books.entity.Author;
import ru.otus.homework.microservices.books.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/v1/authors")
    public List<Author> getAuthors() {
        return authorService.findAll();
    }
}
