package org.example.store.service;

import lombok.RequiredArgsConstructor;
import org.example.store.mapper.OrderItemMapper;
import org.example.store.mapper.OrderMapper;
import org.example.store.model.dto.OrderDTO;
import org.example.store.model.dto.OrderItemDTO;
import org.example.store.model.entity.Order;
import org.example.store.model.entity.OrderItem;
import org.example.store.model.entity.Product;
import org.example.store.repository.OrderItemRepo;
import org.example.store.repository.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;
    private final OrderItemRepo orderItemRepo;
    private final OrderItemMapper orderItemMapper;

    public List<OrderDTO> findAll() {
        return orderRepo.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> findAllByCustomerId(Long customerId) {
        return orderRepo.findAllByCustomerId(customerId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> findTop10OrdersByTotalAmount() {
        return orderRepo.findTop10ByOrderByTotalAmountDesc()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> findOrdersByCustomerIdDesc(Long customerId) {
        return orderRepo.findByCustomerIdOrderByTotalAmountDesc(customerId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> findOrdersByProductId(Long productId) {
        return orderRepo.findOrdersByProductIdIn(productId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> findOrdersByTotalAmountRange(BigDecimal min, BigDecimal max) {
        if (max.compareTo(min) < 0) {
            throw new IllegalArgumentException("Max amount must be greater than or equal to min amount.");
        }
        return orderRepo.findByTotalAmountBetween(min, max)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    public Long countOrdersByCustomer(Long customerId) {
        return orderRepo.countByCustomerId(customerId);
    }

    public Optional<OrderDTO> findById(Long id) {
        return orderRepo.findById(id)
                .map(orderMapper::toDTO);
    }

    private boolean existsById(Long id) {
        return orderRepo.existsById(id);
    }

    @Transactional
    public OrderDTO insert(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        List<OrderItem> items = mapOrderItems(order, orderDTO.getItems());
        order.setItems(items);
        recalculateTotalAmount(order, items);
        return orderMapper.toDTO(orderRepo.save(order));
    }

    @Transactional
    public Optional<OrderDTO> update(Long id, OrderDTO orderDTO) {
        return orderRepo.findById(id)
                .map(order -> {
                    if (orderDTO.getCustomerId() != null) {
                        order.getCustomer().setId(orderDTO.getCustomerId());
                    }
                    order.getItems().clear();
                    List<OrderItem> newItems = mapOrderItems(order, orderDTO.getItems());
                    order.getItems().addAll(newItems);
                    recalculateTotalAmount(order, newItems);
                    return orderMapper.toDTO(order);
                });
    }

    public boolean delete(Long id) {
        boolean exists = this.existsById(id);
        if (!exists) {
            return false;
        }
        orderRepo.deleteById(id);
        return true;
    }

    public List<OrderItemDTO> findItemsByOrderId(Long orderId) {
        return orderItemRepo.findByOrderId(orderId)
                .stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }

    public List<OrderItemDTO> findItemsByQuantityGreaterThan(Integer quantity) {
        return orderItemRepo.findByQuantityGreaterThan(quantity)
                .stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }

    public Integer totalQuantitySoldForProduct(Long productId) {
        Integer totalQuantity = orderItemRepo.totalQuantitySold(productId);
        return totalQuantity == null ? 0 : totalQuantity;
    }

    public BigDecimal totalRevenueForProduct(Long productId) {
        BigDecimal totalRevenue = orderItemRepo.totalRevenue(productId);
        return totalRevenue == null ? BigDecimal.ZERO : totalRevenue;
    }

    private List<OrderItem> mapOrderItems(Order order, List<OrderItemDTO> itemDTOs) {
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item.");
        }
        return itemDTOs.stream()
                .map(itemDTO -> {
                    OrderItem item = orderItemMapper.toEntity(itemDTO);
                    item.setOrder(order);
                    Product product = new Product();
                    product.setId(itemDTO.getProductId());
                    item.setProduct(product);
                    return item;
                })
                .toList();
    }

    private void recalculateTotalAmount(Order order, List<OrderItem> items) {
        BigDecimal total = items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
    }
}
