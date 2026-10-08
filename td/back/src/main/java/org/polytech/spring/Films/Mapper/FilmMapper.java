package org.polytech.spring.Films.Mapper;

import org.polytech.spring.Acteur.Mapper.ActeurMapper;
import org.polytech.spring.Commentaires.Mapper.CommentaireMapper;
import org.polytech.spring.Films.DTO.FilmCreationDTO;
import org.polytech.spring.Films.DTO.FilmDTO;
import org.polytech.spring.Films.Entity.Film;
import org.springframework.stereotype.Component;

@Component
public class FilmMapper {

    private final ActeurMapper acteurMapper;
    private final CommentaireMapper commentaireMapper;

    public FilmMapper(ActeurMapper acteurMapper, CommentaireMapper commentaireMapper) {
        this.acteurMapper = acteurMapper;
        this.commentaireMapper = commentaireMapper;
    }

    public FilmDTO toDto(Film film) {
        return new FilmDTO(
                film.getId(),
                film.getTitre(),
                film.getRealisateur(),
                film.getDateSortie(),
                film.getGenre(),
                film.getActeurs().stream()
                        .map(acteurMapper::toDto)
                        .toList(),
                film.getCommentaires().stream()
                        .map(commentaireMapper::toDto)
                        .toList()
        );
    }

    public Film toEntity(FilmCreationDTO dto) {
        Film film = new Film();
        update(film, dto);
        return film;
    }

    public void update(Film film, FilmCreationDTO dto) {
        film.setTitre(dto.titre());
        film.setRealisateur(dto.realisateur());
        film.setDateSortie(dto.dateSortie());
        film.setGenre(dto.genre());
    }
}
