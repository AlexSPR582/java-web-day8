package test.alexander.day8.creator;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import com.alexander.day8.creator.ConnectionCreator;
import com.alexander.day8.exception.DaoException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;

public class ConnectionCreatorTest {
    ConnectionCreator creator;

    @BeforeClass
    public void setUp() {
        creator = new ConnectionCreator();
    }

    @Test
    public void createConnectionTest() {
        Connection connection = null;
        try {
            connection = creator.create();
        } catch (DaoException e) {
            fail("Exception occurred");
        }
        assertNotNull(connection, "fail test");
    }
}
