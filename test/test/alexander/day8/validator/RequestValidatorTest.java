package test.alexander.day8.validator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.alexander.day8.validator.RequestValidator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RequestValidatorTest {
    RequestValidator validator;

    @BeforeTest
    public void setUp() {
        validator = new RequestValidator();
    }

    @Test
    public void addRequestValidationPositiveTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", "Философия Java");
        parameters.put("pages", "1168");
        parameters.put("publicationYear", "1998");
        parameters.put("authors", "Брюс Эккель");
        boolean condition = validator.addRequestValidation(parameters);
        assertTrue(condition);
    }

    @Test
    public void addRequestValidationNegativeTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", "Философия Java");
        parameters.put("publicationYear", "1998");
        parameters.put("authors", "Брюс Эккель");
        boolean condition = validator.addRequestValidation(parameters);
        assertFalse(condition);
    }

    @Test
    public void removeRequestValidationPositiveTest() {
        Map<String, String> parameters = Map.of("id", "2");
        boolean condition = validator.removeRequestValidation(parameters);
        assertTrue(condition);
    }

    @Test
    public void removeRequestValidationNegativeTest() {
        Map<String, String> parameters = Map.of("id12", "2");
        boolean condition = validator.removeRequestValidation(parameters);
        assertFalse(condition);
    }

    @Test
    public void findRequestValidationPositiveTest() {
        Map<String, String> parameters = Map.of("value", "347");
        boolean condition = validator.findRequestValidation(parameters);
        assertTrue(condition);
    }

    @Test
    public void findRequestValidationNegativeTest() {
        Map<String, String> parameters = Map.of("tag", "347");
        boolean condition = validator.findRequestValidation(parameters);
        assertFalse(condition);
    }
}
