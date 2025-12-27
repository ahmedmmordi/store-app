package org.example.store.repository;

import org.example.store.model.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = "products")
    List<Category> findAll();

    @EntityGraph(attributePaths = "products")
    Optional<Category> findById(Long id);

    @EntityGraph(attributePaths = "products")
    Optional<Category> findByCategoryName(String categoryName);

    @EntityGraph(attributePaths = "products")
    List<Category> findAllByCategoryNameContainingIgnoreCase(String categoryName);
}
