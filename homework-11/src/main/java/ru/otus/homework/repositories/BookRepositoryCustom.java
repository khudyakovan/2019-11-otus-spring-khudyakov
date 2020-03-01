package ru.otus.homework.repositories;

public interface BookRepositoryCustom {

    void deleteAuthorFromBooksByAuthorId(String id);

    void deleteGenreFromBooksByGenreId(String id);

    void deleteCommentsFromBooksWhereCommentatorId(String id);
}
