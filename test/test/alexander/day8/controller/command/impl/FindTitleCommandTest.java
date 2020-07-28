package test.alexander.day8.controller.command.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import com.alexander.day8.controller.command.impl.FindTitleCommand;
import com.alexander.day8.entity.Book;
import com.alexander.day8.exception.CommandException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindTitleCommandTest {
    FindTitleCommand command;
    Book book1;
    Book book2;
    Book book3;
    Book book4;

    @BeforeTest
    public void setUp() {
        command = new FindTitleCommand();
        book1 = new Book(1, "Грозовой перевал", 416, 1847, List.of("Эмили Бронте"));
        book2 = new Book(2, "Мастер и Маргарита", 480, 1966, List.of("Михаил Булгаков"));
        book3 = new Book(3, "Гордость и предубеждение", 416, 1813, List.of("Джейн Остин"));
        book4 = new Book(4, "Гарри Поттер и философский камень", 432, 1997, List.of("Джоан Роулинг"));
    }

    @Test
    public void executePositiveTest() {
        Map<String, String> parameters = Map.of("value", "Гордость и предубеждение");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book3));
        try {
            actual = command.execute(parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void executeEmptyParametersPositiveTest() {
        Map<String, String> parameters = Map.of("value", "");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", List.of(book4, book3, book1, book2));
        try {
            actual = command.execute(parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test
    public void executeNegativeTest() {
        Map<String, String> parameters = Map.of("value", "Война и мир");
        Map<String, Object> actual = null;
        Map<String, Object> expected = Map.of("response", new ArrayList<>());
        try {
            actual = command.execute(parameters);
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertEquals(actual, expected, "fail test");
    }

    @Test (expectedExceptions = CommandException.class)
    public void executeExceptionTest() throws CommandException {
        Map<String, String> parameters = Map.of("bookTitle", "Война и мир");
        command.execute(parameters);
    }
}
