package test.alexander.day8.service;

import static org.testng.Assert.fail;
import static org.testng.Assert.assertEquals;

import com.alexander.day8.model.ConnectionCreator;
import com.alexander.day8.entity.Book;
import com.alexander.day8.exception.DaoException;
import com.alexander.day8.exception.ServiceException;
import com.alexander.day8.service.BookService;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookServiceTest {
    final static String ADD = "INSERT INTO books (title, pages, publicationYear, authors) VALUES ('451 градус по Фаренгейту', 256, 1953, 'Рэй Брэдбери')";
    final static String REMOVE = "DELETE FROM books WHERE title='451 градус по Фаренгейту'";
    final static String SELECT_ID = "SELECT id FROM books WHERE title='451 градус по Фаренгейту'";

    BookService bookService;
    Book book1;
    Book book2;
    Book book3;
    Book book4;

    @BeforeTest
    public void setUp() {
        bookService = new BookService();
        book1 = new Book(1, "Грозовой перевал", 416, 1847, List.of("Эмили Бронте"));
        book2 = new Book(2, "Мастер и Маргарита", 480, 1966, List.of("Михаил Булгаков"));
        book3 = new Book(3, "Гордость и предубеждение", 416, 1813, List.of("Джейн Остин"));
        book4 = new Book(4, "Гарри Поттер и философский камень", 432, 1997, List.of("Джоан Роулинг"));
    }

    @Test
    public void addBookPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", true);
        ConnectionCreator creator = ConnectionCreator.getInstance();
        try (Connection cn = creator.createConnection()) {
            actual = bookService.addBook("451 градус по Фаренгейту", "256",
                    "1953", "Рэй Брэдбери");
            cn.createStatement().execute(REMOVE);
        } catch (ServiceException | DaoException | SQLException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (expectedExceptions = ServiceException.class)
    public void addBookNegativeTest() throws ServiceException {
        bookService.addBook("451 градус по Фаренгейту", "256",
                null, "Рэй Брэдбери");
    }

    @Test
    public void removeBookPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", true);
        ConnectionCreator creator = ConnectionCreator.getInstance();
        try (Connection cn = creator.createConnection()) {
            Statement statement = cn.createStatement();
            statement.execute(ADD);
            ResultSet rs = statement.executeQuery(SELECT_ID);
            if (rs.next()) {
                int id = rs.getInt(1);
                actual = bookService.removeBook(String.valueOf(id));
            }
        } catch (ServiceException | DaoException | SQLException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void removeBookNegativeTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", false);
        try {
            actual = bookService.removeBook("502");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }


    @Test (expectedExceptions = ServiceException.class)
    public void removeBooksExceptionTest() throws ServiceException{
        bookService.removeBook("-5");
    }

    @Test (description = "with parameter")
    public void findBooksByAuthorPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3));
        try {
            actual = bookService.findBooksByAuthors("Джейн Остин");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByAuthorNegativeTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = bookService.findBooksByAuthors("Стивен Кинг");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByAuthorPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3, book4, book2, book1));
        try {
            actual = bookService.findBooksByAuthors("");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByIdPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3));
        try {
            actual = bookService.findBooksById("3");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByIdNegativeTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = bookService.findBooksById("10");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByIdPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book1, book2, book3, book4));
        try {
            actual = bookService.findBooksById("");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByTitlePositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3));
        try {
            actual = bookService.findBooksByTitle("Гордость и предубеждение");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByTitleNegativeTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = bookService.findBooksByTitle("Война и мир");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByTitlePositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book4, book3, book1, book2));
        try {
            actual = bookService.findBooksByTitle("");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByPagesPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book1, book3));
        try {
            actual = bookService.findBooksByPages("416");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByPagesNegativeTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = bookService.findBooksByPages("1098");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByPagesPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book1, book3, book4, book2));
        try {
            actual = bookService.findBooksByPages("");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByPublicationYearPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book1));
        try {
            actual = bookService.findBooksByPublicationYear("1847");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByPublicationYearNegativeTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = bookService.findBooksByPublicationYear("1098");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByPublicationYearPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3, book1, book2, book4));
        try {
            actual = bookService.findBooksByPublicationYear("");
        } catch (ServiceException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }
}
