package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.LinkedHashMap;

@ShellComponent
public class ShellGenresCommandHandler {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ShellHelper shellHelper;
    private final InputReader inputReader;

    @Autowired
    public ShellGenresCommandHandler(BookService bookService,
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

    @ShellMethod("Получить список жанров и книг этого жанра")
    public void showGenres() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("uid", "Uid");
        headers.put("name", "Genre");
        headers.put("books", "Books This Genre");
        shellHelper.render(genreService.getAll(), headers);
    }

//    @ShellMethod("Добавить новый жанр")
//    public void addGenre() {
//
//    }
//
//    @ShellMethod("Редактировать жанр")
//    public void editGenre() {
//
//    }

//    @ShellMethod("Удалить жанр")
//    public void deleteGenre() {
//
//    }
}
