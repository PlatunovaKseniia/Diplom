package ru.leti.diplom.domain.outputdto.materials;

import ru.leti.diplom.domain.materials.LearningObject;
import ru.leti.diplom.domain.outputdto.customer.CustomerOutput;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record HandOutOutput(UUID id, String name, LocalDateTime startTime, LocalDateTime endTime, List<CustomerOutput> customers, List<LearningObject> learningObjects) {
}
