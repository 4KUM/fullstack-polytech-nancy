package org.polytech.spring.Films.DTO;

import org.polytech.spring.Films.Entity.Genre;

import java.time.LocalDate;

public record FilmDTO(
        Long id,
        String titre,
        String realisateur,
        LocalDate dateSortie,
        Genre genre
) {
}
