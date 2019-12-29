package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;
import ru.otus.homework.domain.Author;
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
    final String PROMPT_ADD_BOOK = "\nДобавляется новая книга.\nЕсли жанр книги и авторы книги не содержатся в " +
            "справочниках, то прервите добавление с помощью клавиши Q и сначала добавьте их." +
            "После чего повторите добавление книги";
    final String PROMPT_EDIT_BOOK = "\nРедактирование книги.\nЕсли книга, жанр книги и авторы книги не содержатся в " +
            "справочниках, то прервите добавление с помощью клавиши Q и сначала добавьте их." +
            "После чего повторите редактирование книги";
    final String PROMPT_LIST_BOOKS = "Нажмите любую клавишу для выборка книги из списка";
    final String PROMPT_CHOOSE_BOOK = "Выберите Uid книги из справочника выше";

    final String INFO_RECORD_ADDED_SUCCESSFULLY = "\nЗапись успешно добавлена";
    final String PROMPT_ENTER_BOOK_TITLE = "Введите название книги";
    final String PROMPT_ENTER_ISBN = "Введите ISBN";
    final String PROMPT_ENTER_PUBLISHING_YEAR = "Введите год издания";

    final String PROMPT_LIST_GENRES = "Нажмите любую клавишу для добавления жанров из справочника";
    final String PROMPT_CHOOSE_GENRE = "Выберите Uid жанра книги из справочника выше";
    final String PROMPT_ADDITIONAL_GENRE = "Выбрать еще один жанр? Y-да, любая другая клавиша - нет";

    final String PROMPT_LIST_AUTHORS = "Нажмите любую клавишу для добавления авторов из справочника";
    final String PROMPT_CHOOSE_AUTHOR = "Выберите Uid автора книги из справочника выше";
    final String PROMPT_ADDITIONAL_AUTHOR = "Добавить соавтора? Y-да, любая другая клавиша - нет";

    final String WARNING_EMPTY_TITLE = "Название книги не может быть пустым";
    final String WARNING_INCORRECT_INPUT = "Некорректный ввод. Введите Uid";
    final String WARNING_TERMINATION = "Изменение прервано";

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

        Book book = this.showBookWizard(PROMPT_ADD_BOOK, new Book(), false);
        if (book.getUid() == -1) {
            shellHelper.printWarning(WARNING_TERMINATION);
            return;
        }
        bookService.insert(book);
        shellHelper.printSuccess(INFO_RECORD_ADDED_SUCCESSFULLY);
    }

    @ShellMethod("Редактировать книгу")
    public void editBook() {
        Book book = this.showBookWizard(PROMPT_EDIT_BOOK, new Book(), true);
        if (book.getUid() == -1) {
            shellHelper.printWarning(WARNING_TERMINATION);
            return;
        }
        bookService.edit(book);
    }

    @ShellMethod("Удалить книгу")
    public void deleteBook() {

    }

    private Book showBookWizard(String prompt, Book book, boolean edit) {
        String userInput = inputReader.prompt(prompt, "");
        if ("Q".equals(userInput.toUpperCase())) {
            book.setUid(-1);
            return book;
        }

        if (edit) {
            inputReader.prompt(PROMPT_LIST_BOOKS, "");
            //Выбрать книги из справочника
            book = this.getBookFromList();
        }

        inputReader.prompt(PROMPT_LIST_GENRES, "");
        //Выбрать жанры книги из справочника
        List<Genre> genres = this.getGenreOfBookFromTheList(new ArrayList<Genre>());
        book.setGenres(genres);

        inputReader.prompt(PROMPT_LIST_AUTHORS, "");
        //Выбрать автора из справочника
        List<Author> authors = this.getAuthorsOfBookFromTheList(new ArrayList<>());
        book.setAuthors(authors);

        // Добавить заголовок
        do {
            String fullName = inputReader.prompt(PROMPT_ENTER_BOOK_TITLE, book.getTitle());
            if (StringUtils.hasText(fullName)) {
                book.setTitle(fullName);
            } else {
                shellHelper.printWarning(WARNING_EMPTY_TITLE);
            }
        } while (book.getTitle() == null);

        // Добавить ISBN
        String isbn = inputReader.prompt(PROMPT_ENTER_ISBN, String.valueOf(book.getIsbn()));
        if (isStringLong(isbn)) {
            book.setIsbn(Long.parseLong(isbn));
        } else {
            book.setIsbn(-1);
        }
        //Добавить год издания
        String publicationYear = inputReader.prompt(PROMPT_ENTER_PUBLISHING_YEAR,
                String.valueOf(book.getPublicationYear()));
        if (isStringLong(publicationYear)) {
            book.setPublicationYear(Integer.parseInt(publicationYear));
        } else {
            book.setPublicationYear(-1);
        }
        return book;
    }

    private Book getBookFromList() {
        this.getBooks();
        Long uid = null;
        do {
            String userInput = inputReader.prompt(PROMPT_CHOOSE_BOOK, "");
            if (isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printWarning(WARNING_INCORRECT_INPUT);
            }
        } while (uid == null);

        return bookService.getByUid(uid);
    }

    private List<Genre> getGenreOfBookFromTheList(List<Genre> genres) {
        //Вывод справочника жанров для выбора
        if (genres != null && genres.isEmpty()) {
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("uid", "Uid");
            headers.put("name", "Genre");
            shellHelper.render(genreService.getAll(), headers);
        }
        Long uid = null;
        do {
            String userInput = inputReader.prompt(PROMPT_CHOOSE_GENRE, "");
            if (isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printWarning(WARNING_INCORRECT_INPUT);
            }
        }
        while (uid == null);
        genres.add(genreService.getByUid(uid));
        String userInput = inputReader.prompt(PROMPT_ADDITIONAL_GENRE, "");
        if (("Y").equals(userInput.toUpperCase())) {
            this.getGenreOfBookFromTheList(genres);
        }
        return genres;
    }

    private List<Author> getAuthorsOfBookFromTheList(List<Author> authors) {
        //Вывод справочника жанров для выбора
        if (authors != null && authors.isEmpty()) {
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("uid", "Uid");
            headers.put("fullName", "Author's Full Name");
            shellHelper.render(authorService.getAll(), headers);
        }
        Long uid = null;
        do {
            String userInput = inputReader.prompt(PROMPT_CHOOSE_AUTHOR, "");
            if (isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printWarning(WARNING_INCORRECT_INPUT);
            }
        }
        while (uid == null);
        authors.add(authorService.getByUid(uid));
        String userInput = inputReader.prompt(PROMPT_ADDITIONAL_AUTHOR, "");
        if (("Y").equals(userInput.toUpperCase())) {
            this.getAuthorsOfBookFromTheList(authors);
        }
        return authors;
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
