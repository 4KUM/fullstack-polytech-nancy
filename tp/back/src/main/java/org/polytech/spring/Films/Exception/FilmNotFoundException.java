package org.polytech.spring.Films.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(Long id) {
        super("Film introuvable : " + id);
    }
}


