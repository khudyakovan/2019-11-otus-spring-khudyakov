package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final UtilityService utilityService;

    @Autowired
    public BookServiceImpl(BookDao bookDao,
                           @Lazy AuthorService authorService,
                           @Lazy GenreService genreService,
                           UtilityService utilityService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
        this.utilityService = utilityService;
    }

    @Override
    public BookDto insert(BookDto bookDto) {
        List<GenreDto> genres = bookDto.getGenres();
        List<AuthorDto> authors = bookDto.getAuthors();
        Book book = new Book(bookDto.getTitle(), bookDto.getIsbn(), bookDto.getPublicationYear());
        bookDto = new BookDto(bookDao.insert(book));
        genreService.insertGenresByBookUid(bookDto.getUid(), genres);
        authorService.insertAuthorsByBookUid(bookDto.getUid(), authors);
        return this.getByUid(bookDto.getUid());
    }

    @Override
    public void edit(BookDto bookDto) {
        genreService.editGenresByBookUid(bookDto.getUid(), bookDto.getGenres());
        authorService.editAuthorsByBookUid(bookDto.getUid(), bookDto.getAuthors());
        Book book = new Book(bookDto.getUid(), bookDto.getTitle(), bookDto.getIsbn(), bookDto.getPublicationYear());
        bookDao.edit(book);
    }

    @Override
    public void deleteByUid(long uid) {
        bookDao.deleteByUid(uid);
    }

    @Override
    public BookDto getByUid(long uid) {
        BookDto bookDto = new BookDto(bookDao.getByUid(uid));
        bookDto.setAuthors(authorService.getAuthorsByBookUid(uid));
        bookDto.setGenres(genreService.getGenresByBookUid(uid));
        return bookDto;
    }

    @Override
    public List<BookDto> getAll() {
        List<Book> books = bookDao.getAll();
        List<BookDto> bookDtos = new ArrayList<>();
        books.forEach(book -> {
            BookDto bookDto = new BookDto(book,
                    authorService.getAuthorsByBookUid(book.getUid()),
                    genreService.getGenresByBookUid(book.getUid()));
            bookDtos.add(bookDto);
        });
        return bookDtos;
    }

    @Override
    public int count() {
        return bookDao.count();
    }

    @Override
    public List<BookDto> getBooksByGenreUid(long genreUid) {
        return utilityService.convertToBookDto(bookDao.getBooksByGenreUid(genreUid));
    }

    @Override
    public List<BookDto> getBooksByAuthorUid(long authorUid) {
        return utilityService.convertToBookDto(bookDao.getBooksByAuthorUid(authorUid));
    }
}
