package org.polytech.spring.Films;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/films")
public class Controller {

    private final FilmService filmService;

    public Controller(FilmService filmService) {
        this.filmService = filmService;
    }

    //GET /films
    @GetMapping
    public List<Film> getAll() {
        return filmService.findAll();
    }

    //GET /films/1
    @GetMapping("/{id:\\d+}")
    public Film getById(@PathVariable Long id) {
        return filmService.findById(id);
    }

    //POST /films@RestController
    @PostMapping
    public ResponseEntity<Film> create(@RequestBody Film film) {
        Film saved = filmService.create(film);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }
    //PUT /films/1
    @PutMapping("/{id:\\d+}")
    public Film update(@PathVariable Long id, @RequestBody Film film) {
        return filmService.update(id, film);
    }

    //DELETE /films/1 -
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}