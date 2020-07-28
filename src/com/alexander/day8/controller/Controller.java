package com.alexander.day8.controller;

import com.alexander.day8.controller.command.ActionProvider;
import com.alexander.day8.controller.command.ActionCommand;
import com.alexander.day8.exception.CommandException;

import java.util.Map;

public class Controller {
    public Map<String, Object> processRequest(String commandType, Map<String, String> requestParameters)
            throws CommandException {
        ActionProvider provider = new ActionProvider();
        ActionCommand command = provider.defineCommand(commandType);
        return command.execute(requestParameters);
    }
}
