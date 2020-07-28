package test.alexander.day8.model.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

import com.alexander.day8.model.ConnectionCreator;
import com.alexander.day8.entity.Book;
import com.alexander.day8.exception.DaoException;
import com.alexander.day8.model.impl.BookListDaoImpl;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookListDaoTest {
    final static String ADD = "INSERT INTO books (title, pages, publicationYear, authors) VALUES ('451 градус по Фаренгейту', 256, 1953, 'Рэй Брэдбери')";
    final static String REMOVE = "DELETE FROM books WHERE title='451 градус по Фаренгейту'";
    final static String SELECT_ID = "SELECT id FROM books WHERE title='451 градус по Фаренгейту'";

    BookListDaoImpl dao;
    Book book1;
    Book book2;
    Book book3;
    Book book4;

    @BeforeTest
    public void setUp() {
        dao = new BookListDaoImpl();
        book1 = new Book(1, "Грозовой перевал", 416, 1847, List.of("Эмили Бронте"));
        book2 = new Book(2, "Мастер и Маргарита", 480, 1966, List.of("Михаил Булгаков"));
        book3 = new Book(3, "Гордость и предубеждение", 416, 1813, List.of("Джейн Остин"));
        book4 = new Book(4, "Гарри Поттер и философский камень", 432, 1997, List.of("Джоан Роулинг"));
    }

    @Test
    public void addBookPositiveTest() {
        boolean actualResult = false;
        ConnectionCreator creator = ConnectionCreator.getInstance();
        try (Connection cn = creator.createConnection()) {
            actualResult = dao.addBook("451 градус по Фаренгейту", 256, 1953, "Рэй Брэдбери");
            cn.createStatement().execute(REMOVE);
        } catch (DaoException | SQLException e) {
            fail("Exception occurred", e);
        }
        assertTrue(actualResult, "fail test");
    }

    @Test (expectedExceptions = DaoException.class)
    public void addBookExceptionTest() throws DaoException {
        dao.addBook("Философия Java", 1168, 1998, null);
    }

    @Test
    public void removeBookPositiveTest() {
        boolean actualResult = false;
        ConnectionCreator creator = ConnectionCreator.getInstance();
        try (Connection cn = creator.createConnection()) {
            Statement statement = cn.createStatement();
            statement.execute(ADD);
            ResultSet rs = statement.executeQuery(SELECT_ID);
            if (rs.next()) {
                int id = rs.getInt(1);
                actualResult = dao.removeBook(id);
            }
        } catch (DaoException | SQLException e) {
            fail("Exception occurred", e);
        }
        assertTrue(actualResult, "fail test");
    }

    @Test
    public void removeBooksExceptionTest() {
        boolean actualResult = false;
        try {
            actualResult = dao.removeBook(-7);
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertFalse(actualResult, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByAuthorPositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book3);
        try {
            actual = dao.findBooksByAuthors("Джейн Остин");
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByAuthorNegativeTest() {
        List<Book> actual = null;
        List<Book> expected = new ArrayList<>();
        try {
            actual = dao.findBooksByAuthors("Стивен Кинг");
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByAuthorPositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book3, book4, book2, book1);
        try {
            actual = dao.findBooksByAuthors();
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByIdPositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book3);
        try {
            actual = dao.findBooksById(3);
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByIdNegativeTest() {
        List<Book> actual = null;
        List<Book> expected = new ArrayList<>();
        try {
            actual = dao.findBooksById(10);
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByIdPositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book1, book2, book3, book4);
        try {
            actual = dao.findBooksById();
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByTitlePositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book3);
        try {
            actual = dao.findBooksByTitle("Гордость и предубеждение");
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByTitleNegativeTest() {
        List<Book> actual = null;
        List<Book> expected = new ArrayList<>();
        try {
            actual = dao.findBooksByTitle("Война и мир");
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByTitlePositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book4, book3, book1, book2);
        try {
            actual = dao.findBooksByTitle();
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByPagesPositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book1, book3);
        try {
            actual = dao.findBooksByPages(416);
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByPagesNegativeTest() {
        List<Book> actual = null;
        List<Book> expected = new ArrayList<>();
        try {
            actual = dao.findBooksByPages(1098);
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByPagesPositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book1, book3, book4, book2);
        try {
            actual = dao.findBooksByPages();
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with parameter")
    public void findBooksByPublicationYearPositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book1);
        try {
            actual = dao.findBooksByPublicationYear(1847);
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void findBooksByPublicationYearNegativeTest() {
        List<Book> actual = null;
        List<Book> expected = new ArrayList<>();
        try {
            actual = dao.findBooksByPublicationYear(1098);
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (description = "with no parameter")
    public void sortBooksByPublicationYearPositiveTest() {
        List<Book> actual = null;
        List<Book> expected = List.of(book3, book1, book2, book4);
        try {
            actual = dao.findBooksByPublicationYear();
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }
}
