package org.example.store.repository;

import org.example.store.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByTotalAmountBetween(BigDecimal min, BigDecimal max);
    List<Order> findAllByCustomerId(Long customerId);
    List<Order> findTop10ByOrderByTotalAmountDesc();
    List<Order> findByCustomerIdOrderByTotalAmountDesc(Long customerId);
    Long countByCustomerId(Long customerId);

    @Query(value = "SELECT DISTINCT o.* FROM orders o JOIN order_items i ON o.id = i.order_id WHERE i.product_id = :productId", nativeQuery = true)
    List<Order> findOrdersByProductIdIn(@Param("productId") Long productId);
}
