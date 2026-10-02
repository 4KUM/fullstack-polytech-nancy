package org.polytech.spring.Commentaires.Controller;

import org.polytech.spring.Commentaires.DTO.CommentaireCreationDTO;
import org.polytech.spring.Commentaires.DTO.CommentaireDTO;
import org.polytech.spring.Commentaires.Service.CommentaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CommentaireController {

    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @GetMapping("/films/{filmId}/commentaires")
    public List<CommentaireDTO> findByFilm(@PathVariable Long filmId) {
        return commentaireService.findByFilm(filmId);
    }

    @PostMapping("/films/{filmId}/commentaires")
    public ResponseEntity<CommentaireDTO> create(@PathVariable Long filmId,
                                                 @RequestBody CommentaireCreationDTO commentaire) {
        CommentaireDTO saved = commentaireService.create(filmId, commentaire);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/commentaires/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/commentaires/{id}")
    public CommentaireDTO update(@PathVariable Long id, @RequestBody CommentaireCreationDTO commentaire) {
        return commentaireService.update(id, commentaire);
    }

    @DeleteMapping("/commentaires/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentaireService.delete(id);
        return ResponseEntity.noContent().build();
    }
}