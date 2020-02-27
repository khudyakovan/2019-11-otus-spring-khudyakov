package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.CommentDto;
import ru.otus.homework.dto.CommentatorDto;
import ru.otus.homework.entity.Book;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private final BookService bookService;
    @Autowired
    private final CommentService commentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"/api/v1/", "/api/v1/books"})
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
        List<CommentDto> comments = new ArrayList<>();
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);

        List<Comment> c =  commentService.findCommentsByBookUid(book.getUid());
        c.forEach(comment -> {
            CommentDto commentDto = new CommentDto();
            CommentatorDto commentatorDto = new CommentatorDto();
            BeanUtils.copyProperties(comment, commentDto);
            BeanUtils.copyProperties(comment.getCommentator(), commentatorDto);
            commentDto.setCommentator(commentatorDto);
            comments.add(commentDto);
        });

        bookDto.setComments(comments);
        return bookDto;
    }
}
