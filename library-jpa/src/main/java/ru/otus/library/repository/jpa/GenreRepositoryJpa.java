package ru.otus.library.repository.jpa;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Long saveGenre(Genre genre) {
        if (genre.getId() == null) {
            entityManager.persist(genre);
        } else {
            entityManager.merge(genre);
        }
        return genre.getId();
    }

    @Override
    public Optional<Genre> findGenreById(Long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public List<Genre> findGenresByName(String genreName) {
        TypedQuery<Genre> query = entityManager.createQuery("select g " +
                        "from Genre g " +
                        "where g.name = :name",
                Genre.class);
        query.setParameter("name", genreName);
        return query.getResultList();
    }

    @Override
    public List<Genre> getAllGenres() {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteGenre(Long genreId) {
        Genre genre = entityManager.find(Genre.class, genreId);
        if (genre != null) {
            entityManager.remove(genre);
        }
    }
}
