package org.polytech.spring.Films.Service;

import org.polytech.spring.Films.DTO.FilmCreationDTO;
import org.polytech.spring.Films.DTO.FilmDTO;
import org.polytech.spring.Films.Entity.Film;
import org.polytech.spring.Films.Exception.FilmNotFoundException;
import org.polytech.spring.Films.Repository.FilmRepository;
import org.polytech.spring.Films.Entity.Genre;
import org.polytech.spring.Films.Mapper.FilmMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class FilmService {

    private final FilmRepository filmRepo;
    private final FilmMapper filmMapper;

    public FilmService(FilmRepository filmRepo, FilmMapper filmMapper) {
        this.filmRepo = filmRepo;
        this.filmMapper = filmMapper;
    }

    public List<FilmDTO> findAll(String realisateur, Genre genre) {
        return filmRepo.findAll().stream()
                .filter(f -> realisateur == null
                        || realisateur.equalsIgnoreCase(f.getRealisateur()))
                .filter(f -> genre == null || f.getGenre() == genre)
                .map(filmMapper::toDto)
                .toList();
    }

    public FilmDTO findById(Long id) {
        return filmMapper.toDto(getFilm(id));
    }

    private Film getFilm(Long id) {
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
        if (!filmRepo.existsById(id)) {
            throw new FilmNotFoundException(id);
        }
        filmRepo.deleteById(id);
    }

    private void validate(FilmCreationDTO dto) {
        if (dto.titre() == null || dto.titre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le titre est obligatoire");
        }
    }

    //Pour aller plus loin
    public FilmDTO create(FilmCreationDTO dto) {
        validate(dto);
        Film film = filmMapper.toEntity(dto);
        return filmMapper.toDto(filmRepo.save(film));
    }

    public FilmDTO update(Long id, FilmCreationDTO dto) {
        validate(dto);
        Film film = getFilm(id);
        // l'entité est gérée par JPA dans la transaction : les modifications sont sauvegardées automatiquement,
        // et ses commentaires sont conservés
        filmMapper.update(film, dto);
        return filmMapper.toDto(film);
    }
}
