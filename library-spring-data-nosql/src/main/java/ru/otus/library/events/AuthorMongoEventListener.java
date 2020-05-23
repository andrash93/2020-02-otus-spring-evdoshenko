package ru.otus.library.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Service;
import ru.otus.library.model.Author;
import ru.otus.library.service.BookService;

@Service
public class AuthorMongoEventListener extends AbstractMongoEventListener<Author> {

    private final BookService bookService;

    public AuthorMongoEventListener(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        String authorId = event.getDocument().get("_id").toString();
        bookService.deleteBooksByAuthorId(authorId);
    }
}
