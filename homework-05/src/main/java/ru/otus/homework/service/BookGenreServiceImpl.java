package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookGenreDao;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.GenreDto;

import java.util.List;

@Service
public class BookGenreServiceImpl implements BookGenreService{

    private final BookGenreDao bookGenreDao;
    private final UtilityService utilityService;

    @Autowired
    public BookGenreServiceImpl(BookGenreDao bookGenreDao, UtilityService utilityService) {
        this.bookGenreDao = bookGenreDao;
        this.utilityService = utilityService;
    }

    @Override
    public void insertGenresByBookUid(long bookUid, List<GenreDto> genres) {
        bookGenreDao.insertGenresByBookUid(bookUid, utilityService.convertToGenreDomain(genres));
    }

    @Override
    public void editGenresByBookUid(long bookUid, List<GenreDto> genres) {
        bookGenreDao.editGenresByBookUid(bookUid, utilityService.convertToGenreDomain(genres));
    }

    @Override
    public void deleteGenresByBookUid(long bookUid, List<GenreDto> genres) {
        bookGenreDao.deleteGenresByBookUid(bookUid, utilityService.convertToGenreDomain(genres));
    }

    @Override
    public List<BookDto> getBooksByGenreUid(long genreUid) {
        return utilityService.convertToBookDto(bookGenreDao.getBooksByGenreUid(genreUid));
    }

    @Override
    public List<GenreDto> getGenresByBookUid(long bookUid) {

        return utilityService.convertToGenreDto(bookGenreDao.getGenresByBookUid(bookUid));
    }
}
