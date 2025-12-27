package org.example.store.model.dto;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(exclude = {"products"})
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String categoryName;
    private String description;
    private List<ProductDTO> products;
}
