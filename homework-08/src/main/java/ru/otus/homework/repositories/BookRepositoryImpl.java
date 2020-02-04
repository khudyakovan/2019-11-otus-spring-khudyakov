package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.models.Book;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteAuthorFromBooksByAuthorId(String id) {

        val query = Query.query(Criteria.where("$id").is(id));
        val update = new Update().pull("authors", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public void deleteGenreFromBooksByGenreId(String id) {

        val query = Query.query(Criteria.where("$id").is(id));
        val update = new Update().pull("genres", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public void deleteCommentsFromBooksWhereCommentatorId(String id) {
        val query = Query.query(Criteria.where("commentator._id").is(id));
        val update = new Update().pull("comments", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
