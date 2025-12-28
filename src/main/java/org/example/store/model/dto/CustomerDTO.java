package org.example.store.model.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class CustomerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String email;
    private String phoneNumber;
    private String address;
}
