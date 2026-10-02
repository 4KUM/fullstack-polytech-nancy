package org.polytech.spring.Commentaires.DTO;

import java.time.LocalDate;

public record CommentaireCreationDTO(
        String auteur,
        LocalDate date,
        String message
) {
}
