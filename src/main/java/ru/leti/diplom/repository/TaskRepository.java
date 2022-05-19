package ru.leti.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leti.diplom.domain.materials.HandOut;
import ru.leti.diplom.domain.materials.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<List<Task>> findTaskByHandOut(HandOut handOut);
}
