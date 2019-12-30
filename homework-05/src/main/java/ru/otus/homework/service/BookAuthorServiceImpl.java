package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookAuthorDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;

import java.util.List;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    @Autowired
    private final BookAuthorDao bookAuthorDao;

    public BookAuthorServiceImpl(BookAuthorDao bookAuthorDao) {
        this.bookAuthorDao = bookAuthorDao;
    }

    @Override
    public void insertAuthorsByBookUid(long bookUid, List<Author> authors) {
        bookAuthorDao.insertAuthorsByBookUid(bookUid, authors);
    }

    @Override
    public void editAuthorsByBookUid(long bookUid, List<Author> authors) {
        bookAuthorDao.editAuthorsByBookUid(bookUid, authors);
    }

    @Override
    public void deleteAuthorsByBookUid(long bookUid, List<Author> authors) {
        bookAuthorDao.deleteAuthorsByBookUid(bookUid, authors);
    }

    @Override
    public List<Book> getBooksByAuthorUid(long authorUid) {
        return bookAuthorDao.getBooksByAuthorUid(authorUid);
    }

    @Override
    public List<Author> getAuthorsByBookUid(long bookUid) {
        return bookAuthorDao.getAuthorsByBookUid(bookUid);
    }
}
