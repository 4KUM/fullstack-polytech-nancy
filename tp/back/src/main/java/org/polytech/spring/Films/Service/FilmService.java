package org.polytech.spring.Films.Service;

import org.polytech.spring.Films.Entity.Film;
import org.polytech.spring.Films.Exception.FilmNotFoundException;
import org.polytech.spring.Films.Repository.FilmRepo;
import org.polytech.spring.Films.Entity.Genre;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {

    private final FilmRepo filmRepo;

    public FilmService(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    public List<Film> findAll(String realisateur, Genre genre) {
        return filmRepo.findAll().stream()
                .filter(f -> realisateur == null
                        || realisateur.equalsIgnoreCase(f.getRealisateur()))
                .filter(f -> genre == null || f.getGenre() == genre)
                .toList();
    }

    public Film findById(Long id) {
        return filmRepo.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    /*
    public Film create(Film film) {

        validate(film);
        film.setId(null);
        return filmRepo.save(film);
    }

    public Film update(Long id, Film film) {
        if (!filmRepo.existsById(id)) {
            throw new FilmNotFoundException(id);
        }
        validate(film);
        film.setId(id);
        return filmRepo.save(film);
    } */

    public void delete(Long id) {
        if (!filmRepo.deleteById(id)) {
            throw new FilmNotFoundException(id);
        }
    }

    private void validate(Film film) {
        if (film.getTitre() == null || film.getTitre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le titre est obligatoire");
        }
    }

    //Pour aller plus loin
    public Film create(Film film) {
        validate(film);
        film.setId(null);
        film.setCommentaires(new ArrayList<>());
        return filmRepo.save(film);
    }

    public Film update(Long id, Film film) {
        Film existant = filmRepo.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
        validate(film);
        film.setId(id);
        film.setCommentaires(existant.getCommentaires());
        return filmRepo.save(film);
    }
}
