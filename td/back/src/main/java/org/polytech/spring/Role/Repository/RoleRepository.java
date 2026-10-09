package org.polytech.spring.Role.Repository;

import org.polytech.spring.Role.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByFilmId(Long filmId);

    List<Role> findByActeurId(Long acteurId);

    void deleteByFilmIdAndActeurId(Long filmId, Long acteurId);
}
