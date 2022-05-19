package ru.leti.diplom.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class CustomerInfo {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    private String schoolNumber;
    private String studyGroup;

    @OneToOne(mappedBy = "customerInfo")
    private Customer customer;
}
