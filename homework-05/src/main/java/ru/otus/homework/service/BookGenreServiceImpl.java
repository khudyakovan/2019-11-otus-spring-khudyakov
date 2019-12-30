package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookGenreDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Service
public class BookGenreServiceImpl implements BookGenreService{

    @Autowired
    private final BookGenreDao bookGenreDao;

    public BookGenreServiceImpl(BookGenreDao bookGenreDao) {
        this.bookGenreDao = bookGenreDao;
    }

    @Override
    public void insertGenresByBookUid(long bookUid, List<Genre> genres) {
        bookGenreDao.insertGenresByBookUid(bookUid, genres);
    }

    @Override
    public void editGenresByBookUid(long bookUid, List<Genre> genres) {
        bookGenreDao.editGenresByBookUid(bookUid, genres);
    }

    @Override
    public void deleteGenresByBookUid(long bookUid, List<Genre> genres) {
        bookGenreDao.deleteGenresByBookUid(bookUid, genres);
    }

    @Override
    public List<Book> getBooksByGenreUid(long genreUid) {
        return bookGenreDao.getBooksByGenreUid(genreUid);
    }

    @Override
    public List<Genre> getGenresByBookUid(long bookUid) {
        return bookGenreDao.getGenresByBookUid(bookUid);
    }
}
