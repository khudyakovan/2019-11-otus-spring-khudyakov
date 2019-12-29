package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.LinkedHashMap;

@ShellComponent
public class ShellAuthorCommandHandler {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ShellHelper shellHelper;
    private final InputReader inputReader;

    @Autowired
    public ShellAuthorCommandHandler(BookService bookService,
                                     AuthorService authorService,
                                     GenreService genreService,
                                     ShellHelper shellHelper,
                                     InputReader inputReader) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.shellHelper = shellHelper;
        this.inputReader = inputReader;
    }

    @ShellMethod("Получить список авторов книг в библиотеке")
    public void getAuthors() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("uid", "Uid");
        headers.put("fullName", "Author's Full Name");
        headers.put("books", "Books Written By The Author");
        shellHelper.render(authorService.getAll(), headers);
    }

//    @ShellMethod("Добавить нового автора")
//    public void addAuthor() {
//
//    }

//    @ShellMethod("Редактировать автора")
//    public void editAuthor() {
//
//    }

//    @ShellMethod("Удалить автора")
//    public void deleteAuthor() {
//
//    }
}
