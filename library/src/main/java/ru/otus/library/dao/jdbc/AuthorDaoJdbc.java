package ru.otus.library.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Author> addAuthor(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        params.addValue("country", author.getCountry());
        String sql = "insert into authors (name, country) values (:name, :country)";
        KeyHolder kh = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(sql, params, kh);
            author.setId(kh.getKey().longValue());
            return Optional.of(author);
        } catch (Exception e) {
            System.out.println("ERROR in addAuthor " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> findAuthorById(Long authorId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", authorId);
        String sql = "select id, name, country from authors where id = :id";
        try {
            Author author = jdbcTemplate.queryForObject(sql, params, new AuthorMapper());
            return Optional.ofNullable(author);
        } catch (Exception e) {
            System.out.println("ERROR in findAuthorById " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Author> findAuthorsByName(String authorName) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", authorName);
        String sql = "select id, name, country from authors where name = :name";
        try {
            return jdbcTemplate.query(sql, params, new AuthorMapper());
        } catch (Exception e) {
            System.out.println("ERROR in findAuthorsByName " + e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Author> getAllAuthors() {
        String sql = "select id, name, country from authors";
        try {
            return jdbcTemplate.query(sql, new AuthorMapper());
        } catch (Exception e) {
            System.out.println("ERROR in getAllAuthors " + e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean deleteAuthor(Long authorId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", authorId);
        String sql = "delete from authors where id = :id";
        try {
            jdbcTemplate.update(sql, params);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR in deleteAuthor " + e.getMessage());
        }
        return false;
    }

    @Override
    public Integer countAuthors() {
        String sql = "select count(*) from authors";
        try {
            return jdbcTemplate.getJdbcTemplate().queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("ERROR in countAuthors " + e.getMessage());
        }
        return null;
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            Author author = new Author();
            author.setId(resultSet.getLong("id"));
            author.setName(resultSet.getString("name"));
            author.setCountry(resultSet.getString("country"));
            return author;
        }
    }
}
