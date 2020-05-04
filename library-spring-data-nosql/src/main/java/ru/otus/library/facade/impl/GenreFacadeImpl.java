package ru.otus.library.facade.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.facade.GenreFacade;
import ru.otus.library.model.Genre;
import ru.otus.library.service.GenreService;

import java.util.List;
import java.util.Scanner;

@Service
public class GenreFacadeImpl implements GenreFacade {

    private final GenreService genreService;

    public GenreFacadeImpl(GenreService genreService) {
        this.genreService = genreService;
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
    public void deleteGenre(String id) {
        genreService.deleteGenre(id);
        System.out.println("Жанр успешно удален");
    }
}
