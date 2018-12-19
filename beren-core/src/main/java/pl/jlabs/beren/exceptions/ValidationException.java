package pl.jlabs.beren.exceptions;

import pl.jlabs.beren.model.ValidationResults;

public class ValidationException extends RuntimeException {
    private ValidationResults validationResults;
    private String message;

    public ValidationException(ValidationResults validationResults, String message) {
        this.validationResults = validationResults;
        this.message = message;
    }

    public ValidationResults getValidationResults() {
        return validationResults;
    }

    public String getMessage() {
        return message;
    }
}
