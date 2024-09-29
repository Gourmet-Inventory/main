
package project.gourmetinventoryproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AWSInvalid extends RuntimeException {
    public AWSInvalid() {
        super("Credenciais inválidas");
    }
}
