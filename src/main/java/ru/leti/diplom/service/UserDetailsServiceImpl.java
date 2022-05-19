package ru.leti.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.leti.diplom.domain.customer.Customer;
import ru.leti.diplom.domain.UserDetailsImpl;
import ru.leti.diplom.exception.BusinessException;
import ru.leti.diplom.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository
                .findCustomerByEmail(username)
                .orElseThrow(() -> new BusinessException("User not found"));
        UserDetailsImpl userDetails = new UserDetailsImpl(customer);
        return userDetails;
    }
}
