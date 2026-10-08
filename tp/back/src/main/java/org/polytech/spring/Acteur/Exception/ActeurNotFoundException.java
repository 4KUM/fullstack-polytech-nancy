package org.polytech.spring.Acteur.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ActeurNotFoundException extends RuntimeException {
    public ActeurNotFoundException(Long id) {
        super("Acteur introuvable : " + id);
    }
}
