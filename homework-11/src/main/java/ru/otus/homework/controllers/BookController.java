package ru.otus.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Book;
import ru.otus.homework.repositories.BookRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private final BookRepository bookRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"/api/v1/books"})
    public Flux<Book> getBookList() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/api/v1/books/{uid}")
    public Mono<Book> getBookDetails(@PathVariable("uid") String uid) {
        return bookRepository.findById(uid);
    }

    @PostMapping("/api/v1/books")
    public Mono<Book> addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping(value = {"/api/v1/books"})
    public Mono<Book> updateBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping(value = {"/api/v1/books/{uid}"})
    public void deleteBookByUid(@PathVariable("uid") String uid) {
        bookRepository.deleteById(uid).subscribe();
    }
}
