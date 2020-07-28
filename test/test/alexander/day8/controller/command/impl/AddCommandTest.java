package test.alexander.day8.controller.command.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import com.alexander.day8.controller.command.impl.AddCommand;
import com.alexander.day8.model.ConnectionCreator;
import com.alexander.day8.exception.CommandException;
import com.alexander.day8.exception.DaoException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddCommandTest {
    final static String REMOVE = "DELETE FROM books WHERE title='451 градус по Фаренгейту'";

    AddCommand command;

    @BeforeTest
    public void setUp() {
        command = new AddCommand();
    }

    @Test
    public void executePositiveTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", "451 градус по Фаренгейту");
        parameters.put("pages", "256");
        parameters.put("publicationYear", "1953");
        parameters.put("authors", "Рэй Брэдбери");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", true);
        ConnectionCreator creator = ConnectionCreator.getInstance();
        try (Connection cn = creator.createConnection()) {
            actual = command.execute(parameters);
            cn.createStatement().execute(REMOVE);
        } catch (CommandException | DaoException | SQLException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (expectedExceptions = CommandException.class)
    public void executeExceptionTest() throws CommandException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", "451 градус по Фаренгейту");
        parameters.put("publicationYear", "1953");
        parameters.put("authors", "Рэй Брэдбери");
        command.execute(parameters);
    }
}
