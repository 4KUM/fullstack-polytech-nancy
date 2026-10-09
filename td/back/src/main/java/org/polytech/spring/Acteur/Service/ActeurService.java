package org.polytech.spring.Acteur.Service;

import org.polytech.spring.Acteur.DTO.ActeurCreationDTO;
import org.polytech.spring.Acteur.DTO.ActeurDTO;
import org.polytech.spring.Acteur.Entity.Acteur;
import org.polytech.spring.Acteur.Exception.ActeurNotFoundException;
import org.polytech.spring.Acteur.Mapper.ActeurMapper;
import org.polytech.spring.Acteur.Repository.ActeurRepository;
import org.polytech.spring.Films.DTO.FilmDTO;
import org.polytech.spring.Films.Entity.Film;
import org.polytech.spring.Films.Exception.FilmNotFoundException;
import org.polytech.spring.Films.Mapper.FilmMapper;
import org.polytech.spring.Films.Repository.FilmRepository;
import org.polytech.spring.Role.Repository.RoleRepository;
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
    private final FilmMapper filmMapper;
    private final RoleRepository roleRepo;

    public ActeurService(ActeurRepository acteurRepo, FilmRepository filmRepo,
                         ActeurMapper acteurMapper, FilmMapper filmMapper,
                         RoleRepository roleRepo) {
        this.acteurRepo = acteurRepo;
        this.filmRepo = filmRepo;
        this.acteurMapper = acteurMapper;
        this.filmMapper = filmMapper;
        this.roleRepo = roleRepo;
    }

    public List<ActeurDTO> findAll() {
        return acteurRepo.findAll().stream()
                .map(acteurMapper::toDto)
                .toList();
    }

    public ActeurDTO findById(Long id) {
        return acteurMapper.toDto(getActeur(id));
    }

    public ActeurDTO create(ActeurCreationDTO dto) {
        validate(dto);
        return acteurMapper.toDto(acteurRepo.save(acteurMapper.toEntity(dto)));
    }

    public ActeurDTO update(Long id, ActeurCreationDTO dto) {
        Acteur acteur = getActeur(id);
        validate(dto);
        acteurMapper.update(acteur, dto);
        return acteurMapper.toDto(acteur);
    }

    public void delete(Long id) {
        Acteur acteur = getActeur(id);
        for (Film film : acteur.getFilms()) {
            film.getActeurs().remove(acteur);
        }
        acteurRepo.delete(acteur);
    }

    public List<FilmDTO> findFilms(Long acteurId) {
        getActeur(acteurId);
        return filmRepo.findFilmsDeLActeur(acteurId).stream()
                .map(filmMapper::toDto)
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

    public void associer(Long filmId, Long acteurId) {
        Film film = getFilm(filmId);
        Acteur acteur = getActeur(acteurId);
        film.getActeurs().add(acteur);
        acteur.getFilms().add(film);
    }

    public void dissocier(Long filmId, Long acteurId) {
        Film film = getFilm(filmId);
        Acteur acteur = getActeur(acteurId);
        film.getActeurs().remove(acteur);
        acteur.getFilms().remove(film);
        roleRepo.deleteByFilmIdAndActeurId(filmId, acteurId);
    }

    private Acteur getActeur(Long id) {
        return acteurRepo.findById(id)
                .orElseThrow(() -> new ActeurNotFoundException(id));
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
