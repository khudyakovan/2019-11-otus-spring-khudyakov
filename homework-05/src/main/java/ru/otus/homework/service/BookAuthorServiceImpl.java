package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookAuthorDao;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookDto;

import java.util.List;

@Service
public class BookAuthorServiceImpl implements ru.otus.homework.service.BookAuthorService {

    private final BookAuthorDao bookAuthorDao;
    private final UtilityService utilityService;

    @Autowired
    public BookAuthorServiceImpl(BookAuthorDao bookAuthorDao, UtilityService utilityService) {
        this.bookAuthorDao = bookAuthorDao;
        this.utilityService = utilityService;
    }

    @Override
    public void insertAuthorsByBookUid(long bookUid, List<AuthorDto> authors) {
        bookAuthorDao.insertAuthorsByBookUid(bookUid, utilityService.convertToAuthorDomain(authors));
    }

    @Override
    public void editAuthorsByBookUid(long bookUid, List<AuthorDto> authors) {
        bookAuthorDao.editAuthorsByBookUid(bookUid, utilityService.convertToAuthorDomain(authors));
    }

    @Override
    public void deleteAuthorsByBookUid(long bookUid, List<AuthorDto> authors) {
        bookAuthorDao.deleteAuthorsByBookUid(bookUid, utilityService.convertToAuthorDomain(authors));
    }

    @Override
    public List<BookDto> getBooksByAuthorUid(long authorUid) {
        return utilityService.convertToBookDto(bookAuthorDao.getBooksByAuthorUid(authorUid));
    }

    @Override
    public List<AuthorDto> getAuthorsByBookUid(long bookUid) {
        return utilityService.convertToAuthorDto(bookAuthorDao.getAuthorsByBookUid(bookUid));
    }
}
