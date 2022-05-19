package ru.leti.diplom.domain.outputdto.customer;

import java.util.UUID;

public record CustomerOutput(UUID id, String email, String firstName, String lastName, String info) {
}
