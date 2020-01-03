package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookDto;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final BookService bookService;
    private final GenreService genreService;
    private final UtilityService utilityService;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao,
                             @Lazy BookService bookService,
                             @Lazy GenreService genreService,
                             UtilityService utilityService) {
        this.authorDao = authorDao;
        this.bookService = bookService;
        this.genreService = genreService;
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
        List<BookDto> bookDtos = bookService.getBooksByAuthorUid(uid);
        authorDto.setBooks(bookDtos);
        return authorDto;
    }

    @Override
    public List<AuthorDto> getAll() {
        List<AuthorDto> authorDtos = utilityService.convertToAuthorDto(authorDao.getAll());
        authorDtos.forEach(author -> {
            List<BookDto> bookDtos = bookService.getBooksByAuthorUid(author.getUid());
            author.setBooks(bookDtos);
        });
        return authorDtos;
    }

    @Override
    public int count() {
        return authorDao.count();
    }

    @Override
    public void insertAuthorsByBookUid(long bookUid, List<AuthorDto> authors) {
        authorDao.insertAuthorsByBookUid(bookUid, utilityService.convertToAuthorDomain(authors));
    }

    @Override
    public void editAuthorsByBookUid(long bookUid, List<AuthorDto> authors) {
        authorDao.editAuthorsByBookUid(bookUid, utilityService.convertToAuthorDomain(authors));
    }

    @Override
    public void deleteAuthorsByBookUid(long bookUid, List<AuthorDto> authors) {
        authorDao.deleteAuthorsByBookUid(bookUid, utilityService.convertToAuthorDomain(authors));
    }

    @Override
    public List<AuthorDto> getAuthorsByBookUid(long bookUid) {
        return utilityService.convertToAuthorDto(authorDao.getAuthorsByBookUid(bookUid));
    }
}
