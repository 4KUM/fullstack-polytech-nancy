package org.polytech.spring.Films.DTO;

import org.polytech.spring.Acteur.DTO.ActeurDTO;
import org.polytech.spring.Films.Entity.Genre;

import java.time.LocalDate;
import java.util.List;

public record FilmDTO(
        Long id,
        String titre,
        String realisateur,
        LocalDate dateSortie,
        Genre genre,
        List<ActeurDTO> acteurs
) {
}
