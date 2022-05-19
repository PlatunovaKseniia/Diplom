package ru.leti.diplom.domain.inputdto.customer;

import java.util.UUID;

public record CustomerInput(UUID id, String email, String password) { }
