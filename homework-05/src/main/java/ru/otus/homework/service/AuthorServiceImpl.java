package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookDto;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final BookAuthorService bookAuthorService;
    private final BookGenreService bookGenreService;
    private final UtilityService utilityService;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, BookAuthorService bookAuthorService, BookGenreService bookGenreService, UtilityService utilityService) {
        this.authorDao = authorDao;
        this.bookAuthorService = bookAuthorService;
        this.bookGenreService = bookGenreService;
        this.utilityService = utilityService;
    }

    @Override
    public AuthorDto insert(AuthorDto authorDto) {
        Author author = authorDao.insert(new Author(authorDto.getFullName(), authorDto.getPenName()));
        return this.getByUid(author.getUid());
    }

    @Override
    public void edit(AuthorDto authorDto) {
        authorDao.edit(new Author(authorDto.getUid(), authorDto.getFullName(), authorDto.getPenName()));
    }

    @Override
    public void deleteByUid(long uid) {
        authorDao.deleteByUid(uid);
    }

    @Override
    public AuthorDto getByUid(long uid) {
        AuthorDto authorDto = new AuthorDto(authorDao.getByUid(uid));
        List<BookDto> bookDtos = bookAuthorService.getBooksByAuthorUid(uid);
        authorDto.setBooks(bookDtos);
        return authorDto;
    }

    @Override
    public List<AuthorDto> getAll() {
        List<AuthorDto> authorDtos = utilityService.convertToAuthorDto(authorDao.getAll());
        authorDtos.forEach(author -> {
            List<BookDto> bookDtos = bookAuthorService.getBooksByAuthorUid(author.getUid());
            author.setBooks(bookDtos);
        });
        return authorDtos;
    }

    @Override
    public int count() {
        return authorDao.count();
    }
}
