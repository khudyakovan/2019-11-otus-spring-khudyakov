package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework.entity.mongo.*;
import ru.otus.homework.entity.mysql.Author;
import ru.otus.homework.entity.mysql.Book;
import ru.otus.homework.entity.mysql.Comment;
import ru.otus.homework.entity.mysql.Genre;
import ru.otus.homework.repository.mongo.AuthorRepositoryMongo;
import ru.otus.homework.repository.mongo.GenreRepositoryMongo;
import ru.otus.homework.repository.mongo.UserRepositoryMongo;
import ru.otus.homework.repository.mysql.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements GenericService<BookMongo, Book> {

    private final CommentService commentService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final AuthorRepositoryMongo authorRepository;
    private final GenreRepositoryMongo genreRepository;
    private final UserRepositoryMongo userRepository;

    @Override
    public BookMongo transform(Book entity) {
        return new BookMongo(String.valueOf(new ObjectId()),
                entity.getTitle(),
                entity.getIsbn(),
                entity.getPublicationYear(),
                this.populateAuthors(entity.getAuthors()),
                this.populateGenres(entity.getGenres()),
                this.populateComments(commentRepository.findCommentsByBookUid(entity.getUid())));
    }

    private List<CommentMongo> populateComments(List<Comment> comments) {
        List<CommentMongo> cl = new ArrayList<>();
        comments.forEach(comment -> {
            UserMongo userMongo = userRepository.findByUsername(comment.getUser().getUsername());
            if(userMongo == null){
                userMongo = userRepository.save(userService.transform(comment.getUser()));
                CommentMongo cm = commentService.transform(comment);
                cm.setUserMongo(userMongo);
                cl.add(cm);
            }else {
                CommentMongo cm = commentService.transform(comment);
                cm.setUserMongo(userMongo);
                cl.add(cm);
            }
        });
        return cl;
    }

    private List<AuthorMongo> populateAuthors(List<Author> authors) {
        List<AuthorMongo> am = new ArrayList<>();
        authors.forEach(author -> {
            AuthorMongo authorMongo = authorRepository.findByFullName(author.getFullName());
            if (authorMongo == null) {
                authorMongo = authorRepository.save(authorService.transform(author));
                am.add(authorMongo);
            } else {
                am.add(authorService.transform(author));
            }
        });
        return am;
    }

    private List<GenreMongo> populateGenres(List<Genre> genres) {
        List<GenreMongo> gm = new ArrayList<>();
        genres.forEach(genre -> {
            GenreMongo genreMongo = genreRepository.findByName(genre.getName());
            if (genreMongo == null) {
                genreMongo = genreRepository.save(genreService.transform(genre));
                gm.add(genreMongo);
            } else {
                gm.add(genreService.transform(genre));
            }
        });
        return gm;
    }
}
