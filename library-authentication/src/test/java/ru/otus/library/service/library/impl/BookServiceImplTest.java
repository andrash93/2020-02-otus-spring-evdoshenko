package ru.otus.library.service.library.impl;


import com.sun.tools.javah.Gen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.data.library.dto.BookDto;
import ru.otus.library.data.library.model.Author;
import ru.otus.library.data.library.model.Book;
import ru.otus.library.data.library.model.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.library.AuthorService;
import ru.otus.library.service.library.GenreService;

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

        Author author = new Author();
        author.setAuthorId(4L);

        Genre genre = new Genre();
        genre.setGenreId(4L);

        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setAuthor(author);
        book.setGenre(genre);
        books.add(book);

        List<BookDto> result = new ArrayList<>();
        result.add(new BookDto());

        String bookName = "книга";
        when(bookRepository.findByName(bookName)).thenReturn(books);

        List<BookDto> resultList = bookService.findBooksByName(bookName);

        assertEquals(1, resultList.size());
        verify(bookRepository, times(1)).findByName(bookName);
    }


    @Test
    public void deleteBookTest() {
        final long id = 1;
        bookService.deleteBook(id);
        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAuthorByIdTest() {
        final long id = 1;
        bookRepository.findById(id);
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    public void updateBookTest() {
        final long id = 1;
        Author author = new Author();
        author.setAuthorId(4L);
        when(authorService.getAuthorById(author.getAuthorId())).thenReturn(Optional.of(author));

        Genre genre = new Genre();
        genre.setGenreId(4L);
        when(genreService.getGenreById(genre.getGenreId())).thenReturn(Optional.of(genre));

        Book book = new Book();
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.updateBook(book);


        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void saveBookTest() {
        final long id = 1;
        Author author = new Author();
        author.setAuthorId(4L);
        when(authorService.getAuthorById(author.getAuthorId())).thenReturn(Optional.of(author));

        Genre genre = new Genre();
        genre.setGenreId(4L);
        when(genreService.getGenreById(genre.getGenreId())).thenReturn(Optional.of(genre));

        Book book = new Book();
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.saveBook(book);


        verify(bookRepository, times(1)).save(book);
    }

}