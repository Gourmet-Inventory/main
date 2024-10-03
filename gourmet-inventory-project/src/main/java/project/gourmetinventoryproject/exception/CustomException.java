
package project.gourmetinventoryproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CustomException extends RuntimeException {
    private int errorCode;
    public CustomException(String mensagem, int errorCode) {
        super(mensagem);
        this.errorCode = errorCode;
    }
}
