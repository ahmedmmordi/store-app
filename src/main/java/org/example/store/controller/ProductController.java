package org.example.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.store.model.dto.ProductDTO;
import org.example.store.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Products", description = "Operations About Products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> findAllByName(@NotBlank @RequestParam String name) {
        return ResponseEntity.ok(productService.findAllByProductName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@Positive(message = "Product ID Must Given And Be a Positive Number.") @PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.insert(productDTO));
    }

    @Operation(
            summary = "Update a product by ID",
            description = "Updates the product fields: name, price, rating, and description.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product Updated"),
                    @ApiResponse(responseCode = "404", description = "Product Not Found")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@Positive(message = "Product ID Must Given And Be a Positive Number.") @PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive(message = "Product ID Must Given And Be a Positive Number.") @PathVariable Long id) {
        if (productService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
