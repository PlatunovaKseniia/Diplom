package ru.leti.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leti.diplom.domain.materials.CustomerHandOut;
import ru.leti.diplom.domain.materials.HandOut;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerHandOutRepository extends JpaRepository<CustomerHandOut, UUID> {
    Optional<List<CustomerHandOut>> findCustomerHandOutByHandOut(HandOut handOut);
}
