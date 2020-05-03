package ru.otus.library.facade.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.facade.BookFacade;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.service.BookService;

import java.util.List;
import java.util.Scanner;

@Service
public class BookFacadeImpl implements BookFacade {

    private final BookService bookService;

    public BookFacadeImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название книги");
        String bookName = scanner.nextLine();
        System.out.println("Введите id автора книги");
        String bookAuthorId = scanner.nextLine();
        System.out.println("Введите id жанра книги");
        String bookGenreId = scanner.nextLine();

        System.out.println("Введите издательство книги");
        String bookPublish = scanner.nextLine();
        System.out.println("Введите количество страниц книги");
        Integer bookQuantityPages = Integer.parseInt(scanner.nextLine());

        Author author = new Author();
        author.setId(Long.parseLong(bookAuthorId));
        Genre genre = new Genre();
        genre.setId(Long.parseLong(bookGenreId));

        Book book = new Book();
        book.setName(bookName);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublishing(bookPublish);
        book.setQuantityPages(bookQuantityPages);

        bookService.saveBook(book);
        System.out.println("Книга успешно добавлена");
    }

    @Override
    public void findBookByName(String name) {
        List<Book> booksByName = bookService.findBooksByName(name);
        if (!booksByName.isEmpty()) {
            booksByName.forEach(System.out::println);
        } else {
            System.out.println("Книги не найдены");
        }
    }

    @Override
    public void findBooksByAuthor(String authorName) {
        List<Book> booksByAuthor = bookService.findBooksByAuthor(authorName);
        if (!booksByAuthor.isEmpty()) {
            booksByAuthor.forEach(System.out::println);
        } else {
            System.out.println("Книги не найдены");
        }
    }

    @Override
    public void findAllBooks() {
        List<Book> allBooks = bookService.findAllBooks();
        System.out.println("Количество найденных книг : " + allBooks.size());
        allBooks.forEach(System.out::println);
    }

    @Override
    public void deleteBook(Long id) {
        bookService.deleteBook(id);
        System.out.println("Книга успешно удалена");
    }

    @Override
    public void updateBook(Long id) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новое название книги");
        String bookName = scanner.nextLine();
        System.out.println("Введите новое id автора книги");
        String bookAuthorId = scanner.nextLine();
        System.out.println("Введите новое id жанра книги");
        String bookGenreId = scanner.nextLine();

        System.out.println("Введите новое издательство книги");
        String bookPublish = scanner.nextLine();
        System.out.println("Введите новое количество страниц книги");
        Integer bookQuantityPages = Integer.parseInt(scanner.nextLine());

        Author author = new Author();
        author.setId(Long.parseLong(bookAuthorId));
        Genre genre = new Genre();
        genre.setId(Long.parseLong(bookGenreId));

        Book book = new Book();
        book.setId(id);
        book.setName(bookName);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublishing(bookPublish);
        book.setQuantityPages(bookQuantityPages);

        bookService.updateBook(book);
        System.out.println("Книга успешно обновлена");
    }
}
