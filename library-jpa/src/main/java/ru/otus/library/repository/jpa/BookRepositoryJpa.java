package ru.otus.library.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Long saveBook(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
        } else {
            entityManager.merge(book);
        }
        return book.getId();
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> findBooksByName(String name) {
        TypedQuery<Book> query = entityManager.createQuery("select b " +
                        "from Book b " +
                        "where b.name = :name",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityManager.remove(book);
        }
    }


}
