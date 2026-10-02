package org.polytech.spring.Commentaires.DTO;

import java.time.LocalDate;

public record CommentaireDTO (
        Long id,
        String auteur,
        LocalDate date,
        String message
) {}
