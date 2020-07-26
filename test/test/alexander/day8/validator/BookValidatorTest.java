package test.alexander.day8.validator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.alexander.day8.validator.BookValidator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BookValidatorTest {
    BookValidator validator;

    @BeforeTest
    public void setUp() {
        validator = new BookValidator();
    }

    @Test
    public void bookIdValidationPositiveTest() {
        String id = "2";
        boolean condition = validator.bookIdValidation(id);
        assertTrue(condition, "fail test");
    }

    @Test
    public void bookIdValidationNegativeTest() {
        String id = "A";
        boolean condition = validator.bookIdValidation(id);
        assertFalse(condition, "fail test");
    }

    @Test
    public void bookTitleValidationPositiveTest() {
        String title = "Война и мир";
        boolean condition = validator.bookTitleValidation(title);
        assertTrue(condition, "fail test");
    }

    @Test
    public void bookTitleValidationNegativeTest() {
        boolean condition = validator.bookTitleValidation("");
        assertFalse(condition, "fail test");
    }

    @Test
    public void bookPagesValidationPositiveTest() {
        String pages = "234";
        boolean condition = validator.bookPagesValidation(pages);
        assertTrue(condition, "fail test");
    }

    @Test
    public void bookPagesValidationNegativeTest() {
        String pages = "-234";
        boolean condition = validator.bookPagesValidation(pages);
        assertFalse(condition, "fail test");
    }

    @Test
    public void bookPublicationYearValidationPositiveTest() {
        String year = "1934";
        boolean condition = validator.bookPublicationYearValidation(year);
        assertTrue(condition, "fail test");
    }

    @Test
    public void bookPublicationYearValidationNegativeTest() {
        String year = "-234";
        boolean condition = validator.bookPublicationYearValidation(year);
        assertFalse(condition, "fail test");
    }

    @Test
    public void bookAuthorsValidationPositiveTest() {
        String authors = "Михаил Булгаков,Оскар Уайльд";
        boolean condition = validator.bookAuthorsValidation(authors);
        assertTrue(condition, "fail test");
    }

    @Test
    public void bookAuthorsValidationNegativeTest() {
        String authors = "454QWE!2";
        boolean condition = validator.bookAuthorsValidation(authors);
        assertFalse(condition, "fail test");
    }
}
