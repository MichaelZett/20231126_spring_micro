package de.zettsystems.netzfilm.exception.values;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponseData(UUID uuid, int status, String message, String stackTrace,
                                    List<ValidationError> errors) {

    private static record ValidationError(String field, String message) {
    }

    public void addValidationError(String field, String message) {
        errors.add(new ValidationError(field, message));
    }
}
