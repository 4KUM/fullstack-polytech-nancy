package org.polytech.spring.Commentaires.Service;


import org.polytech.spring.Commentaires.Entity.Commentaire;
import org.polytech.spring.Commentaires.Exception.CommentaireNotFoundException;
import org.polytech.spring.Films.Entity.Film;
import org.polytech.spring.Films.Exception.FilmNotFoundException;
import org.polytech.spring.Films.Repository.FilmRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CommentaireService {

        private final FilmRepo filmRepo;
        private final AtomicLong commentaireId = new AtomicLong();

        public CommentaireService(FilmRepo filmRepo) {
            this.filmRepo = filmRepo;
        }

        public List<Commentaire> findByFilm(Long filmId) {
            return getFilm(filmId).getCommentaires();
        }

        public Commentaire create(Long filmId, Commentaire commentaire) {
            Film film = getFilm(filmId);
            validate(commentaire);
            commentaire.setId(commentaireId.incrementAndGet());
            commentaire.setDate(LocalDate.now());
            film.getCommentaires().add(commentaire);
            return commentaire;
        }

        public Commentaire update(Long id, Commentaire modif) {
            validate(modif);
            Commentaire commentaire = findById(id);
            commentaire.setAuteur(modif.getAuteur());
            commentaire.setMessage(modif.getMessage());
            return commentaire;
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

        private void validate(Commentaire c) {
            if (c.getAuteur() == null || c.getAuteur().isBlank()
                    || c.getMessage() == null || c.getMessage().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "L'auteur et le message sont obligatoires");
            }
        }
    }
