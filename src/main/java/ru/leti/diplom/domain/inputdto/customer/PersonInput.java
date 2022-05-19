package ru.leti.diplom.domain.inputdto.customer;

import java.util.UUID;

public record PersonInput(UUID id, String email, String firstName, String lastName) {
}
