package org.polytech.spring.Acteur.Controller;

import org.polytech.spring.Acteur.DTO.ActeurCreationDTO;
import org.polytech.spring.Acteur.DTO.ActeurDTO;
import org.polytech.spring.Acteur.Service.ActeurService;
import org.polytech.spring.Films.DTO.FilmDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ActeurController {

    private final ActeurService acteurService;

    public ActeurController(ActeurService acteurService) {
        this.acteurService = acteurService;
    }

    @GetMapping("/acteurs")
    public List<ActeurDTO> findAll() {
        return acteurService.findAll();
    }

    @GetMapping("/acteurs/{id}")
    public ActeurDTO findById(@PathVariable Long id) {
        return acteurService.findById(id);
    }

    @PostMapping("/acteurs")
    public ResponseEntity<ActeurDTO> create(@RequestBody ActeurCreationDTO acteur) {
        ActeurDTO saved = acteurService.create(acteur);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/acteurs/{id}")
    public ActeurDTO update(@PathVariable Long id, @RequestBody ActeurCreationDTO acteur) {
        return acteurService.update(id, acteur);
    }

    @DeleteMapping("/acteurs/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        acteurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/acteurs/{id}/films")
    public List<FilmDTO> findFilms(@PathVariable Long id) {
        return acteurService.findFilms(id);
    }

    @GetMapping("/films/{filmId}/acteurs")
    public List<ActeurDTO> findByFilm(@PathVariable Long filmId) {
        return acteurService.findByFilm(filmId);
    }

    @PostMapping("/films/{filmId}/acteurs")
    public ResponseEntity<ActeurDTO> create(@PathVariable Long filmId,
                                            @RequestBody ActeurCreationDTO acteur) {
        ActeurDTO saved = acteurService.create(filmId, acteur);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/films/{filmId}/acteurs/{acteurId}")
    public ResponseEntity<Void> associer(@PathVariable Long filmId, @PathVariable Long acteurId) {
        acteurService.associer(filmId, acteurId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/films/{filmId}/acteurs/{acteurId}")
    public ResponseEntity<Void> dissocier(@PathVariable Long filmId, @PathVariable Long acteurId) {
        acteurService.dissocier(filmId, acteurId);
        return ResponseEntity.noContent().build();
    }
}
