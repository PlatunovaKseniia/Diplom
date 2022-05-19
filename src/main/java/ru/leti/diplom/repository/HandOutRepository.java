package ru.leti.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leti.diplom.domain.materials.HandOut;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HandOutRepository extends JpaRepository<HandOut, String> {
    Optional<HandOut> findHandOutByName(String name);
    Optional<HandOut> findHandOutById(UUID id);
}
