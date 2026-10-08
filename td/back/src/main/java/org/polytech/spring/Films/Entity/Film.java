package org.polytech.spring.Films.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.polytech.spring.Acteur.Entity.Acteur;
import org.polytech.spring.Commentaires.Entity.Commentaire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(length = 100)
    private String realisateur;

    private LocalDate dateSortie;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id")
    private List<Commentaire> commentaires = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "film_acteur",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "acteur_id"))
    private Set<Acteur> acteurs = new HashSet<>();



    public Film() {
    }

    public List<Commentaire> getCommentaires() { return commentaires; }

    public void setCommentaires(List<Commentaire> commentaires) { this.commentaires = commentaires; }

}
