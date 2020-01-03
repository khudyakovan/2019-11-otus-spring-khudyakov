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
}
