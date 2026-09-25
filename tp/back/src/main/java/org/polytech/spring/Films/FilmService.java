package org.polytech.spring.Films;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FilmService {

    private final FilmRepo filmRepo;

    public FilmService(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    public List<Film> findAll() {
        return filmRepo.findAll();
    }

    public Film findById(Long id) {
        return filmRepo.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

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
    }

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
}
