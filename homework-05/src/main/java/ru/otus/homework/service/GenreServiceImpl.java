package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.GenreDto;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final BookService bookService;
    private final UtilityService utilityService;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao, BookService bookService, UtilityService utilityService) {
        this.genreDao = genreDao;
        this.bookService = bookService;
        this.utilityService = utilityService;
    }

    @Override
    public GenreDto insert(GenreDto genreDto) {
        Genre genre = genreDao.insert(new Genre(genreDto.getName()));
        return this.getByUid(genre.getUid());
    }

    @Override
    public void edit(GenreDto genreDto) {
        Genre genre = new Genre(genreDto.getName());
        genreDao.edit(genre);
    }

    @Override
    public void deleteByUid(long uid) {
        genreDao.deleteByUid(uid);
    }

    @Override
    public GenreDto getByUid(long uid) {
        GenreDto genreDto = new GenreDto(genreDao.getByUid(uid));
        genreDto.setBooks(bookService.getBooksByGenreUid(uid));
        return genreDto;
    }

    @Override
    public List<GenreDto> getAll() {
        List<GenreDto> genreDtos = utilityService.convertToGenreDto(genreDao.getAll());
        genreDtos.forEach(genreDto -> {
            List<BookDto> bookDtos = bookService.getBooksByGenreUid(genreDto.getUid());
            genreDto.setBooks(bookDtos);

        });
        return genreDtos;
    }

    @Override
    public int count() {
        return genreDao.count();
    }

    @Override
    public void insertGenresByBookUid(long bookUid, List<GenreDto> genres) {
        genreDao.insertGenresByBookUid(bookUid, utilityService.convertToGenreDomain(genres));
    }

    @Override
    public void editGenresByBookUid(long bookUid, List<GenreDto> genres) {
        genreDao.editGenresByBookUid(bookUid, utilityService.convertToGenreDomain(genres));
    }

    @Override
    public void deleteGenresByBookUid(long bookUid, List<GenreDto> genres) {
        genreDao.deleteGenresByBookUid(bookUid, utilityService.convertToGenreDomain(genres));
    }

    @Override
    public List<GenreDto> getGenresByBookUid(long bookUid) {
        return utilityService.convertToGenreDto(genreDao.getGenresByBookUid(bookUid));
    }
}
