package ru.otus.homework.repositories;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Book;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {

    @Autowired
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UpdateResult> deleteAuthorFromBooksByAuthorId(String id) {

        val query = Query.query(Criteria.where("$id").is(id));
        val update = new Update().pull("authors", query);
        return mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public Mono<UpdateResult> deleteGenreFromBooksByGenreId(String id) {

        val query = Query.query(Criteria.where("$id").is(id));
        val update = new Update().pull("genres", query);
        return mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public Mono<UpdateResult> deleteCommentsFromBooksWhereCommentatorId(String id) {
        val query = Query.query(Criteria.where("commentator._id").is(id));
        val update = new Update().pull("comments", query);
        return mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
