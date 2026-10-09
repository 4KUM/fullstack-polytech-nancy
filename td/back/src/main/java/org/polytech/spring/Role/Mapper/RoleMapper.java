package org.polytech.spring.Role.Mapper;

import org.polytech.spring.Acteur.Mapper.ActeurMapper;
import org.polytech.spring.Role.DTO.RoleDTO;
import org.polytech.spring.Role.Entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ActeurMapper acteurMapper;

    public RoleMapper(ActeurMapper acteurMapper) {
        this.acteurMapper = acteurMapper;
    }

    public RoleDTO toDto(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getPersonnage(),
                role.getFilm().getId(),
                role.getFilm().getTitre(),
                acteurMapper.toDto(role.getActeur())
        );
    }
}
