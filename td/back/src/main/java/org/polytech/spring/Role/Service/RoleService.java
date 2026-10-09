package org.polytech.spring.Role.Service;

import org.polytech.spring.Acteur.Entity.Acteur;
import org.polytech.spring.Acteur.Exception.ActeurNotFoundException;
import org.polytech.spring.Acteur.Repository.ActeurRepository;
import org.polytech.spring.Films.DTO.FilmCreationDTO;
import org.polytech.spring.Films.Entity.Film;
import org.polytech.spring.Films.Exception.FilmNotFoundException;
import org.polytech.spring.Films.Repository.FilmRepository;
import org.polytech.spring.Role.DTO.RoleCreationDTO;
import org.polytech.spring.Role.DTO.RoleDTO;
import org.polytech.spring.Role.Entity.Role;
import org.polytech.spring.Role.Exception.RoleNotFoundException;
import org.polytech.spring.Role.Mapper.RoleMapper;
import org.polytech.spring.Role.Repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepo;
    private final FilmRepository filmRepo;
    private final ActeurRepository acteurRepo;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepo, FilmRepository filmRepo,
                       ActeurRepository acteurRepo, RoleMapper roleMapper) {
        this.roleRepo = roleRepo;
        this.filmRepo = filmRepo;
        this.acteurRepo = acteurRepo;
        this.roleMapper = roleMapper;
    }

    private void validate(RoleCreationDTO dto) {
        if (dto.personnage() == null || dto.personnage().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le Personnage est obligatoire");
        }

        if (dto.acteurId() ==  null || dto.acteurId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le Acteur est obligatoire");
        }
    }

    public List<RoleDTO> findAll() {
        return roleRepo.findAll().stream().map(roleMapper::toDto).toList();
    }

    public RoleDTO findById(Long id) {
        return roleMapper.toDto(roleRepo.findById(id).orElseThrow(
                () -> new RoleNotFoundException(id)
        ));
    }

    public List<RoleDTO> findByFilm(Long filmId) {
        getFilm(filmId);
        return  roleRepo.findByFilmId(filmId).stream().map(roleMapper::toDto).toList();
    }

    public List<RoleDTO> findByActeur(Long acteurId) {
        getActeur(acteurId);
        return roleRepo.findByActeurId(acteurId).stream().map(roleMapper::toDto).toList();
    }

    public RoleDTO create(Long filmId, RoleCreationDTO dto) {
        Film film = filmRepo.findById(filmId).orElseThrow(
                () -> new FilmNotFoundException(filmId)
        );
        validate(dto);
        Acteur acteur = getActeur(dto.acteurId());
        associer(film, acteur);
        Role role ;
        role= roleRepo.save(new Role(film, acteur, dto.personnage()));
        return roleMapper.toDto(role);
    }

    public RoleDTO update(Long id, RoleCreationDTO dto) {
        Role role = getRole(id);
        validate(dto);
        if (!dto.acteurId().equals(role.getActeur().getId())) {
            Acteur acteur = getActeur(dto.acteurId());
            associer(role.getFilm(), acteur);
            role.setActeur(acteur);
        }
        role.setPersonnage(dto.personnage());
        return roleMapper.toDto(role);
    }

    public void delete(Long id) {
        roleRepo.delete(getRole(id));
    }

    private void associer(Film film, Acteur acteur) {
        film.getActeurs().add(acteur);
        acteur.getFilms().add(film);
    }

    private Role getRole(Long id) {
        return roleRepo.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    private Film getFilm(Long filmId) {
        return filmRepo.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException(filmId));
    }

    private Acteur getActeur(Long acteurId) {
        return acteurRepo.findById(acteurId)
                .orElseThrow(() -> new ActeurNotFoundException(acteurId));
    }


}
