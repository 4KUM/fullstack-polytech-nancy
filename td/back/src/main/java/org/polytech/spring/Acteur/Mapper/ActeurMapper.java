package org.polytech.spring.Acteur.Mapper;

import org.polytech.spring.Acteur.DTO.ActeurCreationDTO;
import org.polytech.spring.Acteur.DTO.ActeurDTO;
import org.polytech.spring.Acteur.Entity.Acteur;
import org.springframework.stereotype.Component;

@Component
public class ActeurMapper {

    public ActeurDTO toDto(Acteur acteur) {
        return new ActeurDTO(
                acteur.getId(),
                acteur.getNom(),
                acteur.getPrenom()
        );
    }

    public Acteur toEntity(ActeurCreationDTO dto) {
        Acteur acteur = new Acteur();
        update(acteur, dto);
        return acteur;
    }

    public void update(Acteur acteur, ActeurCreationDTO dto) {
        acteur.setNom(dto.nom());
        acteur.setPrenom(dto.prenom());
    }
}
