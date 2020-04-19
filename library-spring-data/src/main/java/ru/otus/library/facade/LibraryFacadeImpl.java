package ru.otus.library.facade;

import org.springframework.stereotype.Service;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class LibraryFacadeImpl implements LibraryFacade {

    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;

    public LibraryFacadeImpl(AuthorService authorService, BookService bookService, GenreService genreService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
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
    public void findBooksByGenre(String genreName) {
        List<Book> booksByGenre = bookService.findBooksByGenre(genreName);
        if (!booksByGenre.isEmpty()) {
            booksByGenre.forEach(System.out::println);
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

        if (!bookService.bookExist(id)) {
            System.out.println("Книга не найдена и не может быть обновлена");
            return;
        }

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

        bookService.updateBook(book, id);
        System.out.println("Книга успешно обновлена");
    }

    @Override
    public void addAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя автора");
        String authorName = scanner.nextLine();
        System.out.println("Введите страну автора");
        String authorCountry = scanner.nextLine();

        Author author = new Author();
        author.setName(authorName);
        author.setCountry(authorCountry);

        authorService.saveAuthor(author);

        System.out.println("Автор успешно добавлен");
    }

    @Override
    public void findAuthorByName(String authorName) {
        List<Author> authorsByName = authorService.findAuthorsByName(authorName);
        if (!authorsByName.isEmpty()) {
            authorsByName.forEach(System.out::println);
        } else {
            System.out.println("Автор не найден");
        }
    }

    @Override
    public void findAllAuthors() {
        List<Author> allAuthors = authorService.findAllAuthors();
        System.out.println("Количество найденных авторов : " + allAuthors.size());
        allAuthors.forEach(System.out::println);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorService.deleteAuthor(id);
        System.out.println("Автор успешно удален");
    }

    @Override
    public void addGenre() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название жанра");
        String genreName = scanner.nextLine();

        Genre genre = new Genre();
        genre.setName(genreName);

        genreService.saveGenre(genre);

        System.out.println("Жанр успешно добавлен");
    }

    @Override
    public void findGenreByName(String name) {
        List<Genre> genreByName = genreService.findGenreByName(name);
        if (!genreByName.isEmpty()) {
            genreByName.forEach(System.out::println);
        } else {
            System.out.println("Жанр не найден");
        }
    }

    @Override
    public void findAllGenre() {
        List<Genre> allGenre = genreService.findAllGenre();
        System.out.println("Количество найденных жанров : " + allGenre.size());
        allGenre.forEach(System.out::println);
    }

    @Override
    public void deleteGenre(Long id) {
        genreService.deleteGenre(id);
        System.out.println("Жанр успешно удален");
    }
}
