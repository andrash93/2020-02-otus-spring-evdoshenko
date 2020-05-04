package ru.otus.library.facade.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.facade.AuthorFacade;
import ru.otus.library.model.Author;
import ru.otus.library.service.AuthorService;

import java.util.List;
import java.util.Scanner;

@Service
public class AuthorFacadeImpl implements AuthorFacade {

    private final AuthorService authorService;

    public AuthorFacadeImpl(AuthorService authorService) {
        this.authorService = authorService;
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
    public void deleteAuthor(String name) {
        authorService.deleteAuthor(name);
        System.out.println("Автор успешно удален");
    }
}
