package org.example.store.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.store.model.dto.CustomerDTO;
import org.example.store.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@Tag(name = "Customers", description = "Operations About Customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<CustomerDTO>> findAllByUsername(@NotBlank(message = "Name must be provided.") @RequestParam String name) {
        return ResponseEntity.ok(customerService.findAllByUsername(name));
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<CustomerDTO>> findAllByEmail(@NotBlank(message = "Email must be provided.") @RequestParam String email) {
        return ResponseEntity.ok(customerService.findAllByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@Positive(message = "Customer ID must be a positive number.") @PathVariable Long id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerDTO> findByUsername(@NotBlank(message = "Username must be provided.") @PathVariable String username) {
        return customerService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDTO> getByEmail(@NotBlank(message = "Email must be provided.") @PathVariable String email) {
        return customerService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> usernameExists(@NotBlank(message = "Username must be provided.") @PathVariable String username) {
        return ResponseEntity.ok(customerService.existsByUsername(username));
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> emailExists(@NotBlank(message = "Email must be provided.") @PathVariable String email) {
        return ResponseEntity.ok(customerService.existsByEmail(email));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> insert(@Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.insert(customerDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@Positive(message = "Customer ID must be a positive number.") @PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        return customerService.update(id, customerDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive(message = "Customer ID must be a positive number.") @PathVariable Long id) {
        if (customerService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
