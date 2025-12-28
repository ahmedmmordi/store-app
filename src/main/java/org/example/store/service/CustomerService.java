package org.example.store.service;

import lombok.RequiredArgsConstructor;
import org.example.store.exception.DuplicateResourceException;
import org.example.store.mapper.CustomerMapper;
import org.example.store.model.dto.CustomerDTO;
import org.example.store.model.entity.Customer;
import org.example.store.repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    public List<CustomerDTO> findAll() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public List<CustomerDTO> findAllByUsername(String username) {
        return customerRepo.findAllByUsernameContainingIgnoreCase(username)
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public List<CustomerDTO> findAllByEmail(String email) {
        return customerRepo.findAllByEmailContainingIgnoreCase(email)
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public Optional<CustomerDTO> findById(Long id) {
        return customerRepo.findById(id)
                .map(customerMapper::toDTO);
    }

    public Optional<CustomerDTO> findByUsername(String username) {
        return customerRepo.findByUsername(username)
                .map(customerMapper::toDTO);
    }

    public Optional<CustomerDTO> findByEmail(String email) {
        return customerRepo.findByEmail(email)
                .map(customerMapper::toDTO);
    }

    public boolean existsByUsername(String username) {
        return customerRepo.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return customerRepo.existsByEmail(email);
    }

    private boolean existsById(Long id) {
        return customerRepo.existsById(id);
    }

    public CustomerDTO insert(CustomerDTO customerDTO) {
        validateUsernameAndEmail(true, null, customerDTO.getUsername(), customerDTO.getEmail());
        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDTO(customerRepo.save(customer));
    }

    public Optional<CustomerDTO> update(Long id, CustomerDTO customerDTO) {
        return customerRepo.findById(id)
                .map(customer -> {
                    validateUsernameAndEmail(false, customer, customerDTO.getUsername(), customerDTO.getEmail());
                    customer.setUsername(customerDTO.getUsername());
                    customer.setEmail(customerDTO.getEmail());
                    customer.setPhoneNumber(customerDTO.getPhoneNumber());
                    customer.setAddress(customerDTO.getAddress());
                    return customerMapper.toDTO(customerRepo.save(customer));
                });
    }

    public boolean delete(Long id) {
        boolean exists = this.existsById(id);
        if (!exists) {
            return false;
        }
        customerRepo.deleteById(id);
        return true;
    }

    private void validateUsernameAndEmail(boolean forInserting, Customer customer, String username, String email) {
        if (forInserting) {
            if (customerRepo.existsByUsername(username)) {
                throw new DuplicateResourceException("Username Already Exists.");
            }
            if (customerRepo.existsByEmail(email)) {
                throw new DuplicateResourceException("Email Already Exists.");
            }
        }
        else {
            if (!customer.getUsername().equals(username) && customerRepo.existsByUsername(username)) {
                throw new DuplicateResourceException("Username Already Exists.");
            }
            if (!customer.getEmail().equals(email) && customerRepo.existsByEmail(email)) {
                throw new DuplicateResourceException("Email Already Exists.");
            }
        }
    }
}
