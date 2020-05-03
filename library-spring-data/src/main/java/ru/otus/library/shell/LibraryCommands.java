package ru.otus.library.shell;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.facade.AuthorFacade;
import ru.otus.library.facade.BookFacade;
import ru.otus.library.facade.GenreFacade;

@ShellComponent
public class LibraryCommands {

    private final BookFacade bookFacade;
    private final AuthorFacade authorFacade;
    private final GenreFacade genreFacade;

    public LibraryCommands(BookFacade bookFacade, AuthorFacade authorFacade, GenreFacade genreFacade) {
        this.bookFacade = bookFacade;
        this.authorFacade = authorFacade;
        this.genreFacade = genreFacade;
    }


    @ShellMethod(value = "Add author", key = {"add author", "aa"})
    public void addAuthor() {
        authorFacade.addAuthor();
    }

    @ShellMethod(value = "find author by name", key = {"find author", "fa"})
    public void findAuthor(String name) {
        authorFacade.findAuthorByName(name);
    }

    @ShellMethod(value = "find all author by name", key = {"find all author", "fall"})
    public void findAllAuthors() {
        authorFacade.findAllAuthors();
    }

    @ShellMethod(value = "delete author", key = {"delete  author", "da"})
    public void deleteAuthor(Long id) {
        authorFacade.deleteAuthor(id);
    }


    @ShellMethod(value = "Add genre", key = {"add genre", "ag"})
    public void addGenre() {
        genreFacade.addGenre();
    }

    @ShellMethod(value = "find genre by name", key = {"find genre", "fg"})
    public void findGenre(String name) {
        genreFacade.findGenreByName(name);
    }

    @ShellMethod(value = "find all genre", key = {"find all genre", "fallg"})
    public void findAllGenre() {
        genreFacade.findAllGenre();
    }

    @ShellMethod(value = "delete genre", key = {"delete genre", "dg"})
    public void deleteGenre(Long id) {
        genreFacade.deleteGenre(id);
    }


    @ShellMethod(value = "Add book", key = {"add book", "ab"})
    public void addBook() {
        bookFacade.addBook();
    }

    @ShellMethod(value = "find book by author name", key = {"find book author", "fba"})
    public void findBookByAuthor(String name) {
        bookFacade.findBooksByAuthor(name);
    }

    @ShellMethod(value = "find book by name", key = {"find book name", "fbn"})
    public void findBookByName(String name) {
        bookFacade.findBookByName(name);
    }

    @ShellMethod(value = "find all book ", key = {"find all book", "fallb"})
    public void findAllBook() {
        bookFacade.findAllBooks();
    }

    @ShellMethod(value = "delete book", key = {"delete book", "db"})
    public void deleteBook(Long id) {
        bookFacade.deleteBook(id);
    }

    @ShellMethod(value = "update book", key = {"update book", "ub"})
    public void updateBook(Long id) {
        bookFacade.updateBook(id);
    }

}
