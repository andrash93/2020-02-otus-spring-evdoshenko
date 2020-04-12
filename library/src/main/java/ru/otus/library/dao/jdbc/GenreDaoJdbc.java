package ru.otus.library.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.model.Author;
import ru.otus.library.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GenreDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> addGenre(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        String sql = "insert into genres (name) values (:name)";
        KeyHolder kh = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(sql, params, kh);
            genre.setId(kh.getKey().longValue());
            return Optional.of(genre);
        } catch (Exception e) {
            System.out.println("ERROR in addGenre " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Genre> findGenreById(Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        String sql = "select id, name from genres where id = :id";
        try {
            Genre genreRecord = jdbcTemplate.queryForObject(sql, params, new GenreMapper());
            return Optional.ofNullable(genreRecord);
        } catch (Exception e) {
            System.out.println("ERROR in findGenreById " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Genre> findGenreByName(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        String sql = "select id, name from genres where name = :name";
        try {
            Genre genreRecord = jdbcTemplate.queryForObject(sql, params, new GenreMapper());
            return Optional.ofNullable(genreRecord);
        } catch (Exception e) {
            System.out.println("ERROR in findGenreByName " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "select id, name from genres";
        try {
            return jdbcTemplate.query(sql, new GenreMapper());
        } catch (Exception e) {
            System.out.println("ERROR in getAllGenres " + e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean deleteGenre(Long genreId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", genreId);
        String sql = "delete from genres where id = :id";
        try {
            jdbcTemplate.update(sql, params);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR in deleteGenre " + e.getMessage());
        }
        return false;
    }

    @Override
    public Integer countGenres() {
        String sql = "select count(*) from genres";
        try {
            return jdbcTemplate.getJdbcTemplate().queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("ERROR in countGenres " + e.getMessage());
        }
        return null;
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            Genre genre = new Genre();
            genre.setId(resultSet.getLong("id"));
            genre.setName(resultSet.getString("name"));
            return genre;
        }
    }
}
