package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.entity.Book;
import ru.otus.homework.service.BookService;

import java.util.LinkedHashMap;

@ShellComponent
@RequiredArgsConstructor
public class ShellBookCommandHandler {

    private final ShellHelper shellHelper;
    private final BookService bookService;
    private final InputReader inputReader;

    @ShellMethod("Получить список книг в библиотеке")
    public void showBooks() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("uid", "Uid");
        headers.put("title", "Title");
        headers.put("isbn", "ISBN");
        headers.put("publicationYear", "Publication Year");
        headers.put("authors", "Authors");
        headers.put("genres", "Genres");
        shellHelper.render(bookService.findAll(), headers);
    }

    @ShellMethod("Получить детали книги")
    public void showBookDetails(){
        Book book = this.getBookFromList();
        System.out.println(book.getAuthors());
        System.out.println(book.getGenres());
    }

    private Book getBookFromList() {
        this.showBooks();
        Long uid = null;
        do {
            String userInput = inputReader.promptTranslated("prompt.choose.book","");
            if (NumberUtils.isParsable(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printErrorTranslated("error.incorrect.input");
            }
        } while (uid == null);
        return bookService.findByUid(uid);
    }
}
