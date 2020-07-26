package com.alexander.day8.controller.command;

import com.alexander.day8.exception.CommandException;

public class ActionProvider {
    public ActionCommand defineCommand(String command) throws CommandException {
        if (command == null || command.isBlank()) {
            throw new CommandException("Invalid Command");
        }
        ActionCommand commandType;
        try {
            commandType = CommandType.valueOf(command).getCommand();
        } catch (IllegalArgumentException e) {
            throw new CommandException("No such command");
        }
        return commandType;
    }
}
