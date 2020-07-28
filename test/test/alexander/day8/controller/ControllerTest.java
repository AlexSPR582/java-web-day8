package test.alexander.day8.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import com.alexander.day8.controller.Controller;
import com.alexander.day8.model.ConnectionCreator;
import com.alexander.day8.entity.Book;
import com.alexander.day8.exception.CommandException;
import com.alexander.day8.exception.DaoException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ControllerTest {
    final static String ADD = "INSERT INTO books (title, pages, publicationYear, authors) VALUES ('451 градус по Фаренгейту', 256, 1953, 'Рэй Брэдбери')";
    final static String REMOVE = "DELETE FROM books WHERE title='451 градус по Фаренгейту'";
    final static String SELECT_ID = "SELECT id FROM books WHERE title='451 градус по Фаренгейту'";

    Controller controller;
    Book book1;
    Book book2;
    Book book3;
    Book book4;

    @BeforeTest
    public void setUp() {
        controller = new Controller();
        book1 = new Book(1, "Грозовой перевал", 416, 1847, List.of("Эмили Бронте"));
        book2 = new Book(2, "Мастер и Маргарита", 480, 1966, List.of("Михаил Булгаков"));
        book3 = new Book(3, "Гордость и предубеждение", 416, 1813, List.of("Джейн Остин"));
        book4 = new Book(4, "Гарри Поттер и философский камень", 432, 1997, List.of("Джоан Роулинг"));
    }

    @Test
    public void processAddRequestPositiveTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", "451 градус по Фаренгейту");
        parameters.put("pages", "256");
        parameters.put("publicationYear", "1953");
        parameters.put("authors", "Рэй Брэдбери");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", true);
        ConnectionCreator creator = ConnectionCreator.getInstance();
        try (Connection cn = creator.createConnection()) {
            actual = controller.processRequest("ADD", parameters);
            cn.createStatement().execute(REMOVE);
        } catch (CommandException | DaoException | SQLException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processRemoveBookPositiveTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", true);
        ConnectionCreator creator = ConnectionCreator.getInstance();
        try (Connection cn = creator.createConnection()) {
            Statement statement = cn.createStatement();
            statement.execute(ADD);
            ResultSet rs = statement.executeQuery(SELECT_ID);
            if (rs.next()) {
                int id = rs.getInt(1);
                Map<String, String> parameters = Map.of("id", String.valueOf(id));
                actual = controller.processRequest("REMOVE", parameters);
            }
        } catch (CommandException | DaoException | SQLException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processRemoveBookNegativeTest()  {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", false);
        try {
            Map<String, String> parameters = Map.of("id", "502");
            actual = controller.processRequest("REMOVE", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByAuthorPositiveTest() {
        Map<String, String> parameters = Map.of("value", "Джейн Остин");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3));
        try {
            actual = controller.processRequest("FIND_AUTHOR", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByAuthorNegativeTest() {
        Map<String, String> parameters = Map.of("value", "Стивен Кинг");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = controller.processRequest("FIND_AUTHOR", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processSortBooksByAuthorPositiveTest() {
        Map<String, String> parameters = Map.of("value", "");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3, book4, book2, book1));
        try {
            actual = controller.processRequest("FIND_AUTHOR", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByIdPositiveTest() {
        Map<String, String> parameters = Map.of("value", "3");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3));
        try {
            actual = controller.processRequest("FIND_ID", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByIdNegativeTest() {
        Map<String, String> parameters = Map.of("value", "30");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = controller.processRequest("FIND_ID", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processSortBooksByIdNegativeTest() {
        Map<String, String> parameters = Map.of("value", "");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book1, book2, book3, book4));
        try {
            actual = controller.processRequest("FIND_ID", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByPagesPositiveTest() {
        Map<String, String> parameters = Map.of("value", "416");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book1, book3));
        try {
            actual = controller.processRequest("FIND_PAGES", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByPagesNegativeTest() {
        Map<String, String> parameters = Map.of("value", "978");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = controller.processRequest("FIND_PAGES", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processSortBooksByPagesNegativeTest() {
        Map<String, String> parameters = Map.of("value", "");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book1, book3, book4, book2));
        try {
            actual = controller.processRequest("FIND_PAGES", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByPublicationYearPositiveTest() {
        Map<String, String> parameters = Map.of("value", "1847");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book1));
        try {
            actual = controller.processRequest("FIND_PUBLICATION_YEAR", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByPublicationYearNegativeTest() {
        Map<String, String> parameters = Map.of("value", "1645");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = controller.processRequest("FIND_PUBLICATION_YEAR", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processSortBooksByPublicationYearNegativeTest() {
        Map<String, String> parameters = Map.of("value", "");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3, book1, book2, book4));
        try {
            actual = controller.processRequest("FIND_PUBLICATION_YEAR", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByTitlePositiveTest() {
        Map<String, String> parameters = Map.of("value", "Гордость и предубеждение");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3));
        try {
            actual = controller.processRequest("FIND_TITLE", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processFindBooksByTitleNegativeTest() {
        Map<String, String> parameters = Map.of("value", "Война и мир");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = controller.processRequest("FIND_TITLE", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void processSortBooksByTitleNegativeTest() {
        Map<String, String> parameters = Map.of("value", "");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book4, book3, book1, book2));
        try {
            actual = controller.processRequest("FIND_TITLE", parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test(expectedExceptions = CommandException.class)
    public void processRequestExceptionTest() throws CommandException {
        Map<String, String> parameters = Map.of("value", "BookName");
        controller.processRequest("UPDATE", parameters);
    }
}
