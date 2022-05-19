package ru.leti.diplom.domain.materials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearningObject {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String kind;
    private String path;

    @OneToMany(mappedBy = "learningObject", orphanRemoval = true)
    private List<Task> customerTask;
}
