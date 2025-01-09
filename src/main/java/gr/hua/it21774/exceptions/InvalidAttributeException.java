package gr.hua.it21774.exceptions;

public class InvalidAttributeException extends RuntimeException {
    public InvalidAttributeException() {
    }

    public InvalidAttributeException(String message) {
        super(message);
    }

    public InvalidAttributeException(String message, Throwable cause) {
        super(message, cause);
    }
}
