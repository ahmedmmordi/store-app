package org.example.store.repository;

import jakarta.validation.constraints.NotBlank;
import org.example.store.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByProductNameContainingIgnoreCase(@NotBlank(message = "Product name is required.") String productName);
}
