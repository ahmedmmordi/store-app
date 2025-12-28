package org.example.store.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DummyProductResponseDTO {
    private List<DummyProductDTO> products;
    private Integer total;
    private Integer skip;
    private Integer limit;
}
