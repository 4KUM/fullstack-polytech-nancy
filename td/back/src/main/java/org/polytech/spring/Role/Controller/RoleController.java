package org.polytech.spring.Role.Controller;

import org.polytech.spring.Role.DTO.RoleCreationDTO;
import org.polytech.spring.Role.DTO.RoleDTO;
import org.polytech.spring.Role.Service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<RoleDTO> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/roles/{id}")
    public RoleDTO findById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @GetMapping("/films/{filmId}/roles")
    public List<RoleDTO> findByFilm(@PathVariable Long filmId) {
        return roleService.findByFilm(filmId);
    }

    @GetMapping("/acteurs/{acteurId}/roles")
    public List<RoleDTO> findByActeur(@PathVariable Long acteurId) {
        return roleService.findByActeur(acteurId);
    }

    @PostMapping("/films/{filmId}/roles")
    public ResponseEntity<RoleDTO> create(@PathVariable Long filmId, @RequestBody RoleCreationDTO role) {
        RoleDTO saved = roleService.create(filmId, role);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/roles/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/roles/{id}")
    public RoleDTO update(@PathVariable Long id, @RequestBody RoleCreationDTO role) {
        return roleService.update(id, role);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
