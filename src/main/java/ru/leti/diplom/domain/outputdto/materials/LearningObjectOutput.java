package ru.leti.diplom.domain.outputdto.materials;

import java.util.UUID;

public record LearningObjectOutput(UUID id, String name, String kind, String path, String taskFile) {
}
