package ru.otus.library.changelog;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;

import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;
import ru.otus.library.data.model.Genre;


@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "000", id = "dropDB", author = "evdoshenko", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }


    @ChangeSet(order = "002", id = "init", author = "evdoshenko", runAlways = true)
    public void init(MongoTemplate template) {
        String authorId = addAuthor(template, "Пушкин", "Россия");
        String genreId = addGenre(template, "Стихи");
        addBook(template, "Стихи", authorId, genreId, "Дрофа", 5);

        authorId = addAuthor(template, "Беккет", "Англия");
        genreId = addGenre(template, "Сказки");
        addBook(template, "Книга", authorId, genreId, "Бкс", 45);

        authorId = addAuthor(template, "Лермонтов", "Россия");

        genreId = addGenre(template, "Стихи");
        addBook(template, "Книга3", authorId, genreId, "Бкс", 48);
    }

    private String addGenre(MongoTemplate template, String name) {
        Genre genre = new Genre();
        genre.setName(name);
        Genre save = template.save(genre);
        return save.getId();
    }

    private String addAuthor(MongoTemplate template, String name, String country) {
        Author author = new Author();
        author.setName(name);
        author.setCountry(country);
        Author save = template.save(author);
        return save.getId();
    }

    private void addBook(MongoTemplate template, String name, String authorId, String genreId, String publishing, Integer pages) {
        Book book = new Book();
        book.setName(name);
        book.setAuthorId(authorId);
        book.setGenreId(genreId);
        book.setPublishing(publishing);
        book.setQuantityPages(pages);
        template.save(book);
    }

}
