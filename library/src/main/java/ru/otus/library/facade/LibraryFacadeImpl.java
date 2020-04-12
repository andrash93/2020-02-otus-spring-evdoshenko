package ru.otus.library.facade;

import org.springframework.stereotype.Service;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class LibraryFacadeImpl implements LibraryFacade {

    private final LibraryService libraryService;

    public LibraryFacadeImpl(LibraryService libraryService) {
        this.libraryService = libraryService;
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


        libraryService.addBook(book);
        System.out.println("Книга успешно добавлена");

    }

    @Override
    public void findBookByName(String bookName) {
        List<Book> booksByName = libraryService.findBooksByName(bookName);
        if (!booksByName.isEmpty()) {
            System.out.println(booksByName);
        } else {
            System.out.println("Книги не найдены");
        }
    }

    @Override
    public void findBooksByAuthor(String authorName) {
        List<Book> booksByAuthor = libraryService.findBooksByAuthor(authorName);
        booksByAuthor.forEach(System.out::println);
    }

    @Override
    public void deleteBook(Long id) {
        boolean isDelete = libraryService.deleteBook(id);
        if (isDelete) {
            System.out.println("Книга успешно удалена");
        } else {
            System.out.println("Книга не может быть удалена");
        }
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
        book.setName(bookName);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublishing(bookPublish);
        book.setQuantityPages(bookQuantityPages);

        libraryService.updateBook(book, id);
        System.out.println("Книга успешно обновлена");
    }

    @Override
    public void findAllBooks() {
        List<Book> allBooks = libraryService.findAllBooks();
        System.out.println("Количество найденных книг : " + allBooks.size());
        allBooks.forEach(System.out::println);
    }

    @Override
    public void findBooksByGenre(String genreName) {
        List<Book> booksByGenre = libraryService.findBooksByGenre(genreName);
        booksByGenre.forEach(System.out::println);
    }

    @Override
    public void findAuthorByName(String authorName) {
        List<Author> authorsByName = libraryService.findAuthorsByName(authorName);
        if (!authorsByName.isEmpty()) {
            System.out.println(authorsByName);
        } else {
            System.out.println("Автор не найден");
        }
    }

    @Override
    public void findAllAuthors() {
        List<Author> allAuthors = libraryService.findAllAuthors();
        System.out.println("Количество найденных авторов : " + allAuthors.size());
        allAuthors.forEach(System.out::println);
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

        libraryService.addAuthor(author);

        System.out.println("Автор успешно добавлен");
    }

    @Override
    public void deleteAuthor(Long id) {
        boolean isDelete = libraryService.deleteAuthor(id);
        if (isDelete) {
            System.out.println("Автор успешно удален");
        } else {
            System.out.println("Автор не может быть удален");
        }
    }

    @Override
    public void findGenreByName(String genreName) {
        Optional<Genre> genreByName = libraryService.findGenreByName(genreName);
        if (genreByName.isPresent()) {
            System.out.println(genreByName.get());
        } else {
            System.out.println("Жанр не найден");
        }
    }

    @Override
    public void findAllGenre() {
        List<Genre> allGenre = libraryService.findAllGenre();
        System.out.println("Количество найденных жанров : " + allGenre.size());
        allGenre.forEach(System.out::println);
    }

    @Override
    public void addGenre() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название жанра");
        String genreName = scanner.nextLine();

        Genre genre = new Genre();
        genre.setName(genreName);

        libraryService.addGenre(genre);

        System.out.println("Жанр успешно добавлен");
    }

    @Override
    public void deleteGenre(Long id) {
        boolean isDelete = libraryService.deleteGenre(id);
        if (isDelete) {
            System.out.println("Жанр успешно удален");
        } else {
            System.out.println("Жанр не может быть удален");
        }
    }
}
