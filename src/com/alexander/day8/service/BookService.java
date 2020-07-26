package com.alexander.day8.service;

import com.alexander.day8.exception.DaoException;
import com.alexander.day8.exception.ServiceException;
import com.alexander.day8.model.BookListDao;
import com.alexander.day8.model.impl.BookListDaoImpl;
import com.alexander.day8.validator.BookValidator;

import java.util.HashMap;
import java.util.Map;

public class BookService {
    public Map<String, Object> addBook(String title, String pages, String publicationYear, String authors)
            throws ServiceException {
        BookValidator validator = new BookValidator();
        if (!validator.bookTitleValidation(title) ||
                !validator.bookPagesValidation(pages) ||
                !validator.bookPublicationYearValidation(publicationYear) ||
                !validator.bookAuthorsValidation(authors)) {
            throw new ServiceException("Invalid request parameters");
        }
        int bookPages = Integer.parseInt(pages);
        int bookPublicationYear = Integer.parseInt(publicationYear);
        BookListDao dao = new BookListDaoImpl();
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isAdded = dao.addBook(title, bookPages, bookPublicationYear, authors);
            response.put("response", isAdded);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return response;
    }

    public Map<String, Object> removeBook(String id)
            throws ServiceException {
        BookValidator validator = new BookValidator();
        if (!validator.bookIdValidation(id)) {
            throw new ServiceException("Invalid request parameters");
        }
        int bookId = Integer.parseInt(id);
        BookListDao dao = new BookListDaoImpl();
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isRemoved = dao.removeBook(bookId);
            response.put("response", isRemoved);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return response;
    }

    public Map<String, Object> findBooksById(String id) throws ServiceException {
        BookValidator validator = new BookValidator();
        BookListDao dao = new BookListDaoImpl();
        Map<String, Object> response = new HashMap<>();
        try {
            if (id.isEmpty()) {
                response.put("response", dao.findBooksById());
            } else if (validator.bookIdValidation(id)) {
                int bookId = Integer.parseInt(id);
                response.put("response", dao.findBooksById(bookId));
            } else {
                throw new ServiceException("Invalid request parameters");
            }
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return response;
    }

    public Map<String, Object> findBooksByTitle(String title) throws ServiceException {
        BookValidator validator = new BookValidator();
        BookListDao dao = new BookListDaoImpl();
        Map<String, Object> response = new HashMap<>();
        try {
            if (title.isEmpty()) {
                response.put("response", dao.findBooksByTitle());
            } else if (validator.bookTitleValidation(title)) {
                response.put("response", dao.findBooksByTitle(title));
            } else {
                throw new ServiceException("Invalid request parameters");
            }
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return response;
    }

    public Map<String, Object> findBooksByPages(String pages) throws ServiceException {
        BookValidator validator = new BookValidator();
        BookListDao dao = new BookListDaoImpl();
        Map<String, Object> response = new HashMap<>();
        try {
            if (pages.isEmpty()) {
                response.put("response", dao.findBooksByPages());
            } else if (validator.bookPagesValidation(pages)) {
                int bookPages = Integer.parseInt(pages);
                response.put("response", dao.findBooksByPages(bookPages));
            } else {
                throw new ServiceException("Invalid request parameters");
            }
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return response;
    }

    public Map<String, Object> findBooksByPublicationYear(String year) throws ServiceException {
        BookValidator validator = new BookValidator();
        BookListDao dao = new BookListDaoImpl();
        Map<String, Object> response = new HashMap<>();
        try {
            if (year.isEmpty()) {
                response.put("response", dao.findBooksByPublicationYear());
            } else if (validator.bookPublicationYearValidation(year)) {
                int publicationYear = Integer.parseInt(year);
                response.put("response", dao.findBooksByPublicationYear(publicationYear));
            } else {
                throw new ServiceException("Invalid request parameters");
            }
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return response;
    }

    public Map<String, Object> findBooksByAuthors(String authors) throws ServiceException {
        BookValidator validator = new BookValidator();
        BookListDao dao = new BookListDaoImpl();
        Map<String, Object> response = new HashMap<>();
        try {
            if (authors.isEmpty()) {
                response.put("response", dao.findBooksByAuthors());
            } else if (validator.bookAuthorsValidation(authors)) {
                response.put("response", dao.findBooksByAuthors(authors));
            } else {
                throw new ServiceException("Invalid request parameters");
            }
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return response;
    }
}
