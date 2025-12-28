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
public class OrderItemDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
