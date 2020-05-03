package ru.otus.library.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Long saveAuthor(Author author) {
        if (author.getId() == null) {
            entityManager.persist(author);
        } else {
            entityManager.merge(author);
        }
        return author.getId();
    }

    @Override
    public Optional<Author> findAuthorById(Long authorId) {
        return Optional.ofNullable(entityManager.find(Author.class, authorId));
    }

    @Override
    public List<Author> findAuthorsByName(String authorName) {
        TypedQuery<Author> query = entityManager.createQuery("select a " +
                        "from Author a " +
                        "where a.name = :name",
                Author.class);
        query.setParameter("name", authorName);
        return query.getResultList();
    }

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void deleteAuthor(Long authorId) {
        Author author = entityManager.find(Author.class, authorId);
        if (author != null) {
            entityManager.remove(author);
        }
    }

}
