package org.polytech.spring.Acteur.Service;

import org.polytech.spring.Acteur.DTO.ActeurCreationDTO;
import org.polytech.spring.Acteur.DTO.ActeurDTO;
import org.polytech.spring.Acteur.Entity.Acteur;
import org.polytech.spring.Acteur.Mapper.ActeurMapper;
import org.polytech.spring.Acteur.Repository.ActeurRepository;
import org.polytech.spring.Films.Entity.Film;
import org.polytech.spring.Films.Exception.FilmNotFoundException;
import org.polytech.spring.Films.Repository.FilmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ActeurService {

    private final ActeurRepository acteurRepo;
    private final FilmRepository filmRepo;
    private final ActeurMapper acteurMapper;

    public ActeurService(ActeurRepository acteurRepo, FilmRepository filmRepo, ActeurMapper acteurMapper) {
        this.acteurRepo = acteurRepo;
        this.filmRepo = filmRepo;
        this.acteurMapper = acteurMapper;
    }

    public List<ActeurDTO> findAll() {
        return acteurRepo.findAll().stream()
                .map(acteurMapper::toDto)
                .toList();
    }

    public List<ActeurDTO> findByFilm(Long filmId) {
        getFilm(filmId);
        return acteurRepo.findActeurDuFilm(filmId).stream()
                .map(acteurMapper::toDto)
                .toList();
    }

    public ActeurDTO create(Long filmId, ActeurCreationDTO dto) {
        Film film = getFilm(filmId);
        validate(dto);
        Acteur acteur = acteurRepo.save(acteurMapper.toEntity(dto));
        film.getActeurs().add(acteur);
        acteur.getFilms().add(film);
        return acteurMapper.toDto(acteur);
    }

    private Film getFilm(Long filmId) {
        return filmRepo.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException(filmId));
    }

    private void validate(ActeurCreationDTO dto) {
        if (dto.nom() == null || dto.nom().isBlank()
                || dto.prenom() == null || dto.prenom().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le nom et le prénom sont obligatoires");
        }
    }
}
