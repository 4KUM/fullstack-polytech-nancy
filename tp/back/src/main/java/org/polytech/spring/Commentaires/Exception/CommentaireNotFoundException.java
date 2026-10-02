package org.polytech.spring.Commentaires.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentaireNotFoundException extends RuntimeException {
    public CommentaireNotFoundException(Long id) {
        super("Commentaire introuvable : " + id);
    }
}
