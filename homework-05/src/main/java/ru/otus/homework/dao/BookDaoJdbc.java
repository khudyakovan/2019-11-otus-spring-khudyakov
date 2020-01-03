package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    public Book insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("isbn", book.getIsbn())
                .addValue("publication_year", book.getPublicationYear());
        jdbc.update("insert into tbl_books(title, isbn, publication_year) " +
                        "values(:title, :isbn, :publication_year)"
                , params, keyHolder);
        book.setUid(keyHolder.getKey().longValue());
        return book;
    }

    public void edit(Book book) {
        final Map<String, Object> params = new HashMap<>(4);
        params.put("uid", book.getUid());
        params.put("title", book.getTitle());
        params.put("isbn", book.getIsbn());
        params.put("publication_year", book.getPublicationYear());
        jdbc.update("update tbl_books set title = :title, " +
                        "isbn = :isbn, publication_year = :publication_year " +
                        "where uid = :uid"
                , params);
    }

    public void deleteByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        jdbc.update("delete from tbl_books where uid = :uid", params);
    }

    public Book getByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        return jdbc.queryForObject("select * from tbl_books where uid = :uid", params, new BookMapper());
    }

    public List<Book> getAll() {
        return jdbc.query("select * from tbl_books", new BookMapper());
    }

    public int count() {
        return jdbc.queryForObject("select count(*) from tbl_books", new HashMap<>(0), Integer.class);
    }

    @Override
    public List<Book> getBooksByAuthorUid(long authorUid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("authorUid", authorUid);
        return jdbc.query("select b.uid, b.title, b.isbn, b.publication_year from tbl_book_author ba\n" +
                "join tbl_authors a on ba.author_uid = a.uid\n" +
                "join tbl_books b on ba.book_uid = b.uid\n" +
                "where a.uid = :authorUid", params, new BookMapper());
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
}
