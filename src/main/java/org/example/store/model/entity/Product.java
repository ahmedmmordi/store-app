package org.example.store.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.store.model.base.BaseEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Column(name = "product_name", nullable = false, length = 255)
    @NotBlank(message = "Product name is required.")
    private String productName;

    @Column(name = "product_description", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Product Description Is Required.")
    private String description;

    @Column(name = "product_price", nullable = false, precision = 10, scale = 2)
    @DecimalMin(value = "1.00", inclusive = true)
    @NotNull(message = "Product price is required.")
    private BigDecimal price;

    @Column(name = "product_rating", nullable = false)
    @Min(0)
    @Max(5)
    @NotNull(message = "Product rate is required.")
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
