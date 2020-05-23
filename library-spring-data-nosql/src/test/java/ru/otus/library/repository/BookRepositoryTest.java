package ru.otus.library.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;

import java.util.List;

import static org.junit.Assert.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class BookRepositoryTest {


    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findByNameTest() {
        String authorName = "автор2";
        String bookName = "книга2";

        Author author = new Author();
        author.setName(authorName);
        Author saveAuthor = authorRepository.save(author);

        Book book = new Book();
        book.setName(bookName);
        book.setAuthorId(saveAuthor.getId());
        bookRepository.save(book);

        List<Book> books = bookRepository.findByName(bookName);
        assertEquals(books.size(), 1);

        bookRepository.removeBookByAuthorId(saveAuthor.getId());

        books = bookRepository.findByName(bookName);
        assertEquals(books.size(), 0);
    }
}