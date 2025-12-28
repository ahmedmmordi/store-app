package org.example.store.repository;

import org.example.store.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    List<Customer> findAllByUsernameContainingIgnoreCase(String username);
    List<Customer> findAllByEmailContainingIgnoreCase(String email);
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
