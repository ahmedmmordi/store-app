package org.example.store.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.store.model.dto.OrderDTO;
import org.example.store.model.dto.OrderItemDTO;
import org.example.store.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name = "Orders", description = "Operations About Orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderDTO>> findAllByCustomer(@Positive(message = "Customer ID must be a positive number.") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.findAllByCustomerId(id));
    }

    @GetMapping("/top")
    public ResponseEntity<List<OrderDTO>> findTopTenOrders() {
        return ResponseEntity.ok(orderService.findTop10OrdersByTotalAmount());
    }

    @GetMapping("/customer/{id}/desc")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerDesc(@Positive(message = "Customer ID must be a positive number.") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrdersByCustomerIdDesc(id));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<OrderDTO>> getOrdersByProduct(@Positive(message = "Product ID must be a positive number.") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrdersByProductId(id));
    }

    @GetMapping("/range")
    public ResponseEntity<List<OrderDTO>> getOrdersByTotalAmountRange(@RequestParam @Min(value = 0, message = "Min amount must be non-negative.") BigDecimal min, @RequestParam @Min(value = 0, message = "Max amount must be non-negative.") BigDecimal max) {
        return ResponseEntity.ok(orderService.findOrdersByTotalAmountRange(min, max));
    }

    @GetMapping("/customer/{id}/count")
    public ResponseEntity<Long> countOrdersByCustomer(@Positive(message = "Customer ID must be a positive number.") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.countOrdersByCustomer(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@Positive(message = "Order ID must be a positive number.") @PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.insert(orderDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@Positive(message = "Order ID must be a positive number.") @PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        return orderService.update(id, orderDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive(message = "Order ID must be a positive number.") @PathVariable Long id) {
        if (orderService.delete(id)) {
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<OrderItemDTO>> getItems(@Positive(message = "Order ID must be a positive number.") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.findItemsByOrderId(id));
    }

    @GetMapping("/items/quantity/{quantity}")
    public ResponseEntity<List<OrderItemDTO>> getItemsByQuantityGreaterThan(@Min(value = 0, message = "Quantity must be non-negative.") @PathVariable Integer quantity) {
        return ResponseEntity.ok(orderService.findItemsByQuantityGreaterThan(quantity));
    }

    @GetMapping("/product/{id}/quantity")
    public ResponseEntity<Integer> totalQuantityForProduct(@Positive(message = "Product ID must be a positive number.") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.totalQuantitySoldForProduct(id));
    }

    @GetMapping("/product/{id}/revenue")
    public ResponseEntity<BigDecimal> totalRevenueForProduct(@Positive(message = "Product ID must be a positive number.") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.totalRevenueForProduct(id));
    }
}
