package org.polytech.spring.Commentaires.Entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Commentaire {
    private Long id;
    private String auteur;
    private LocalDate date;
    private String message;

    public Commentaire(){}

    public Commentaire(Long id, String auteur, String message) {
        this.id = id;
        this.auteur = auteur;
        this.date = LocalDate.now();
        this.message = message;
    }
}
