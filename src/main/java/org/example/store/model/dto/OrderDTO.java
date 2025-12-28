package org.example.store.model.dto;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class OrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long customerId;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> items;
}
