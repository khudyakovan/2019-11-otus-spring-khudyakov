package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookGenreDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final BookGenreDao bookGenreDaoDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao, BookGenreDao bookDao) {
        this.genreDao = genreDao;
        this.bookGenreDaoDao = bookDao;
    }

    @Override
    public Genre insert(Genre genre) {
        return genreDao.insert(genre);
    }

    @Override
    public void edit(Genre genre) {
        genreDao.edit(genre);
    }

    @Override
    public void deleteByUid(long uid) {
        genreDao.deleteByUid(uid);
    }

    @Override
    public Genre getByUid(long uid) {
        Genre genre = genreDao.getByUid(uid);
        genre.setBooks(bookGenreDaoDao.getBooksByGenreUid(uid));
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = genreDao.getAll();
        genres.forEach(genre -> genre.setBooks(bookGenreDaoDao.getBooksByGenreUid(genre.getUid())));
        return genres;
    }

    @Override
    public int count() {
        return genreDao.count();
    }
}
