package org.example.store.model.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ProductDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String productName;
    private String description;
    private BigDecimal price;
    private Double rating;
}
