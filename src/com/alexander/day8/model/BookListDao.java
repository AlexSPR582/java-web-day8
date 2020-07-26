package com.alexander.day8.model;

import com.alexander.day8.entity.Book;
import com.alexander.day8.exception.DaoException;

import java.util.List;

public interface BookListDao {
    boolean addBook(String title, int pages, int publicationYear, String authors) throws DaoException;
    boolean removeBook(int id) throws DaoException;
    List<Book> findBooksById(int... id) throws DaoException;
    List<Book> findBooksByTitle(String... title) throws DaoException;
    List<Book> findBooksByPages(int... pages) throws DaoException;
    List<Book> findBooksByPublicationYear(int... year) throws DaoException;
    List<Book> findBooksByAuthors(String... authors) throws DaoException;
}
