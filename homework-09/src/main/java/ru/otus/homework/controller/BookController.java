package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.homework.entity.Book;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private final BookService bookService;
    @Autowired
    private final AuthorService authorService;
    @Autowired
    private final GenreService genreService;
    @Autowired
    private final CommentService commentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"/"})
    public String showIndexPage(Model model) {
        List<Book> books = bookService.findAll();
        //pageAttributes.setCommonAttributes(model);
        //pageAttributes.setBookAttributes(model);
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping(value = "/books/{uid}")
    public String showBookDetailsPage(@PathVariable("uid") long uid, Model model) {
        //pageAttributes.setCommonAttributes(model);
        //pageAttributes.setBookAttributes(model);
        //pageAttributes.setBookDetailsAttributes(model);
        Book book = bookService.findByUid(uid);
        List<Comment> comments = commentService.findCommentsByBookUid(book.getUid());
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "book-details";
    }

    @GetMapping("/books/add")
    public String showAddBook(Model model){
        //pageAttributes.setCommonAttributes(model);
        //pageAttributes.setBookAttributes(model);

        Book book = new Book();
        model.addAttribute("add", true);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());

        return "book-add";
    }

    @PostMapping("/books/add")
    public String addBook(Model model, @ModelAttribute Book book){
        Book newBook = bookService.save(book);
        return "redirect:/books/" + newBook.getUid();
    }

    @GetMapping(value = {"/books/{uid}/edit"})
    public String showEditBook(Model model, @PathVariable long uid){
        //pageAttributes.setCommonAttributes(model);
        //pageAttributes.setBookAttributes(model);

        Book book = bookService.findByUid(uid);
        book.getAuthors();
        book.getGenres();
        model.addAttribute("add", false);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book-edit";
    }

    @PostMapping(value = {"/books/{uid}/edit"})
    public String updateBook(Model model,
                                @PathVariable long uid,
                                @ModelAttribute("book") Book book) {
        try {
            book.setUid(uid);
            bookService.save(book);
            return "redirect:/books/" + book.getUid();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "book-edit";
        }
    }

    @GetMapping(value = {"/books/{uid}/delete"})
    public String deleteBook(Model model, @PathVariable long uid){
        Book book = bookService.findByUid(uid);
        List<Comment> comments = commentService.findCommentsByBookUid(book.getUid());
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        model.addAttribute("allowDelete", true);
        return "book-details";
    }

    @PostMapping(value = {"/books/{uid}/delete"})
    public String deleteBookByUid(
            Model model, @PathVariable long uid) {
        try {
            bookService.deleteByUid(uid);
            return "redirect:/";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "book-details";
        }
    }
}
