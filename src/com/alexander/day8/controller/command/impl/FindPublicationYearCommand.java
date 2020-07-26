package com.alexander.day8.controller.command.impl;

import com.alexander.day8.controller.command.ActionCommand;
import com.alexander.day8.exception.CommandException;
import com.alexander.day8.exception.ServiceException;
import com.alexander.day8.service.BookService;
import com.alexander.day8.validator.RequestValidator;

import java.util.Map;

public class FindPublicationYearCommand implements ActionCommand {
    @Override
    public Map<String, Object> execute(Map<String, String> requestParameters)
            throws CommandException {
        RequestValidator validator = new RequestValidator();
        if (!validator.findRequestValidation(requestParameters)) {
            throw new CommandException("Invalid Parameters");
        }
        String pages = requestParameters.get("value");
        BookService service = new BookService();
        Map<String, Object> response;
        try {
            response = service.findBooksByPublicationYear(pages);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return response;
    }
}
