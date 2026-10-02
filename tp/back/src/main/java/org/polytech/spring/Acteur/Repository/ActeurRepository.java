package org.polytech.spring.Acteur.Repository;

import org.polytech.spring.Acteur.Entity.Acteur;
import org.polytech.spring.Films.Entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActeurRepository extends JpaRepository<Acteur, Long> {
    List<Acteur> findByFilmsId(Long filmId);

    @Query("select a from Acteur a join a.films f where f.id = :filmId")
    List<Acteur> findActeurDuFilm(@Param("filmId") Long filmId);

}
