package ru.otus.library.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "books")
public class Book {

    @Id
    private String id;
    @DBRef
    private Genre genre;
    @DBRef
    private Author author;
    private String name;
    private Integer quantityPages;
    private String publishing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getQuantityPages() {
        return quantityPages;
    }

    public void setQuantityPages(Integer quantityPages) {
        this.quantityPages = quantityPages;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantityPages=" + quantityPages +
                ", publishing='" + publishing + '\'' +
                '}';
    }
}
