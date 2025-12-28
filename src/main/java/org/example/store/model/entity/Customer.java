package org.example.store.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.store.model.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity {
    @NotBlank(message = "Username must not be blank")
    @Column(name = "customer_username", nullable = false, unique = true)
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters")
    private String username;

    @Column(name = "customer_email", nullable = false, unique = true, length = 50)
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @Column(name = "customer_phone", length = 20)
    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number must contain only digits and may start with '+'"
    )
    private String phoneNumber;

    @Column(name = "customer_address")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
