package org.polytech.spring.Acteur.Controller;

import org.polytech.spring.Acteur.DTO.ActeurCreationDTO;
import org.polytech.spring.Acteur.DTO.ActeurDTO;
import org.polytech.spring.Acteur.Service.ActeurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
