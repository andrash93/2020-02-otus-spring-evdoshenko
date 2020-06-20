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
        Author author = addAuthor(template, "Пушкин", "Россия");
        Genre genre = addGenre(template, "Стихи");
        addBook(template, "Стихи", author, genre, "Дрофа", 5);

        author = addAuthor(template, "Беккет", "Англия");
        genre = addGenre(template, "Сказки");
        addBook(template, "Книга", author, genre, "Бкс", 45);

        author = addAuthor(template, "Лермонтов", "Россия");
        genre = addGenre(template, "Стихи");
        addBook(template, "Книга3", author, genre, "Бкс", 48);
    }

    private Genre addGenre(MongoTemplate template, String name) {
        Genre genre = new Genre();
        genre.setName(name);
        Genre save = template.save(genre);
        return save;
    }

    private Author addAuthor(MongoTemplate template, String name, String country) {
        Author author = new Author();
        author.setName(name);
        author.setCountry(country);
        Author save = template.save(author);
        return save;
    }

    private void addBook(MongoTemplate template, String name, Author author, Genre genre, String publishing, Integer pages) {
        Book book = new Book();
        book.setName(name);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setPublishing(publishing);
        book.setQuantityPages(pages);
        template.save(book);
    }

}
