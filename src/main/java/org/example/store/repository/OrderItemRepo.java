package org.example.store.repository;

import org.example.store.model.entity.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"order", "order.customer", "product", "product.category"})
    List<OrderItem> findByQuantityGreaterThan(Integer quantity);

    @EntityGraph(attributePaths = {"order", "order.customer", "product", "product.category"})
    List<OrderItem> findByOrderId(Long orderId);

    @Query(value = "SELECT SUM(item_quantity) FROM order_items WHERE product_id = :productId", nativeQuery = true)
    Integer totalQuantitySold(@Param("productId") Long productId);

    @Query(value = "SELECT SUM(item_quantity * unit_price) FROM order_items WHERE product_id = :productId", nativeQuery = true)
    BigDecimal totalRevenue(@Param("productId") Long productId);
}
