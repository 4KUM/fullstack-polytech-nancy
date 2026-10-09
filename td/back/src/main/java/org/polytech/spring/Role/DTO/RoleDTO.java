package org.polytech.spring.Role.DTO;

import org.polytech.spring.Acteur.DTO.ActeurDTO;

public record RoleDTO(
        Long id,
        String personnage,
        Long filmId,
        String filmTitre,
        ActeurDTO acteur
) {
}
