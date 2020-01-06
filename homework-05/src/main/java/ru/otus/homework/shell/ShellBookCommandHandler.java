package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.GenreDto;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;
import ru.otus.homework.service.UtilityService;

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
    private final UtilityService utilityService;

    @Autowired
    public ShellBookCommandHandler(BookService bookService,
                                   AuthorService authorService,
                                   GenreService genreService,
                                   ShellHelper shellHelper,
                                   InputReader inputReader,
                                   UtilityService utilityService
                                   ) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.shellHelper = shellHelper;
        this.inputReader = inputReader;
        this.utilityService = utilityService;
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
        Book book = this.bookWizard("prompt.add.book", new Book(), false);
        if (book.getUid() == -1) {
            shellHelper.printWarningTranslated("warning.termination");
            return;
        }
        bookService.insert(book);
        shellHelper.printSuccessTranslated("info.record.added.successfully");
    }

    @ShellMethod("Редактировать книгу")
    public void editBook() {
        Book book = this.bookWizard("prompt.edit.book", new Book(), true);
        if (book.getUid() == -1) {
            shellHelper.printWarningTranslated("warning.termination");
            return;
        }
        bookService.edit(book);
        shellHelper.printSuccessTranslated("info.record.edited.successfully");
    }

    @ShellMethod("Удалить книгу")
    public void deleteBook() {
        String userInput = inputReader.promptTranslated("warning.delete.book", "N");
        if (!"Y".equals(userInput.toUpperCase())) {
            return;
        }
        Book book = this.getBookFromList();
        bookService.deleteByUid(book.getUid());
        shellHelper.printWarningTranslated("info.record.deleted.successfully");
    }

    private Book bookWizard(String prompt, Book book, boolean edit) {
        String userInput = inputReader.promptTranslated(prompt,"");
        if ("Q".equals(userInput.toUpperCase())) {
            book.setUid(-1);
            return book;
        }

        if (edit) {
            inputReader.promptTranslated("prompt.list.books","");
            //Выбрать книги из справочника
            book = this.getBookFromList();
        }

        inputReader.promptTranslated("prompt.list.genres","");
        //Выбрать жанры книги из справочника
        List<GenreDto> genres = this.getGenreOfBookFromTheList(new ArrayList<>());
        book.setGenres(utilityService.convertToGenreDomain(genres));

        inputReader.promptTranslated("prompt.list.authors","");
        //Выбрать автора из справочника
        List<AuthorDto> authors = shellHelper.getAuthorsFromList(new ArrayList<>(), authorService);
        book.setAuthors(utilityService.convertToAuthorDomain(authors));

        // Добавить заголовок
        do {
            String title = inputReader.promptTranslated("prompt.enter.book.title", book.getTitle());
            if (StringUtils.hasText(title)) {
                book.setTitle(title);
            } else {
                shellHelper.printErrorTranslated("error.empty.title");
            }
        } while (book.getTitle() == null);

        // Добавить ISBN
        String isbn = inputReader.promptTranslated("prompt.enter.isbn",String.valueOf(book.getIsbn()));
        if (shellHelper.isStringLong(isbn)) {
            book.setIsbn(Long.parseLong(isbn));
        } else {
            book.setIsbn(-1);
        }
        //Добавить год издания
        String publicationYear = inputReader.promptTranslated("prompt.enter.publishing.year",
                String.valueOf(book.getPublicationYear()));
        if (shellHelper.isStringLong(publicationYear)) {
            book.setPublicationYear(Integer.parseInt(publicationYear));
        } else {
            book.setPublicationYear(-1);
        }
        return book;
    }

    private Book getBookFromList() {
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
