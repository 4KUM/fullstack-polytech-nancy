package org.polytech.spring.Films.Repository;

import org.polytech.spring.Films.Entity.Film;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FilmRepo {

    private final Map<Long, Film> films = new HashMap<>();

    private long filmId;

    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    public Optional<Film> findById(Long id) {
        return Optional.ofNullable(films.get(id));
    }

    public Film save(Film film) {
        if (film.getId() == null) {
            film.setId(++filmId);
        }
        films.put(film.getId(), film);
        return film;
    }

    public boolean existsById(Long id) {
        return films.containsKey(id);
    }

    public boolean deleteById(Long id) {
        return films.remove(id) != null;
    }


}
