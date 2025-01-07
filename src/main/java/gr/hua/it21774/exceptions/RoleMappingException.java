package gr.hua.it21774.exceptions;

public class RoleMappingException extends RuntimeException {
    public RoleMappingException(String message) {
        super(message);
    }

    public RoleMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}