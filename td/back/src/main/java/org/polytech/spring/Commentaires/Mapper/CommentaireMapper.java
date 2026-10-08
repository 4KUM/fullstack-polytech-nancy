package org.polytech.spring.Commentaires.Mapper;

import org.polytech.spring.Commentaires.DTO.CommentaireCreationDTO;
import org.polytech.spring.Commentaires.DTO.CommentaireDTO;
import org.polytech.spring.Commentaires.Entity.Commentaire;
import org.springframework.stereotype.Component;

@Component
public class CommentaireMapper {

    public CommentaireDTO toDto(Commentaire commentaire) {
        return new CommentaireDTO(
                commentaire.getId(),
                commentaire.getAuteur(),
                commentaire.getDate(),
                commentaire.getMessage()
        );
    }

    public Commentaire toEntity(CommentaireCreationDTO dto) {
        Commentaire commentaire = new Commentaire();
        update(commentaire, dto);
        return commentaire;
    }

    public void update(Commentaire commentaire, CommentaireCreationDTO dto) {
        commentaire.setAuteur(dto.auteur());
        commentaire.setMessage(dto.message());
    }
}
