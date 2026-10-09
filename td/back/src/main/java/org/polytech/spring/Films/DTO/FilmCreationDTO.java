package org.polytech.spring.Films.DTO;

import org.polytech.spring.Films.Entity.Genre;

import java.time.LocalDate;

public record FilmCreationDTO(
        String titre,
        String realisateur,
        LocalDate dateSortie,
        Genre genre,
        String affiche
) {
}
