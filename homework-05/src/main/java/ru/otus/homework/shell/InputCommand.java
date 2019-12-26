package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import ru.otus.homework.domain.Book;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
public class InputCommand {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ShellHelper shellHelper;

    @Autowired
    public InputCommand(BookService bookService, AuthorService authorService, GenreService genreService, ShellHelper shellHelper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.shellHelper = shellHelper;
    }

    @ShellMethod("Получить список книг в библиотеке")
    public void getBooks() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("title", "Title");
        headers.put("isbn", "ISBN");
        headers.put("publicationYear", "Publication Year");
        headers.put("authors", "Authors");
        headers.put("genres", "Genres");

        TableModel model = new BeanListTableModel<>(bookService.getAll(), headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.oldschool);
        tableBuilder.addHeaderBorder(BorderStyle.oldschool);
        shellHelper.print(tableBuilder.build().render(180), null);
    }

    @ShellMethod("Получить список авторов книг в библиотеке")
    public void getAuthors(){
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("fullName", "Full Name");
        headers.put("penName", "Pen Name");
        headers.put("books", "Books Written By The Author");

        TableModel model = new BeanListTableModel<>(authorService.getAll(), headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.oldschool);
        tableBuilder.addHeaderBorder(BorderStyle.oldschool);
        shellHelper.print(tableBuilder.build().render(180), null);
    }
}
