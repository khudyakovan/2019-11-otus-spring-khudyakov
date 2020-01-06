package ru.otus.homework.service;

import ru.otus.homework.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorDto insert(AuthorDto author);

    void edit(AuthorDto authorDto);

    void deleteByUid(long uid);

    AuthorDto getByUid(long uid);

    List<AuthorDto> getAll();

    int count();

    void insertAuthorsByBookUid(long bookUid, List<AuthorDto> authors);

    void editAuthorsByBookUid(long bookUid, List<AuthorDto> authors);

    void deleteAuthorsByBookUid(long bookUid, List<AuthorDto> authors);

    List<AuthorDto> getAuthorsByBookUid(long bookUid);
}
