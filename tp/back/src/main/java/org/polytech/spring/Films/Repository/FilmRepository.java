package org.polytech.spring.Films.Repository;

import org.polytech.spring.Films.Entity.Film;
import org.polytech.spring.Films.Entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findByActeursId(Long acteurId);

    @Query("select f from Film f join f.acteurs a where a.id = :acteurId")
    List<Film> findFilmsDeLActeur(@Param("acteurId") Long acteurId);



}
