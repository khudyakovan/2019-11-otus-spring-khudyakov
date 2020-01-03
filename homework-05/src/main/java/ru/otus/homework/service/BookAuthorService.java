package ru.otus.homework.service;

import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookDto;

import java.util.List;

public interface BookAuthorService {
    void insertAuthorsByBookUid(long bookUid, List<AuthorDto> authors);

    void editAuthorsByBookUid(long bookUid, List<AuthorDto> authors);

    void deleteAuthorsByBookUid(long bookUid, List<AuthorDto> authors);

    List<BookDto> getBooksByAuthorUid(long authorUid);

    List<AuthorDto> getAuthorsByBookUid(long bookUid);
}
