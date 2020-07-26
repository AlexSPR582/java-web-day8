package com.alexander.day8.controller.command;

import com.alexander.day8.exception.CommandException;

import java.util.Map;

public interface ActionCommand {
    Map<String, Object> execute(Map<String, String> parameters) throws CommandException;
}
