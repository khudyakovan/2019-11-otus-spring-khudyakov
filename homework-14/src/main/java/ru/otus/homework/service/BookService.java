package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.entity.mongo.AuthorMongo;
import ru.otus.homework.entity.mongo.BookMongo;
import ru.otus.homework.entity.mongo.CommentMongo;
import ru.otus.homework.entity.mongo.GenreMongo;
import ru.otus.homework.entity.mysql.Author;
import ru.otus.homework.entity.mysql.Book;
import ru.otus.homework.entity.mysql.Comment;
import ru.otus.homework.entity.mysql.Genre;
import ru.otus.homework.repository.mysql.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements GenericService<BookMongo, Book> {

    private final CommentService commentService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentRepository commentRepository;

    @Override
    public BookMongo transform(Book entity) {
        return new BookMongo(String.valueOf(entity.getUid()),
                entity.getTitle(),
                entity.getIsbn(),
                entity.getPublicationYear(),
                this.populateAuthors(entity.getAuthors()),
                this.populateGenres(entity.getGenres()),
                this.populateComments(commentRepository.findCommentsByBookUid(entity.getUid())));
    }

    private List<CommentMongo> populateComments(List<Comment> comments) {
        List<CommentMongo> cm = new ArrayList<>();
        comments.forEach(comment -> {
            cm.add(commentService.transform(comment));
        });
        return cm;
    }

    private List<AuthorMongo> populateAuthors(List<Author> authors) {
        List<AuthorMongo> am = new ArrayList<>();
        authors.forEach(author -> {
            am.add(authorService.transform(author));
        });
        return am;
    }

    private List<GenreMongo> populateGenres(List<Genre> genres) {
        List<GenreMongo> gm = new ArrayList<>();
        genres.forEach(genre -> {
            gm.add(genreService.transform(genre));
        });
        return gm;
    }
}
