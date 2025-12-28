package org.example.store.repository;

import org.example.store.model.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Order> findAll();

    @EntityGraph(attributePaths = {"items", "items.product"})
    Optional<Order> findById(Long id);

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Order> findByTotalAmountBetween(BigDecimal min, BigDecimal max);

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Order> findAllByCustomerId(Long customerId);

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Order> findTop10ByOrderByTotalAmountDesc();

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Order> findByCustomerIdOrderByTotalAmountDesc(Long customerId);

    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.customer c JOIN FETCH o.items i JOIN FETCH i.product p JOIN FETCH p.category WHERE i.product.id = :productId")
    List<Order> findOrdersByProductIdIn(@Param("productId") Long productId);

    Long countByCustomerId(Long customerId);
}
