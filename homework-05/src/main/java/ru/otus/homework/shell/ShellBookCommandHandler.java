package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
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
    final String INFO_ADD_BOOK = "\nДобавляется новая книга\n";
    final String INFO_RECORD_ADDED_SUCCESSFULLY = "\nЗапись успешно добавлена";
    final String PROMPT_ENTER_BOOK_TITLE = "Введите название книги";
    final String WARNING_EMPTY_TITLE = "Название книги не может быть пустым";
    final String PROMPT_ENTER_ISBN = "Введите ISBN";
    final String PROMPT_ENTER_PUBLISHING_YEAR = "Введите год издания";
    final String PROMPT_LIST_GENRES = "Справочник жанров";
    final String PROMPT_CHOOSE_GENRE = "Выберите жанр книги";
    final String WARNING_GENRE_INCORRECT_INPUT = "Некорректный ввод. Введите Uid жанра";
    final String PROMPT_ADDITIONAL_GENRE = "Выбрать еще один жанр? Y-да, любая другая клавиша - нет";

    @Autowired
    public ShellBookCommandHandler(BookService bookService,
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

    @ShellMethod("Получить список книг в библиотеке")
    public void getBooks() {
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
        Book book = new Book();
        //Выбрать жанры книги из справочника
        List<Genre> genres = this.getGenreOfBookFromTheList(new ArrayList<Genre>());
        book.setGenres(genres);
        shellHelper.printInfo(INFO_ADD_BOOK);
        // Добавить заголовок
        do {
            String fullName = inputReader.prompt(PROMPT_ENTER_BOOK_TITLE);
            if (StringUtils.hasText(fullName)) {
                book.setTitle(fullName);
            } else {
                shellHelper.printWarning(WARNING_EMPTY_TITLE);
            }
        } while (book.getTitle() == null);

        // Добавить ISBN
        String isbn = inputReader.prompt(PROMPT_ENTER_ISBN);
        if (isStringLong(isbn)) {
            book.setIsbn(Long.parseLong(isbn));
        } else {
            book.setIsbn(-1);
        }
        //Добавить год издания
        String publicationYear = inputReader.prompt(PROMPT_ENTER_PUBLISHING_YEAR);
        if (isStringLong(publicationYear)) {
            book.setPublicationYear(Integer.parseInt(publicationYear));
        } else {
            book.setPublicationYear(-1);
        }

        bookService.insert(book);
        shellHelper.printSuccess(INFO_RECORD_ADDED_SUCCESSFULLY);
    }

    @ShellMethod("Редактировать книгу")
    public void editBook() {

    }

    @ShellMethod("Удалить книгу")
    public void deleteBook() {

    }

    private List<Genre> getGenreOfBookFromTheList(List<Genre> genres) {
        //Вывод справочника жанров для выбора
        if (genres.size() == 0){
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("uid", "Uid");
            headers.put("name", "Genre");
            headers.put("books", "Books This Genre");
            shellHelper.render(genreService.getAll(), headers);
        }
        Long uid = null;
        do {
            String userInput = inputReader.prompt(PROMPT_CHOOSE_GENRE, "");
            if (isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printWarning(WARNING_GENRE_INCORRECT_INPUT);
            }
        }
        while (uid == null);
        genres.add(genreService.getByUid(uid));
        String userInput = inputReader.prompt(PROMPT_ADDITIONAL_GENRE, "");
        if(("Y").equals(userInput.toUpperCase())){
            this.getGenreOfBookFromTheList(genres);
        }
        return genres;
    }

    private boolean isStringLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
