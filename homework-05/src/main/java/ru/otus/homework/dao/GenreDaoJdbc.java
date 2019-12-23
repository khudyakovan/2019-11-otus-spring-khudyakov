package ru.otus.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
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
    public void insert(Genre genre) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", genre.getName());
        jdbc.update("insert into tbl_genres(name) values(:name)", params);
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

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long uid = resultSet.getLong("uid");
            String name = resultSet.getString("name");
            return new Genre(uid, name);
        }
    }
}
