package test.alexander.day8.controller.command.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import com.alexander.day8.controller.command.impl.RemoveCommand;
import com.alexander.day8.model.ConnectionCreator;
import com.alexander.day8.exception.CommandException;
import com.alexander.day8.exception.DaoException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class RemoveCommandTest {
    final static String ADD = "INSERT INTO books (title, pages, publicationYear, authors) VALUES ('451 градус по Фаренгейту', 256, 1953, 'Рэй Брэдбери')";
    final static String SELECT_ID = "SELECT id FROM books WHERE title='451 градус по Фаренгейту'";

    RemoveCommand command;

    @BeforeTest
    public void setUp() {
        command = new RemoveCommand();
    }

    @Test
    public void executePositiveTest() {
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
                actual = command.execute(parameters);
            }
        } catch (CommandException | DaoException | SQLException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void executeNegativeTest() {
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", false);
        try {
            Map<String, String> parameters = Map.of("id", "502");
            actual = command.execute(parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (expectedExceptions = CommandException.class)
    public void executeExceptionTest() throws CommandException {
        Map<String, String> parameters = Map.of("bookId", "-20");
        command.execute(parameters);
    }
}
