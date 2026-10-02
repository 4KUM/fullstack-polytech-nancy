package org.polytech.spring.Commentaires.Service;


import org.polytech.spring.Commentaires.DTO.CommentaireCreationDTO;
import org.polytech.spring.Commentaires.DTO.CommentaireDTO;
import org.polytech.spring.Commentaires.Entity.Commentaire;
import org.polytech.spring.Commentaires.Exception.CommentaireNotFoundException;
import org.polytech.spring.Commentaires.Mapper.CommentaireMapper;
import org.polytech.spring.Films.Entity.Film;
import org.polytech.spring.Films.Exception.FilmNotFoundException;
import org.polytech.spring.Films.Repository.FilmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CommentaireService {

        private final FilmRepository filmRepo;
        private final CommentaireMapper commentaireMapper;

        public CommentaireService(FilmRepository filmRepo, CommentaireMapper commentaireMapper) {
            this.filmRepo = filmRepo;
            this.commentaireMapper = commentaireMapper;
        }

        public List<CommentaireDTO> findByFilm(Long filmId) {
            return getFilm(filmId).getCommentaires().stream()
                    .map(commentaireMapper::toDto)
                    .toList();
        }

        public CommentaireDTO create(Long filmId, CommentaireCreationDTO dto) {
            Film film = getFilm(filmId);
            validate(dto);
            Commentaire commentaire = commentaireMapper.toEntity(dto);
            commentaire.setDate(LocalDate.now());
            film.getCommentaires().add(commentaire);
            filmRepo.flush();
            return commentaireMapper.toDto(commentaire);
        }

        public CommentaireDTO update(Long id, CommentaireCreationDTO dto) {
            validate(dto);
            Commentaire commentaire = findById(id);
            commentaireMapper.update(commentaire, dto);
            return commentaireMapper.toDto(commentaire);
        }

        public void delete(Long id) {
            for (Film film : filmRepo.findAll()) {
                if (film.getCommentaires().removeIf(c -> c.getId().equals(id))) {
                    return;
                }
            }
            throw new CommentaireNotFoundException(id);
        }

        private Commentaire findById(Long id) {
            return filmRepo.findAll().stream()
                    .flatMap(f -> f.getCommentaires().stream())
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new CommentaireNotFoundException(id));
        }

        private Film getFilm(Long filmId) {
            return filmRepo.findById(filmId)
                    .orElseThrow(() -> new FilmNotFoundException(filmId));
        }

        private void validate(CommentaireCreationDTO dto) {
            if (dto.auteur() == null || dto.auteur().isBlank()
                    || dto.message() == null || dto.message().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "L'auteur et le message sont obligatoires");
            }
        }
    }
