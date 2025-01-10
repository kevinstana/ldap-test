package gr.hua.it21774.exceptions;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {

    private final HttpStatus status;
    private final String customMessage;

    public GenericException(HttpStatus status, String customMessage) {
        this.status = status;
        this.customMessage = customMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}