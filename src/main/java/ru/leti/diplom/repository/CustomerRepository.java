package ru.leti.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leti.diplom.domain.customer.Customer;
import ru.leti.diplom.domain.customer.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findCustomerByEmail(String email);
    Optional<List<Customer>> findCustomersByRole(Role role);
    Optional<Customer> findCustomerById(UUID id);
}
