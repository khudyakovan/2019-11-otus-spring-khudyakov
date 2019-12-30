package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookAuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.BookGenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final BookAuthorDao bookAuthorDao;
    private final BookGenreDao bookGenreDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao, BookAuthorDao bookAuthorDao, BookGenreDao bookGenreDao) {
        this.bookDao = bookDao;
        this.bookAuthorDao = bookAuthorDao;
        this.bookGenreDao = bookGenreDao;
    }

    @Override
    public Book insert(Book book) {
        book = bookDao.insert(book);
        bookGenreDao.insertGenresByBookUid(book.getUid(), book.getGenres());
        bookAuthorDao.insertAuthorsByBookUid(book.getUid(), book.getAuthors());
        return book;
    }

    @Override
    public void edit(Book book) {
        bookGenreDao.editGenresByBookUid(book.getUid(), book.getGenres());
        bookAuthorDao.editAuthorsByBookUid(book.getUid(), book.getAuthors());
        bookDao.edit(book);
    }

    @Override
    public void deleteByUid(long uid) {
        bookDao.deleteByUid(uid);
    }

    @Override
    public Book getByUid(long uid) {
        Book book = bookDao.getByUid(uid);
        book.setAuthors(bookAuthorDao.getAuthorsByBookUid(uid));
        book.setGenres(bookGenreDao.getGenresByBookUid(uid));
        return book;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = bookDao.getAll();
        books.forEach(book -> {
            book.setAuthors(bookAuthorDao.getAuthorsByBookUid(book.getUid()));
            book.setGenres(bookGenreDao.getGenresByBookUid(book.getUid()));
        });
        return books;
    }

    @Override
    public int count() {
        return bookDao.count();
    }
}
