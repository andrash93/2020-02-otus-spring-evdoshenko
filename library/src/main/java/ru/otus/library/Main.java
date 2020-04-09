package ru.otus.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.library.dao.BookDao;
import ru.otus.library.model.Author;
import ru.otus.library.model.Genre;
import ru.otus.library.service.LibraryService;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext cont = SpringApplication.run(Main.class);

        BookDao bookDao = cont.getBean(BookDao.class);

        System.out.println(bookDao.findBooksByAuthor("автор2"));

//        LibraryService libraryService = cont.getBean(LibraryService.class);
//
//        Author author = new Author();
//        author.setName("Автор 1");
//        author.setCountry("Россия");
//        libraryService.addAuthor(author);
//
//        libraryService.findAuthorByName("Автор 1").ifPresent(System.out::println);
//
//        Genre genre = new Genre();
//        genre.setName("Жанр 1");
//        libraryService.addGenre(genre);
//
//        libraryService.findGenreByName("Жанр 1").ifPresent(System.out::println);

    }
}