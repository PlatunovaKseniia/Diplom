package ru.leti.diplom.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.leti.diplom.domain.materials.CustomerHandOut;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "customer_info_id")
    private CustomerInfo customerInfo;

    @OneToMany(mappedBy = "customer", cascade = ALL, orphanRemoval = true)
    private List<CustomerHandOut> customerHandOuts;
}
