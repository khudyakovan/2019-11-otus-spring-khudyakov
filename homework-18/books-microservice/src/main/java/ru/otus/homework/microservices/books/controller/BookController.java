package ru.otus.homework.microservices.books.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.microservices.books.dto.BookDto;
import ru.otus.homework.microservices.books.dto.CommentDto;
import ru.otus.homework.microservices.books.entity.Book;
import ru.otus.homework.microservices.books.service.BookService;
import ru.otus.homework.microservices.books.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private final BookService bookService;
    @Autowired
    private final CommentService commentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"/api/v1/books"})
    public List<Book> getBookList() {
        return bookService.findAll();
    }

    @GetMapping(path = "/api/v1/books/{uid}")
    public BookDto getBookDetails(@PathVariable("uid") long uid) {
        Book book = bookService.findByUid(uid);
        return this.bookMapper(book);
    }

    @PostMapping("/api/v1/books")
    public Book addBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping(value = {"/api/v1/books"})
    public Book updateBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @DeleteMapping(value = {"/api/v1/books/{uid}"})
    public void deleteBookByUid(@PathVariable long uid) {
        bookService.deleteByUid(uid);
    }


    private BookDto bookMapper(Book book){
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        List<CommentDto> comments =  commentService.findCommentsByBookId(book.getUid());
        bookDto.setComments(comments);
        return bookDto;
    }
}
