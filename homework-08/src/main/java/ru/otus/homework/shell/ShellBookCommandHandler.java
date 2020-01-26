package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.service.GenreService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellBookCommandHandler {

    private final ShellHelper shellHelper;
    private final BookService bookService;
    private final InputReader inputReader;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final CommentService commentService;

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

    @ShellMethod("Получить описание книги")
    public void showBookDetails(){
        Book book = this.getBookFromList();
        List<Comment> comments = commentService.findCommentsByBookUid(book.getUid());
        shellHelper.printInfoTranslated("info.book.details","");
        shellHelper.printSuccessTranslated("info.book.details.title", book.getTitle());
        shellHelper.printSuccessTranslated("info.book.details.isbn", String.valueOf(book.getIsbn()));
        shellHelper.printSuccessTranslated("info.book.details.publicationYear", String.valueOf(book.getPublicationYear()));
        shellHelper.printSuccessTranslated("info.book.details.authors", book.getAuthors().toString());
        shellHelper.printSuccessTranslated("info.book.details.genres", book.getGenres().toString());
        shellHelper.printInfoTranslated("info.book.details.comments", String.valueOf(comments.size()));
        inputReader.promptTranslated("prompt.list.comments","");
        comments.forEach(comment -> shellHelper.printSuccessTranslated("info.book.details.template",
                comment.getCommentator().toString(),
                comment.getCommentText(),
                comment.getCommentDate().toString()));
    }

    @ShellMethod("Добавить комментарий к книге")
    public void addBookComment(){
        Book book = this.getBookFromList();

        Comment comment = new Comment();

        // Добавить комментарий
        do {
            String commentText = inputReader.promptTranslated("prompt.add.new.comment", "");
            if (StringUtils.hasText(commentText)) {
                comment.setCommentText(commentText);
                comment.setCommentDate(new Date());
            } else {
                shellHelper.printErrorTranslated("error.empty.comment");
            }
        } while (comment.getCommentText() == null);
        comment.setBook(book);
        commentService.save(comment);
        shellHelper.printSuccessTranslated("info.record.added.successfully");
    }

    @ShellMethod("Добавить новую книгу")
    public void addBook() {
        Book book = this.bookWizard("prompt.add.book", new Book(), false);
        if (book.getUid() == -1) {
            shellHelper.printWarningTranslated("warning.termination");
            return;
        }
        bookService.save(book);
        shellHelper.printSuccessTranslated("info.record.added.successfully");
    }

    @ShellMethod("Редактировать книгу")
    public void editBook() {
        Book book = this.bookWizard("prompt.edit.book", new Book(), true);
        if (book.getUid() == -1) {
            shellHelper.printWarningTranslated("warning.termination");
            return;
        }
        bookService.save(book);
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
        List<Genre> genres = this.getGenreOfBookFromTheList(new ArrayList<>());
        book.setGenres(genres);

        inputReader.promptTranslated("prompt.list.authors","");
        //Выбрать автора из справочника
        List<Author> authors = shellHelper.getAuthorsFromList(new ArrayList<>(), authorService);
        book.setAuthors(authors);

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
        if (NumberUtils.isParsable(isbn)) {
            book.setIsbn(Long.parseLong(isbn));
        } else {
            book.setIsbn(-1);
        }
        //Добавить год издания
        String publicationYear = inputReader.promptTranslated("prompt.enter.publishing.year",
                String.valueOf(book.getPublicationYear()));
        if (NumberUtils.isParsable(publicationYear)) {
            book.setPublicationYear(Integer.parseInt(publicationYear));
        } else {
            book.setPublicationYear(-1);
        }
        return book;
    }

    private List<Genre> getGenreOfBookFromTheList(List<Genre> genreDtos) {
        //Вывод справочника жанров для выбора
        if (genreDtos != null && genreDtos.isEmpty()) {
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("uid", "Uid");
            headers.put("name", "Genre");
            shellHelper.render(genreService.findAll(), headers);
        }
        Long uid = null;
        do {
            String userInput = inputReader.promptTranslated("prompt.choose.genre","");
            if (NumberUtils.isParsable(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printErrorTranslated("error.incorrect.input");
            }
        }
        while (uid == null);
        genreDtos.add(genreService.findByUid(uid));
        String userInput = inputReader.promptTranslated("prompt.additional.genre","");
        if (("Y").equals(userInput.toUpperCase())) {
            this.getGenreOfBookFromTheList(genreDtos);
        }
        return genreDtos;
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
