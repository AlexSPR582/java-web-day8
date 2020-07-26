package com.alexander.day8.model.impl;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alexander.day8.creator.ConnectionCreator;
import com.alexander.day8.entity.Book;
import com.alexander.day8.exception.DaoException;
import com.alexander.day8.model.BookListDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookListDaoImpl implements BookListDao {
    private static final String ADD_BOOK = "INSERT INTO books (title, pages, publicationYear, authors) VALUES (?, ?, ?, ?)";
    private static final String REMOVE_BOOK = "DELETE FROM books WHERE id=?";
    private static final String FIND_BY_ID = "SELECT id, title, pages, publicationYear, authors FROM books WHERE id=?";
    private static final String FIND_BY_TITLE = "SELECT id, title, pages, publicationYear, authors FROM books WHERE title=?";
    private static final String FIND_BY_PAGES = "SELECT id, title, pages, publicationYear, authors FROM books WHERE pages=?";
    private static final String FIND_BY_PUBLICATION_YEAR = "SELECT id, title, pages, publicationYear, authors FROM books WHERE publicationYear=?";
    private static final String FIND_BY_AUTHORS = "SELECT id, title, pages, publicationYear, authors FROM books WHERE authors=?";
    private static final String SORT_BY_ID = "SELECT id, title, pages, publicationYear, authors FROM books ORDER BY id";
    private static final String SORT_BY_TITLE = "SELECT id, title, pages, publicationYear, authors FROM books ORDER BY title";
    private static final String SORT_BY_PAGES = "SELECT id, title, pages, publicationYear, authors FROM books ORDER BY pages";
    private static final String SORT_BY_PUBLICATION_YEAR = "SELECT id, title, pages, publicationYear, authors FROM books ORDER BY publicationYear";
    private static final String SORT_BY_AUTHORS = "SELECT id, title, pages, publicationYear, authors FROM books ORDER BY authors";

    @Override
    public boolean addBook(String title, int pages, int publicationYear, String authors)
            throws DaoException {
        boolean isAdded;
        ConnectionCreator creator = new ConnectionCreator();
        try (Connection cn = creator.create()) {
            PreparedStatement addStatement = cn.prepareStatement(ADD_BOOK);
            addStatement.setString(1, title);
            addStatement.setInt(2, pages);
            addStatement.setInt(3, publicationYear);
            addStatement.setString(4, authors);
            isAdded = addStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isAdded;
    }

    @Override
    public boolean removeBook(int id) throws DaoException {
        boolean isRemoved;
        ConnectionCreator creator = new ConnectionCreator();
        try (Connection cn = creator.create()) {
            PreparedStatement removeStatement = cn.prepareStatement(REMOVE_BOOK);
            removeStatement.setInt(1, id);
            isRemoved = removeStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isRemoved;
    }

    @Override
    public List<Book> findBooksById(int... id) throws DaoException {
        List<Book> foundBooks = new ArrayList<>();
        ConnectionCreator creator = new ConnectionCreator();
        try (Connection cn = creator.create()) {
            PreparedStatement findStatement;
            if (id.length == 1) {
                findStatement = cn.prepareStatement(FIND_BY_ID);
                findStatement.setInt(1, id[0]);
            } else {
                findStatement = cn.prepareStatement(SORT_BY_ID);
            }
            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt(1);
                String title = rs.getString(2);
                int pages = rs.getInt(3);
                int publicationYear = rs.getInt(4);
                List<String> authors = Arrays.asList(rs.getString(5).split(","));
                Book book = new Book(bookId, title, pages, publicationYear, authors);
                foundBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBooksByTitle(String... title) throws DaoException {
        List<Book> foundBooks = new ArrayList<>();
        ConnectionCreator creator = new ConnectionCreator();
        try (Connection cn = creator.create()) {
            PreparedStatement findStatement;
            if (title.length == 1) {
                findStatement = cn.prepareStatement(FIND_BY_TITLE);
                findStatement.setString(1, title[0]);
            } else {
                findStatement = cn.prepareStatement(SORT_BY_TITLE);
            }
            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String bookTitle = rs.getString(2);
                int pages = rs.getInt(3);
                int publicationYear = rs.getInt(4);
                List<String> bookAuthors = Arrays.asList(rs.getString(5).split(","));
                Book book = new Book(id, bookTitle, pages, publicationYear, bookAuthors);
                foundBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBooksByPages(int... bookPages) throws DaoException {
        List<Book> foundBooks = new ArrayList<>();
        ConnectionCreator creator = new ConnectionCreator();
        try (Connection cn = creator.create()) {
            PreparedStatement findStatement;
            if (bookPages.length == 1) {
                findStatement = cn.prepareStatement(FIND_BY_PAGES);
                findStatement.setInt(1, bookPages[0]);
            } else {
                findStatement = cn.prepareStatement(SORT_BY_PAGES);
            }
            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                int pages = rs.getInt(3);
                int publicationYear = rs.getInt(4);
                List<String> authors = Arrays.asList(rs.getString(5).split(","));
                Book book = new Book(id, title, pages, publicationYear, authors);
                foundBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBooksByPublicationYear(int... year) throws DaoException {
        List<Book> foundBooks = new ArrayList<>();
        ConnectionCreator creator = new ConnectionCreator();
        try (Connection cn = creator.create()) {
            PreparedStatement findStatement;
            if (year.length == 1) {
                findStatement = cn.prepareStatement(FIND_BY_PUBLICATION_YEAR);
                findStatement.setInt(1, year[0]);
            } else {
                findStatement = cn.prepareStatement(SORT_BY_PUBLICATION_YEAR);
            }
            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                int pages = rs.getInt(3);
                int publicationYear = rs.getInt(4);
                List<String> authors = Arrays.asList(rs.getString(5).split(","));
                Book book = new Book(id, title, pages, publicationYear, authors);
                foundBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return foundBooks;
    }

    @Override
    public List<Book> findBooksByAuthors(String... authors) throws DaoException {
        List<Book> foundBooks = new ArrayList<>();
        ConnectionCreator creator = new ConnectionCreator();
        try (Connection cn = creator.create()) {
            PreparedStatement findStatement;
            if (authors.length == 1) {
                findStatement = cn.prepareStatement(FIND_BY_AUTHORS);
                findStatement.setString(1, authors[0]);
            } else {
                findStatement = cn.prepareStatement(SORT_BY_AUTHORS);
            }
            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                int pages = rs.getInt(3);
                int publicationYear = rs.getInt(4);
                List<String> bookAuthors = Arrays.asList(rs.getString(5).split(","));
                Book book = new Book(id, title, pages, publicationYear, bookAuthors);
                foundBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return foundBooks;
    }
}
