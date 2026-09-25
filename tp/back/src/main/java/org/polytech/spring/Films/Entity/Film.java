package org.polytech.spring.Films.Entity;

import lombok.Getter;
import lombok.Setter;
import org.polytech.spring.Commentaires.Entity.Commentaire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Film {
    private Long id;
    private String titre;
    private String realisateur;
    private LocalDate dateSortie;
    private Genre genre;
    private List<Commentaire> commentaires =  new ArrayList<>();


    public Film() {
    }

    public Film(Long id, String titre, String realisateur, LocalDate dateSortie, Genre genre, List<Commentaire> commentaires) {
        this.id = id;
        this.titre = titre;
        this.realisateur = realisateur;
        this.dateSortie = dateSortie;
        this.genre = genre;
        this.commentaires = commentaires;
    }

    public List<Commentaire> getCommentaires() { return commentaires; }

    public void setCommentaires(List<Commentaire> commentaires) { this.commentaires = commentaires; }

    @Override
    public String toString() {
        return "Film{id=" + id + ", titre='" + titre + "', realisateur='" + realisateur
                + "', dateSortie=" + dateSortie + ", genre=" + genre + "}";
    }
}
