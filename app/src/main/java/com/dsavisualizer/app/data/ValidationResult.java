package com.dsavisualizer.app.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final List<String> errors = new ArrayList<>();

    public void addError(String error) {
        if (error != null && !error.trim().isEmpty()) {
            errors.add(error.trim());
        }
    }

    public void addAll(List<String> values) {
        if (values == null) {
            return;
        }
        for (String value : values) {
            addError(value);
        }
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public String toSingleMessage() {
        if (errors.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder("Structured content validation failed:");
        for (String error : errors) {
            builder.append("\n- ").append(error);
        }
        return builder.toString();
    }
}
