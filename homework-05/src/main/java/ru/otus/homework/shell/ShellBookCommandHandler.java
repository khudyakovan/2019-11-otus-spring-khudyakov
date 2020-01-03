package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.GenreDto;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
public class ShellBookCommandHandler {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ShellHelper shellHelper;
    private final InputReader inputReader;

    @Autowired
    public ShellBookCommandHandler(BookService bookService,
                                   AuthorService authorService,
                                   GenreService genreService,
                                   ShellHelper shellHelper,
                                   InputReader inputReader
                                   ) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.shellHelper = shellHelper;
        this.inputReader = inputReader;
    }

    @ShellMethod("Получить список книг в библиотеке")
    public void showBooks() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("uid", "Uid");
        headers.put("title", "Title");
        headers.put("isbn", "ISBN");
        headers.put("publicationYear", "Publication Year");
        headers.put("authors", "Authors");
        headers.put("genres", "Genres");
        shellHelper.render(bookService.getAll(), headers);
    }

    @ShellMethod("Добавить новую книгу")
    public void addBook() {
        BookDto bookDto = this.bookWizard("prompt.add.book", new BookDto(), false);
        if (bookDto.getUid() == -1) {
            shellHelper.printWarningTranslated("warning.termination");
            return;
        }
        bookService.insert(bookDto);
        shellHelper.printSuccessTranslated("info.record.added.successfully");
    }

    @ShellMethod("Редактировать книгу")
    public void editBook() {
        BookDto bookDto = this.bookWizard("prompt.edit.book", new BookDto(), true);
        if (bookDto.getUid() == -1) {
            shellHelper.printWarningTranslated("warning.termination");
            return;
        }
        bookService.edit(bookDto);
        shellHelper.printSuccessTranslated("info.record.edited.successfully");
    }

    @ShellMethod("Удалить книгу")
    public void deleteBook() {
        String userInput = inputReader.promptTranslated("warning.delete.book", "N");
        if (!"Y".equals(userInput.toUpperCase())) {
            return;
        }
        BookDto bookDto = this.getBookFromList();
        bookService.deleteByUid(bookDto.getUid());
        shellHelper.printWarningTranslated("info.record.deleted.successfully");
    }

    private BookDto bookWizard(String prompt, BookDto bookDto, boolean edit) {
        String userInput = inputReader.promptTranslated(prompt,"");
        if ("Q".equals(userInput.toUpperCase())) {
            bookDto.setUid(-1);
            return bookDto;
        }

        if (edit) {
            inputReader.promptTranslated("prompt.list.books","");
            //Выбрать книги из справочника
            bookDto = this.getBookFromList();
        }

        inputReader.promptTranslated("prompt.list.genres","");
        //Выбрать жанры книги из справочника
        List<GenreDto> genres = this.getGenreOfBookFromTheList(new ArrayList<>());
        bookDto.setGenres(genres);

        inputReader.promptTranslated("prompt.list.authors","");
        //Выбрать автора из справочника
        List<AuthorDto> authors = shellHelper.getAuthorsFromList(new ArrayList<>(), authorService);
        bookDto.setAuthors(authors);

        // Добавить заголовок
        do {
            String title = inputReader.promptTranslated("prompt.enter.book.title", bookDto.getTitle());
            if (StringUtils.hasText(title)) {
                bookDto.setTitle(title);
            } else {
                shellHelper.printErrorTranslated("error.empty.title");
            }
        } while (bookDto.getTitle() == null);

        // Добавить ISBN
        String isbn = inputReader.promptTranslated("prompt.enter.isbn",String.valueOf(bookDto.getIsbn()));
        if (shellHelper.isStringLong(isbn)) {
            bookDto.setIsbn(Long.parseLong(isbn));
        } else {
            bookDto.setIsbn(-1);
        }
        //Добавить год издания
        String publicationYear = inputReader.promptTranslated("prompt.enter.publishing.year",
                String.valueOf(bookDto.getPublicationYear()));
        if (shellHelper.isStringLong(publicationYear)) {
            bookDto.setPublicationYear(Integer.parseInt(publicationYear));
        } else {
            bookDto.setPublicationYear(-1);
        }
        return bookDto;
    }

    private BookDto getBookFromList() {
        this.showBooks();
        Long uid = null;
        do {
            String userInput = inputReader.promptTranslated("prompt.choose.book","");
            if (shellHelper.isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printErrorTranslated("error.incorrect.input");
            }
        } while (uid == null);

        return bookService.getByUid(uid);
    }

    private List<GenreDto> getGenreOfBookFromTheList(List<GenreDto> genreDtos) {
        //Вывод справочника жанров для выбора
        if (genreDtos != null && genreDtos.isEmpty()) {
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("uid", "Uid");
            headers.put("name", "Genre");
            shellHelper.render(genreService.getAll(), headers);
        }
        Long uid = null;
        do {
            String userInput = inputReader.promptTranslated("prompt.choose.genre","");
            if (shellHelper.isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printErrorTranslated("error.incorrect.input");
            }
        }
        while (uid == null);
        genreDtos.add(genreService.getByUid(uid));
        String userInput = inputReader.promptTranslated("prompt.additional.genre","");
        if (("Y").equals(userInput.toUpperCase())) {
            this.getGenreOfBookFromTheList(genreDtos);
        }
        return genreDtos;
    }
}
