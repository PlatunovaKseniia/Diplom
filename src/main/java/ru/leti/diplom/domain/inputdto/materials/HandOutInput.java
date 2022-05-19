package ru.leti.diplom.domain.inputdto.materials;

import java.util.List;
import java.util.UUID;

public record HandOutInput(UUID id, String name, String startTime, String endTime, List<String> email, List<String> nameLearningObject) {
}

