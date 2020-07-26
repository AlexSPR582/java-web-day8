package com.alexander.day8.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Book {
    private int id;
    private String title;
    private List<String> authors;
    private int pages;
    private int publicationYear;

    public Book(int id, String title, int pages, int publicationYear, List<String> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.pages = pages;
        this.publicationYear = publicationYear;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public int getPages() {
        return pages;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return id == book.id &&
                pages == book.pages &&
                publicationYear == book.publicationYear &&
                title.equals(book.title) &&
                authors.equals(book.authors);
    }

    @Override
    public int hashCode() {
        return 31 * id + title.hashCode() + authors.hashCode() + pages * 14 + publicationYear;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", authors=").append(authors);
        sb.append(", pages=").append(pages);
        sb.append(", publicationYear=").append(publicationYear);
        sb.append('}');
        return sb.toString();
    }
}
