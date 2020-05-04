package ru.otus.library.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Service;
import ru.otus.library.model.Genre;
import ru.otus.library.service.BookService;

@Service
public class GenreMongoEventListener extends AbstractMongoEventListener<Genre> {

    private final BookService bookService;

    public GenreMongoEventListener(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        String genreId = event.getDocument().get("_id").toString();
        bookService.deleteBooksByGenreId(genreId);
    }
}