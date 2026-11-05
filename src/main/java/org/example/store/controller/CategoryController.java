package org.example.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get all categories", description = "Retrieves a list of all categories.", responses = {
            @ApiResponse(responseCode = "200", description = "Categories Retrieved") })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(summary = "Search categories by name", description = "Retrieves a list of categories matching the provided name.", responses = {
            @ApiResponse(responseCode = "200", description = "Categories Retrieved") })
    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> findAllByName(@NotBlank @RequestParam String name) {
        return ResponseEntity.ok(categoryService.findAllByCategoryName(name));
    }

    @Operation(summary = "Get a category by ID", description = "Retrieves a specific category based on the provided ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Category Found"),
            @ApiResponse(responseCode = "404", description = "Category Not Found") })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(
            @Positive(message = "Category ID must be a positive number.") @PathVariable Long id) {
        return categoryService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @Operation(summary = "Create a new category", description = "Adds a new category to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Category Created"),
            @ApiResponse(responseCode = "400", description = "Invalid Input Data") })
    public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.insert(categoryDTO));
    }

    @Operation(summary = "Update a category by ID", description = "Updates the category fields.", responses = {
            @ApiResponse(responseCode = "200", description = "Category Updated"),
            @ApiResponse(responseCode = "404", description = "Category Not Found") })
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @Positive(message = "Category ID must be a positive number.") @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        return categoryService.update(id, categoryDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a category by ID", description = "Removes a category from the system.", responses = {
            @ApiResponse(responseCode = "204", description = "Category Deleted"),
            @ApiResponse(responseCode = "404", description = "Category Not Found") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Positive(message = "Category ID must be a positive number.") @PathVariable Long id) {
        if (categoryService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
