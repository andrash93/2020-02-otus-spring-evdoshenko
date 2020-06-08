package ru.otus.library.service.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.data.dto.BookDto;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void findBookByNameTest() {

        List<BookDto> booksByName = bookService.findBooksByName("");
        assertEquals(0, booksByName.size());

        List<Book> books = new ArrayList<>();
        books.add(new Book());

        List<BookDto> result = new ArrayList<>();
        result.add(new BookDto());

        String bookName = "книга";
        when(bookRepository.findByName(bookName)).thenReturn(books);

        List<BookDto> resultList = bookService.findBooksByName(bookName);

        assertEquals(1, resultList.size());
        verify(bookRepository, times(1)).findByName(bookName);
    }

    @Test
    public void findBookByAuthorNameTest() {

        String authorName = "автор";
        String auyhorId = "5eaf2e4b8e7c4112fd80d972";

        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setAuthorId(auyhorId);
        books.add(book);
        book = new Book();
        book.setAuthorId(auyhorId);
        books.add(book);

        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setName(authorName);
        author.setId("5eaf2e4b8e7c4112fd80d972");
        authors.add(author);

        when(authorService.getAuthorById(authorName)).thenReturn(Optional.of(author));

        List<String> authorIds = authors.stream().map(Author::getId).collect(Collectors.toList());

        when(bookRepository.findBooksByAuthorIdIn(authorIds)).thenReturn(books);

        List<BookDto> resultList = bookService.findBooksByAuthor(authorName);

        assertEquals(2, resultList.size());
    }

    @Test
    public void deleteBookTest() {
        final String id = "5eaf2e4b8e7c4112fd80d972";
        bookService.deleteBook(id);
        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    public void updateBookTest() {
        final String id = "5eaf2e4b8e7c4112fd80d972";
        Book book = new Book();
        book.setId(id);
        book.setName("name");
        book.setAuthorId("auyhorId");
        bookService.updateBook(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void saveBookTest() {
        final String id = "5eaf2e4b8e7c4112fd80d972";
        Book book = new Book();
        book.setId(id);
        book.setName("name");
        book.setAuthorId("auyhorId");
        bookService.saveBook(book);
        verify(bookRepository, times(1)).insert(book);
    }

    @Test
    public void deleteBooksByAuthorIdTest() {
        final String authorId = "5eaf2e4b8e7c4112fd80d972";
        bookService.deleteBooksByAuthorId(authorId);
        verify(bookRepository, times(1)).removeBookByAuthorId(authorId);
    }


}