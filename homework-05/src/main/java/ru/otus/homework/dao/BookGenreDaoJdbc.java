package ru.otus.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookGenreDaoJdbc implements BookGenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookGenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insertGenresByBookUid(long bookUid, List<Genre> genres) {
        if (genres == null){
            return;
        }
        genres.forEach(genre -> {
            final Map<String, Object> params = new HashMap<>(2);
            params.put("bookUid", bookUid);
            params.put("genreUid", genre.getUid());
            jdbc.update("insert into tbl_book_genre(book_uid, genre_uid) values(:bookUid, :genreUid)", params);
        });
    }

    @Override
    public void editGenresByBookUid(long bookUid, List<Genre> genres) {
        genres.forEach(genre -> {
            final Map<String, Object> params = new HashMap<>(1);
            params.put("bookUid", bookUid);
            jdbc.update("delete from tbl_book_genre where book_uid = :bookUid", params);
        });
        this.insertGenresByBookUid(bookUid, genres);
    }

    @Override
    public void deleteGenresByBookUid(long bookUid, List<Genre> genres) {
        genres.forEach(genre -> {
            final Map<String, Object> params = new HashMap<>(2);
            params.put("bookUid", bookUid);
            params.put("genreUid", genre.getUid());
            jdbc.update("delete from tbl_book_genre where book_uid = :bookUid and genre_uid = :genreUid", params);
        });
    }

    @Override
    public List<Book> getBooksByGenreUid(long genreUid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("genreUid", genreUid);
        return jdbc.query("select b.uid, b.title, b.isbn, b.publication_year from tbl_book_genre bg\n" +
                "join tbl_genres g on bg.genre_uid = g.uid\n" +
                "join tbl_books b on bg.book_uid = b.uid\n" +
                "where g.uid = :genreUid", params, new BookMapper());
    }

    @Override
    public List<Genre> getGenresByBookUid(long bookUid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("bookUid", bookUid);
        return jdbc.query("select g.uid, g.name from tbl_book_genre bg\n" +
                "join tbl_genres g on bg.genre_uid = g.uid\n" +
                "join tbl_books b on bg.book_uid = b.uid\n" +
                "where b.uid = :bookUid", params, new GenreMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long uid = resultSet.getLong("uid");
            String title = resultSet.getString("title");
            long isbn = resultSet.getLong("isbn");
            short publication_year = resultSet.getShort("publication_year");
            return new Book(uid, title, isbn, publication_year);
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long uid = resultSet.getLong("uid");
            String name = resultSet.getString("name");
            return new Genre(uid, name);
        }
    }

}
