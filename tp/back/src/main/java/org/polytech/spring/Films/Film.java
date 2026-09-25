package org.polytech.spring.Films;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Film {
    private Long id;
    private String titre;
    private String realisateur;
    private LocalDate dateSortie;
    private Genre genre;


    public Film() {
    }

    public Film(Long id, String titre, String realisateur, LocalDate dateSortie, Genre genre) {
        this.id = id;
        this.titre = titre;
        this.realisateur = realisateur;
        this.dateSortie = dateSortie;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Film{id=" + id + ", titre='" + titre + "', realisateur='" + realisateur
                + "', dateSortie=" + dateSortie + ", genre=" + genre + "}";
    }
}
