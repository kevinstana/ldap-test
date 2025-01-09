package gr.hua.it21774.exceptions;

public class UserNotFoundExceptoin extends RuntimeException {
    public UserNotFoundExceptoin() {
    }

    public UserNotFoundExceptoin(String message) {
        super(message);
    }

    public UserNotFoundExceptoin(String message, Throwable cause) {
        super(message, cause);
    }
}
