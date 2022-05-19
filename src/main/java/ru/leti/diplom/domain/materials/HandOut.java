package ru.leti.diplom.domain.materials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandOut {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "handOut", cascade = ALL, orphanRemoval = true)
    private List<Task> customerTask;

    @OneToMany(mappedBy = "handOut", cascade = ALL, orphanRemoval = true)
    private List<CustomerHandOut> customerHandOuts;
}
