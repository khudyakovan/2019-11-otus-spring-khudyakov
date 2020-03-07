package ru.otus.homework.repositories;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Mono;

public interface BookRepositoryCustom {

    Mono<UpdateResult> deleteAuthorFromBooksByAuthorId(String id);

    Mono<UpdateResult> deleteGenreFromBooksByGenreId(String id);

    Mono<UpdateResult> deleteCommentsFromBooksWhereCommentatorId(String id);
}
