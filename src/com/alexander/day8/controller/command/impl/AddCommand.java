package com.alexander.day8.controller.command.impl;

import com.alexander.day8.controller.command.ActionCommand;
import com.alexander.day8.exception.CommandException;
import com.alexander.day8.exception.ServiceException;
import com.alexander.day8.service.BookService;
import com.alexander.day8.validator.RequestValidator;

import java.util.Map;

public class AddCommand implements ActionCommand {
    @Override
    public Map<String, Object> execute(Map<String, String> requestParameters)
            throws CommandException {
        RequestValidator validator = new RequestValidator();
        if (!validator.addRequestValidation(requestParameters)) {
            throw new CommandException("Invalid parameters");
        }
        BookService service = new BookService();
        String title = requestParameters.get("title");
        String pages = requestParameters.get("pages");
        String publicationYear = requestParameters.get("publicationYear");
        String authors = requestParameters.get("authors");
        Map<String, Object> response;
        try {
            response = service.addBook(title, pages, publicationYear, authors);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return response;
    }
}
