package com.alexander.day8.validator;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookValidator {
    private static final int MIN_PAGES = 1;
    private static final int MAX_PAGES = 10000;
    private static final int MIN_PUBLICATION_YEAR = 1;
    private static final int MAX_PUBLICATION_YEAR = LocalDate.now().getYear();
    private static final int MIN_AUTHORS = 1;
    private static final int MAX_AUTHORS = 30;
    private static final int MIN_ID = 1;

    private static final String AUTHOR_REGEX = "^[\\p{Alpha}а-яА-Я\\s.]+$";

    public boolean bookIdValidation(String id) {
        if (id == null) {
            return false;
        }
        int bookId;
        try {
            bookId = Integer.parseInt(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return bookId >= MIN_ID;
    }

    public boolean bookTitleValidation(String title) {
        return title != null && !title.isBlank();
    }

    public boolean bookAuthorsValidation(String authors) {
        if (authors == null || authors.isBlank()) {
            return false;
        }
        String[] bookAuthors = authors.split(",");
        Pattern pattern = Pattern.compile(AUTHOR_REGEX);
        for (String author: bookAuthors) {
            Matcher matcher = pattern.matcher(author);
            if (!matcher.find()) {
                return false;
            }
        }
        return bookAuthors.length >= MIN_AUTHORS && bookAuthors.length <= MAX_AUTHORS;
    }

    public boolean bookPagesValidation(String pages) {
        if (pages == null || pages.isBlank()) {
            return false;
        }
        int bookPages;
        try {
            bookPages = Integer.parseInt(pages);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return bookPages >= MIN_PAGES && bookPages <= MAX_PAGES;
    }

    public boolean bookPublicationYearValidation(String publicationYear) {
        if (publicationYear == null || publicationYear.isBlank()) {
            return false;
        }
        int bookPublicationYear;
        try {
            bookPublicationYear = Integer.parseInt(publicationYear);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return bookPublicationYear >= MIN_PUBLICATION_YEAR &&
                bookPublicationYear <= MAX_PUBLICATION_YEAR;
    }
}
