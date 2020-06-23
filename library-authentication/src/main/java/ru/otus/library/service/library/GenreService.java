package ru.otus.library.service.library;



import ru.otus.library.data.library.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    void saveGenre(Genre genre);

    List<Genre> findAllGenre();

    void deleteGenre(Long id);

    Optional<Genre> getGenreById(Long id);

    void updateGenre(Genre genre);
}
