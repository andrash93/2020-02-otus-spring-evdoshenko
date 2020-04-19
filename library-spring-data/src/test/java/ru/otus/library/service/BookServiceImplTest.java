package ru.otus.library.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.model.Book;
import ru.otus.library.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void findBookByName() {
        List<Book> booksByName = bookService.findBooksByName("");
        assertEquals(0, booksByName.size());

        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        String bookName = "книга тест";
        when(bookRepository.findByName(bookName)).thenReturn(books);

        List<Book> resultList = bookService.findBooksByName(bookName);

        assertEquals(3, resultList.size());
        verify(bookRepository, times(1)).findByName(bookName);
    }

    @Test
    public void findBookByAuthorName() {
        List<Book> booksByName = bookService.findBooksByAuthor("");
        assertEquals(0, booksByName.size());

        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        String authorName = "автор";
        when(bookRepository.findByAuthor_Name(authorName)).thenReturn(books);

        List<Book> resultList = bookService.findBooksByAuthor(authorName);

        assertEquals(3, resultList.size());
        verify(bookRepository, times(1)).findByAuthor_Name(authorName);
    }

    @Test
    public void deleteBook() {
        final long id = 1;
        bookService.deleteBook(id);
        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    public void updateBook() {
        final long id = 1;
        Book book = new Book();
        bookService.updateBook(book, id);
        verify(bookRepository, times(1)).save(book);
    }


}