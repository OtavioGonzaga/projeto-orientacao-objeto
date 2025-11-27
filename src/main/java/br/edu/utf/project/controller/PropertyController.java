package br.edu.utf.project.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utf.project.decorator.UserId;
import br.edu.utf.project.dto.CreatePropertyDTO;
import br.edu.utf.project.model.PropertyModel;
import br.edu.utf.project.service.PropertyService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public List<PropertyModel> getAll() {
        return propertyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyModel> getById(@PathVariable UUID id) {
        return propertyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PropertyModel> create(@Parameter(hidden = true) @UserId UUID ownerId,
            @Valid @RequestBody CreatePropertyDTO property) {
        return ResponseEntity.ok(propertyService.create(ownerId, property));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyModel> update(@Parameter(hidden = true) @UserId UUID ownerId, @PathVariable UUID id,
            @Valid @RequestBody CreatePropertyDTO property) {
        return ResponseEntity.ok(propertyService.update(id, ownerId, property));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = propertyService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
