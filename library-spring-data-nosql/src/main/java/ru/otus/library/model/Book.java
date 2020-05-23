package ru.otus.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String authorId;
    private String genreId;
    private String name;
    private Integer quantityPages;
    private String publishing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", authorId='" + authorId + '\'' +
                ", genreId='" + genreId + '\'' +
                ", name='" + name + '\'' +
                ", quantityPages=" + quantityPages +
                ", publishing='" + publishing + '\'' +
                '}';
    }
}
