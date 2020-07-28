package test.alexander.day8.model.impl;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import com.alexander.day8.model.ConnectionCreator;
import com.alexander.day8.exception.DaoException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;

public class ConnectionCreatorTest {
    ConnectionCreator creator;

    @BeforeClass
    public void setUp() {
        creator = ConnectionCreator.getInstance();
    }

    @Test
    public void createConnectionTest() {
        Connection connection = null;
        try {
            connection = creator.createConnection();
        } catch (DaoException e) {
            fail("Exception occurred", e);
        }
        assertNotNull(connection, "fail test");
    }
}
