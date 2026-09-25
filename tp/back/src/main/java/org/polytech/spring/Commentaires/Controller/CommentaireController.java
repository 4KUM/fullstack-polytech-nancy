package org.polytech.spring.Commentaires.Controller;

import org.polytech.spring.Commentaires.Entity.Commentaire;
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
    public List<Commentaire> findByFilm(@PathVariable Long filmId) {
        return commentaireService.findByFilm(filmId);
    }

    @PostMapping("/films/{filmId}/commentaires")
    public ResponseEntity<Commentaire> create(@PathVariable Long filmId,
                                              @RequestBody Commentaire commentaire) {
        Commentaire saved = commentaireService.create(filmId, commentaire);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/commentaires/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/commentaires/{id}")
    public Commentaire update(@PathVariable Long id, @RequestBody Commentaire commentaire) {
        return commentaireService.update(id, commentaire);
    }

    @DeleteMapping("/commentaires/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentaireService.delete(id);
        return ResponseEntity.noContent().build();
    }
}