package ru.otus.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", genre.getName());
        jdbc.update("insert into tbl_genres(name) values(:name)", params, keyHolder);
        genre.setUid(keyHolder.getKey().longValue());
        return genre;
    }

    @Override
    public void edit(Genre genre) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("uid", genre.getUid());
        params.put("name", genre.getName());
        jdbc.update("update tbl_genres set name = :name where uid = :uid", params);
    }

    @Override
    public void deleteByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        jdbc.update("delete from tbl_genres where uid = :uid", params);
    }

    @Override
    public Genre getByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        return jdbc.queryForObject("select * from tbl_genres where uid = :uid", params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from tbl_genres", new GenreMapper());
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from tbl_genres", new HashMap<>(0), Integer.class);
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
    public List<Genre> getGenresByBookUid(long bookUid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("bookUid", bookUid);
        return jdbc.query("select g.uid, g.name from tbl_book_genre bg\n" +
                "join tbl_genres g on bg.genre_uid = g.uid\n" +
                "join tbl_books b on bg.book_uid = b.uid\n" +
                "where b.uid = :bookUid", params, new GenreMapper());
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
