package ru.leti.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import ru.leti.diplom.domain.customer.Customer;
import ru.leti.diplom.domain.inputdto.customer.CustomerInput;
import ru.leti.diplom.service.CustomerService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final CustomerService customerService;

    @MutationMapping
    public Customer signUpCustomer(@Argument CustomerInput customerInput) {
        return customerService.signUpCustomer(customerInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Customer updateCustomer(@Argument CustomerInput customerInput) {
        return customerService.updateCustomer(customerInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public void deleteCustomer(@Argument UUID customerInputID) {
        customerService.deleteCustomer(customerInputID);
    }
}
