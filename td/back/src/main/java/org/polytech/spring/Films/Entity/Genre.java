package org.polytech.spring.Films.Entity;

public enum Genre {
    ACTION("Action"),
    AVENTURE("Aventure"),
    ANIMATION("Animation"),
    BIOPIC("Biopic"),
    COMEDIE("Comédie"),
    COMEDIE_DRAMATIQUE("Comédie dramatique"),
    COMEDIE_MUSICALE("Comédie musicale"),
    CRIME("Crime"),
    DOCUMENTAIRE("Documentaire"),
    DRAME("Drame"),
    ESPIONNAGE("Espionnage"),
    FAMILLE("Famille"),
    FANTASTIQUE("Fantastique"),
    GUERRE("Guerre"),
    HISTORIQUE("Historique"),
    HORREUR("Horreur"),
    MUSICAL("Musical"),
    MYSTERE("Mystère"),
    POLICIER("Policier"),
    ROMANCE("Romance"),
    SCIENCE_FICTION("Science-fiction"),
    SPORT("Sport"),
    SUPER_HEROS("Super-héros"),
    THRILLER("Thriller"),
    WESTERN("Western");

    private final String libelle;
    Genre(String libelle) {
        this.libelle = libelle;
    }
    public String getLibelle() {
        return libelle;
    }
}
