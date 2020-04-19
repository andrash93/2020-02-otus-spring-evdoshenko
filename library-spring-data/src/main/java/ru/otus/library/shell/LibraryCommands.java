package ru.otus.library.shell;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.facade.LibraryFacade;

@ShellComponent
public class LibraryCommands {

    private final LibraryFacade libraryFacade;

    public LibraryCommands(LibraryFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
    }


    @ShellMethod(value = "Add author", key = {"add author", "aa"})
    public void addAuthor() {
        libraryFacade.addAuthor();
    }

    @ShellMethod(value = "find author by name", key = {"find author", "fa"})
    public void findAuthor(String name) {
        libraryFacade.findAuthorByName(name);
    }

    @ShellMethod(value = "find all author by name", key = {"find all author", "fall"})
    public void findAllAuthors() {
        libraryFacade.findAllAuthors();
    }

    @ShellMethod(value = "delete author", key = {"delete  author", "da"})
    public void deleteAuthor(Long id) {
        libraryFacade.deleteAuthor(id);
    }


    @ShellMethod(value = "Add genre", key = {"add genre", "ag"})
    public void addGenre() {
        libraryFacade.addGenre();
    }

    @ShellMethod(value = "find genre by name", key = {"find genre", "fg"})
    public void findGenre(String name) {
        libraryFacade.findGenreByName(name);
    }

    @ShellMethod(value = "find all genre", key = {"find all genre", "fallg"})
    public void findAllGenre() {
        libraryFacade.findAllGenre();
    }

    @ShellMethod(value = "delete genre", key = {"delete genre", "dg"})
    public void deleteGenre(Long id) {
        libraryFacade.deleteGenre(id);
    }


    @ShellMethod(value = "Add book", key = {"add book", "ab"})
    public void addBook() {
        libraryFacade.addBook();
    }

    @ShellMethod(value = "find book by author name", key = {"find book author", "fba"})
    public void findBookByAuthor(String name) {
        libraryFacade.findBooksByAuthor(name);
    }

    @ShellMethod(value = "find book by name", key = {"find book name", "fbn"})
    public void findBookByName(String name) {
        libraryFacade.findBookByName(name);
    }

    @ShellMethod(value = "find all book ", key = {"find all book", "fallb"})
    public void findAllBook() {
        libraryFacade.findAllBooks();
    }

    @ShellMethod(value = "delete book", key = {"delete book", "db"})
    public void deleteBook(Long id) {
        libraryFacade.deleteBook(id);
    }

    @ShellMethod(value = "update book", key = {"update book", "ub"})
    public void updateBook(Long id) {
        libraryFacade.updateBook(id);
    }

}
