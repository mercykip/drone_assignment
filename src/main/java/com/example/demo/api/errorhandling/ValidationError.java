package com.example.demo.api.errorhandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ValidationError {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors = new ArrayList<>();

    private final String errorMessage;

    public ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addValidationError(String error) {
        errors.add(error);
    }

    public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }

}
