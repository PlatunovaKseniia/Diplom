package ru.leti.diplom.domain.materials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "handOutID")
    private HandOut handOut;

    @ManyToOne
    @JoinColumn(name = "learningObjectID")
    private LearningObject learningObject;
}
