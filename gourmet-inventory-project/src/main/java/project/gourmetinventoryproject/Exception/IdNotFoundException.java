package project.gourmetinventoryproject.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException() {
        super("ID não encontrado");
    }
}