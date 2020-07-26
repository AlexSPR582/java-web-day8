package com.alexander.day8.validator;

import java.util.Map;

public class RequestValidator {
    public boolean addRequestValidation(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return false;
        }
        return parameters.containsKey("title") && parameters.containsKey("pages") &&
                parameters.containsKey("publicationYear") && parameters.containsKey("authors");
    }

    public boolean removeRequestValidation(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return false;
        }
        return parameters.containsKey("id");
    }

    public boolean findRequestValidation(Map<String, String> parameters) {
        if (parameters == null) {
            return false;
        } else if (parameters.isEmpty()) {
            return true;
        }
        return parameters.containsKey("value");
    }
}
