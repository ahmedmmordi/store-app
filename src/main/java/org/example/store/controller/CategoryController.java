package org.example.store.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.store.model.dto.CategoryDTO;
import org.example.store.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@Tag(name = "Categories", description = "Operations About Categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> findAllByName(@NotBlank @RequestParam String name) {
        return ResponseEntity.ok(categoryService.findAllByCategoryName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@Positive(message = "Category ID must be a positive number.") @PathVariable Long id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.insert(categoryDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Positive(message = "Category ID must be a positive number.") @PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return categoryService.update(id, categoryDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive(message = "Category ID must be a positive number.") @PathVariable Long id) {
        if (categoryService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
