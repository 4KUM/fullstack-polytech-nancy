package org.polytech.spring.Role.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.polytech.spring.Acteur.Entity.Acteur;
import org.polytech.spring.Films.Entity.Film;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String personnage;

    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Film film;

    @ManyToOne(optional = false)
    @JoinColumn(name = "acteur_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Acteur acteur;

    public Role() {}

    public Role(Film film, Acteur acteur, String personnage) {
        this.film = film;
        this.acteur = acteur;
        this.personnage = personnage;
    }
}
