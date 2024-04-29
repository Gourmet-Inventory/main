package project.gourmetinventoryproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ElementAlreadyExistException extends RuntimeException {
    public ElementAlreadyExistException() {
        super("Item já existe");
    }
}