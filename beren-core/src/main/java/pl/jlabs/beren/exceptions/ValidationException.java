package pl.jlabs.beren.exceptions;

public class ValidationException extends RuntimeException {
    private String message;

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
