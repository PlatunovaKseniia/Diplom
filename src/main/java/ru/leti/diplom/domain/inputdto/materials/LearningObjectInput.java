package ru.leti.diplom.domain.inputdto.materials;

import java.util.UUID;

public record LearningObjectInput(UUID id, String name, String kind, String uploadFile) {
}
