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
import ru.otus.homework.service.TranslationService;

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
    private final TranslationService translationService;
//    final String PROMPT_ADD_BOOK = "\nДобавляется новая книга.\nЕсли жанр книги и авторы книги не содержатся в " +
//            "справочниках, то прервите добавление с помощью клавиши Q и сначала добавьте их." +
//            "После чего повторите добавление книги";
//    final String PROMPT_EDIT_BOOK = "\nРедактирование книги.\nЕсли книга, жанр книги и авторы книги не содержатся в " +
//            "справочниках, то прервите добавление с помощью клавиши Q и сначала добавьте их." +
//            "После чего повторите редактирование книги";
//    final String PROMPT_LIST_BOOKS = "Нажмите любую клавишу для выборка книги из списка";
//    final String PROMPT_CHOOSE_BOOK = "Выберите Uid книги из справочника выше";
//
//    final String PROMPT_ENTER_BOOK_TITLE = "Введите название книги";
//    final String PROMPT_ENTER_ISBN = "Введите ISBN";
//    final String PROMPT_ENTER_PUBLISHING_YEAR = "Введите год издания";
//
//    final String INFO_RECORD_ADDED_SUCCESSFULLY = "\nЗапись успешно добавлена";
//    final String INFO_RECORD_EDITED_SUCCESSFULLY = "\nЗапись успешно отредактирована";
//    final String INFO_RECORD_DELETED_SUCCESSFULLY = "\nЗапись успешно удалена";
//
//    final String PROMPT_LIST_GENRES = "Нажмите любую клавишу для добавления жанров из справочника";
//    final String PROMPT_CHOOSE_GENRE = "Выберите Uid жанра книги из справочника выше";
//    final String PROMPT_ADDITIONAL_GENRE = "Выбрать еще один жанр? Y-да, любая другая клавиша - нет";
//
//    final String PROMPT_LIST_AUTHORS = "Нажмите любую клавишу для добавления авторов из справочника";
//
//    final String WARNING_EMPTY_TITLE = "Название книги не может быть пустым";
//    final String WARNING_INCORRECT_INPUT = "Некорректный ввод. Введите Uid";
//    final String WARNING_TERMINATION = "Изменение прервано";
//
//    final String WARNING_DELETE_BOOK = "Вы действительно хотите удалить книгу?\nНажмите Y для продолжения или любую клавишу для отмены";

    @Autowired
    public ShellBookCommandHandler(BookService bookService,
                                   AuthorService authorService,
                                   GenreService genreService,
                                   ShellHelper shellHelper,
                                   InputReader inputReader,
                                   TranslationService translationService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.shellHelper = shellHelper;
        this.inputReader = inputReader;
        this.translationService = translationService;
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

        Book book = this.showBookWizard(translationService.getTranslation("prompt.add.book",""), new Book(), false);
        if (book.getUid() == -1) {
            shellHelper.printWarning(translationService.getTranslation("warning.termination",""));
            return;
        }
        bookService.insert(book);
        shellHelper.printSuccess(translationService.getTranslation("info.record.added.successfully",""));
    }

    @ShellMethod("Редактировать книгу")
    public void editBook() {
        Book book = this.showBookWizard(translationService.getTranslation("prompt.edit.book",""), new Book(), true);
        if (book.getUid() == -1) {
            shellHelper.printWarning(translationService.getTranslation("warning.termination",""));
            return;
        }
        bookService.edit(book);
        shellHelper.printSuccess(translationService.getTranslation("info.record.edited.successfully",""));
    }

    @ShellMethod("Удалить книгу")
    public void deleteBook() {
        String userInput = inputReader.prompt(translationService.getTranslation("warning.delete.book",""), "N");
        if (!"Y".equals(userInput.toUpperCase())) {
            return;
        }
        Book book = this.getBookFromList();
        bookService.deleteByUid(book.getUid());
        shellHelper.printWarning(translationService.getTranslation("info.record.deleted.successfully",""));
    }

    private Book showBookWizard(String prompt, Book book, boolean edit) {
        String userInput = inputReader.prompt(prompt, "");
        if ("Q".equals(userInput.toUpperCase())) {
            book.setUid(-1);
            return book;
        }

        if (edit) {
            inputReader.prompt(translationService.getTranslation("prompt.list.books",""), "");
            //Выбрать книги из справочника
            book = this.getBookFromList();
        }

        inputReader.prompt(translationService.getTranslation("prompt.list.genres",""), "");
        //Выбрать жанры книги из справочника
        List<Genre> genres = this.getGenreOfBookFromTheList(new ArrayList<>());
        book.setGenres(genres);

        inputReader.prompt(translationService.getTranslation("prompt.list.authors",""), "");
        //Выбрать автора из справочника
        List<Author> authors = shellHelper.getAuthorsOfBookFromTheList(new ArrayList<>(), authorService);
        book.setAuthors(authors);

        // Добавить заголовок
        do {
            String fullName = inputReader.prompt(translationService.getTranslation("prompt.enter.book.title",""), book.getTitle());
            if (StringUtils.hasText(fullName)) {
                book.setTitle(fullName);
            } else {
                shellHelper.printWarning(translationService.getTranslation("warning.empty.title",""));
            }
        } while (book.getTitle() == null);

        // Добавить ISBN
        String isbn = inputReader.prompt(translationService.getTranslation("prompt.enter.isbn",""), String.valueOf(book.getIsbn()));
        if (isStringLong(isbn)) {
            book.setIsbn(Long.parseLong(isbn));
        } else {
            book.setIsbn(-1);
        }
        //Добавить год издания
        String publicationYear = inputReader.prompt(translationService.getTranslation("prompt.enter.publishing.year",""),
                String.valueOf(book.getPublicationYear()));
        if (isStringLong(publicationYear)) {
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
            String userInput = inputReader.prompt(translationService.getTranslation("prompt.choose.book",""), "");
            if (isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printWarning(translationService.getTranslation("warning.incorrect.input",""));
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
            String userInput = inputReader.prompt(translationService.getTranslation("prompt.choose.genre",""), "");
            if (isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printWarning(translationService.getTranslation("warning.incorrect.input",""));
            }
        }
        while (uid == null);
        genres.add(genreService.getByUid(uid));
        String userInput = inputReader.prompt(translationService.getTranslation("prompt.additional.genre",""), "");
        if (("Y").equals(userInput.toUpperCase())) {
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
