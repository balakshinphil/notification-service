package notificationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientExistsException extends RuntimeException {
    public ClientExistsException(String message) {
        super(message);
    }

    public ClientExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
