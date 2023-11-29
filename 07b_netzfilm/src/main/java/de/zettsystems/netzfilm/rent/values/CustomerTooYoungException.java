package de.zettsystems.netzfilm.rent.values;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomerTooYoungException extends ResponseStatusException {
    public CustomerTooYoungException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Customer too young");
    }
}
