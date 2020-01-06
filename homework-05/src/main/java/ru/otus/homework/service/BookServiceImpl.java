package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.domain.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final UtilityService utilityService;

    @Autowired
    public BookServiceImpl(BookDao bookDao,
                           @Lazy AuthorService authorService,
                           @Lazy GenreService genreService,
                           UtilityService utilityService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
        this.utilityService = utilityService;
    }

    @Override
    public Book insert(Book book) {
        book = bookDao.insert(book);
        genreService.insertGenresByBookUid(book.getUid(), utilityService.convertToGenreDto(book.getGenres()));
        authorService.insertAuthorsByBookUid(book.getUid(), utilityService.convertToAuthorDto(book.getAuthors()));
        return this.getByUid(book.getUid());
    }

    @Override
    public void edit(Book book) {
        genreService.editGenresByBookUid(book.getUid(), utilityService.convertToGenreDto(book.getGenres()));
        authorService.editAuthorsByBookUid(book.getUid(), utilityService.convertToAuthorDto(book.getAuthors()));
        book = new Book(book.getUid(), book.getTitle(), book.getIsbn(), book.getPublicationYear());
        bookDao.edit(book);
    }

    @Override
    public void deleteByUid(long uid) {
        bookDao.deleteByUid(uid);
    }

    @Override
    public Book getByUid(long uid) {
        Book book = bookDao.getByUid(uid);
        return book;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public int count() {
        return bookDao.count();
    }

    @Override
    public List<Book> getBooksByGenreUid(long genreUid) {
        return bookDao.getBooksByGenreUid(genreUid);
    }

    @Override
    public List<Book> getBooksByAuthorUid(long authorUid) {
        return bookDao.getBooksByAuthorUid(authorUid);
    }
}
