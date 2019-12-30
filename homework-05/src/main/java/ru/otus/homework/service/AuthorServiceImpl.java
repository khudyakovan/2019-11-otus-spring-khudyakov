package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.*;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final BookAuthorDao bookAuthorDao;
    private final BookGenreDao bookGenreDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, BookAuthorDao bookAuthorDao, BookGenreDao bookGenreDao) {
        this.authorDao = authorDao;
        this.bookAuthorDao = bookAuthorDao;
        this.bookGenreDao = bookGenreDao;
    }

    @Override
    public Author insert(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public void edit(Author author) {
        authorDao.edit(author);
    }

    @Override
    public void deleteByUid(long uid) {
        authorDao.deleteByUid(uid);
    }

    @Override
    public Author getByUid(long uid) {
        Author author = authorDao.getByUid(uid);
        List<Book> books = bookAuthorDao.getBooksByAuthorUid(uid);
        books.forEach(book -> book.setGenres(bookGenreDao.getGenresByBookUid(book.getUid())));
        author.setBooks(books);
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = authorDao.getAll();
        authors.forEach(author -> author.setBooks(bookAuthorDao.getBooksByAuthorUid(author.getUid())));
        return authors;
    }

    @Override
    public int count() {
        return authorDao.count();
    }
}
