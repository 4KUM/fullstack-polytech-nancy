package org.polytech.spring.Acteur.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.polytech.spring.Films.Entity.Film;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@Entity
public class Acteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;

    public Acteur() {}

    @ManyToMany(mappedBy = "acteurs")
    private Set<Film> films = new HashSet<>();
}
