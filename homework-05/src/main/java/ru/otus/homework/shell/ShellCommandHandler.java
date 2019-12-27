package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.util.StringUtils;
import ru.otus.homework.domain.Book;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
public class ShellCommandHandler {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ShellHelper shellHelper;
    private final InputReader inputReader;
    final String PROMPT_ADD_BOOK = "\nДобавляется новая книга:";
    final String PROMPT_RECORD_ADDED_SUCCESSFULLY = "\nЗапись успешно добавлена";
    final String PROMPT_ENTER_BOOK_TITLE = "Введите название книги";
    final String PROMPT_EMPTY_TITLE = "Название книги не может быть пустым";
    final String PROMPT_ENTER_ISBN = "Введите ISBN";
    final String PROMPT_ENTER_PUBLISHING_YEAR = "Введите год издания";
    final String PROMPT_LIST_GENRES = "Вывод справочника жанров.\nЕсли жанр не найден, то нажмите клавишу N и " +
            "создайте новую запись в справочнике жанров. ";
    final String PROMPT_CHOOSE_GENRE = "Выберите жанр книги";

    @Autowired
    public ShellCommandHandler(BookService bookService,
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

        this.render(bookService.getAll(), headers);
    }

    @ShellMethod("Получить список авторов книг в библиотеке")
    public void getAuthors() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("uid", "Uid");
        headers.put("fullName", "Author's Full Name");
        headers.put("books", "Books Written By The Author");
        this.render(authorService.getAll(), headers);
    }

    @ShellMethod("Получить список жанров и книг этого жанра")
    public void getGenres() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("uid", "Uid");
        headers.put("name", "Genre");
        headers.put("books", "Books This Genre");
        this.render(genreService.getAll(), headers);
    }

    @ShellMethod("Добавить новую книгу")
    public void addBook() {
        Book book = new Book();
        // Добавить заголовок
        do {
            String fullName = inputReader.prompt(PROMPT_ENTER_BOOK_TITLE);
            if (StringUtils.hasText(fullName)) {
                book.setTitle(fullName);
            } else {
                shellHelper.printWarning(PROMPT_EMPTY_TITLE);
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
        //Вывод информации о созданной книге
        shellHelper.printInfo(PROMPT_ADD_BOOK);
        shellHelper.print(book.getTitle());
        shellHelper.print(String.valueOf(book.getIsbn()));
        shellHelper.print(String.valueOf(book.getPublicationYear()));

        //Вывод справочника жанров для выбора
        String userInput = inputReader.prompt(PROMPT_LIST_GENRES,"");
        if( userInput.toUpperCase().equals("N")){
            return;
        };
        this.getGenres();

        /*
        TODO
        Обработка пользовательского ввода ID
        */
        userInput = inputReader.prompt(PROMPT_CHOOSE_GENRE, "");
        do{

        }
        while (!isStringLong(userInput));

        bookService.insert(book);
        shellHelper.printSuccess(PROMPT_RECORD_ADDED_SUCCESSFULLY);
    }

//    @ShellMethod("Добавить нового автора")
//    public void addAuthor() {
//
//    }
//
//    @ShellMethod("Добавить новый жанр")
//    public void addGenre() {
//
//    }

    @ShellMethod("Редактировать книгу")
    public void editBook() {

    }

//    @ShellMethod("Редактировать автора")
//    public void editAuthor() {
//
//    }
//
//    @ShellMethod("Редактировать жанр")
//    public void editGenre() {
//
//    }

    @ShellMethod("Удалить книгу")
    public void deleteBook() {

    }

    @ShellMethod("Удалить автора")
    public void deleteAuthor() {

    }

//    @ShellMethod("Удалить жанр")
//    public void deleteGenre() {
//
//    }

    private void render(List list, LinkedHashMap<String, Object> headers) {
        TableModel model = new BeanListTableModel<>(list, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.oldschool);
        tableBuilder.addHeaderBorder(BorderStyle.oldschool);
        shellHelper.print(tableBuilder.build().render(180), null);
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
