package org.polytech.spring.Commentaires.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.polytech.spring.Films.Entity.Film;

import java.time.LocalDate;

@Getter
@Setter

@Entity
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String auteur;

    @Column(length = 100)
    private LocalDate date;

    @Column(length = 1000, nullable=false)
    private String message;

    public Commentaire(){}

    public Commentaire(Long id, String auteur, String message) {
        this.id = id;
        this.auteur = auteur;
        this.date = LocalDate.now();
        this.message = message;
    }

    @ManyToOne
    @JoinColumn(name = "id_film")
    private Film film;
}
