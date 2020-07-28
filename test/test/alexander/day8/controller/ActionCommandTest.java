package test.alexander.day8.controller;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import com.alexander.day8.controller.command.ActionCommand;
import com.alexander.day8.controller.command.ActionProvider;
import com.alexander.day8.exception.CommandException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ActionCommandTest {
    ActionProvider provider;

    @BeforeClass
    public void setUp() {
        provider = new ActionProvider();
    }

    @Test
    public void defineAddCommandPositiveTest() {
        ActionCommand command = null;
        try {
            command = provider.defineCommand("ADD");
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertNotNull(command, "fail test");
    }

    @Test
    public void defineRemoveCommandPositiveTest() {
        ActionCommand command = null;
        try {
            command = provider.defineCommand("REMOVE");
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertNotNull(command, "fail test");
    }

    @Test
    public void defineFindIdCommandPositiveTest() {
        ActionCommand command = null;
        try {
            command = provider.defineCommand("FIND_ID");
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertNotNull(command, "fail test");
    }

    @Test
    public void defineFindTitleCommandPositiveTest() {
        ActionCommand command = null;
        try {
            command = provider.defineCommand("FIND_TITLE");
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertNotNull(command, "fail test");
    }

    @Test
    public void defineFindPagesCommandPositiveTest() {
        ActionCommand command = null;
        try {
            command = provider.defineCommand("FIND_PAGES");
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertNotNull(command, "fail test");
    }

    @Test
    public void defineFindPublicationYearCommandPositiveTest() {
        ActionCommand command = null;
        try {
            command = provider.defineCommand("FIND_PUBLICATION_YEAR");
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertNotNull(command, "fail test");
    }

    @Test
    public void defineFindAuthorCommandPositiveTest() {
        ActionCommand command = null;
        try {
            command = provider.defineCommand("FIND_AUTHOR");
        } catch (CommandException e) {
            fail("Exception occurred", e);
        }
        assertNotNull(command, "fail test");
    }

    @Test (expectedExceptions = CommandException.class)
    public void defineCommandExceptionTest() throws CommandException {
        provider.defineCommand("SEARCH");
    }
}
