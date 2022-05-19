package ru.leti.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leti.diplom.domain.materials.LearningObject;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LearningObjectRepository extends JpaRepository<LearningObject, String> {
    Optional<LearningObject> findLearningObjectByName(String name);
    Optional<LearningObject> findLearningObjectById(UUID id);
}
