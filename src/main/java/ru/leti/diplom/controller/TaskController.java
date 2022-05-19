package ru.leti.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import ru.leti.diplom.domain.inputdto.materials.HandOutInput;
import ru.leti.diplom.domain.inputdto.materials.LearningObjectInput;
import ru.leti.diplom.domain.materials.HandOut;
import ru.leti.diplom.domain.materials.LearningObject;
import ru.leti.diplom.domain.outputdto.materials.HandOutOutput;
import ru.leti.diplom.domain.outputdto.materials.LearningObjectOutput;
import ru.leti.diplom.service.TaskService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class TaskController {

    final private TaskService taskService;

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public HandOut addHandOut(@Argument HandOutInput handOutInput) {
        return taskService.addHandOut(handOutInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public LearningObject addLearningObject(@Argument LearningObjectInput learningObjectInput) {
        return taskService.addLearningObject(learningObjectInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public LearningObject updateLearningObject(@Argument LearningObjectInput learningObjectInput) {
        return taskService.updateLearningObject(learningObjectInput);
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public LearningObjectOutput getLearningObjectByName(@Argument String name) {
        return taskService.getLearningObjectByName(name);
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public HandOutOutput getHandOutByName(@Argument String name) {
        return taskService.getHandOutByName(name);
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public List<LearningObject> getAllLearningObject() {
        return taskService.getAllLearningObject();
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public List<HandOut> getAllHandOut() {
        return taskService.getAllHandOut();
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public List<HandOutOutput> getHandOutByDate() {
        return taskService.getHandOutByDate();
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public HandOut updateHandOut(@Argument HandOutInput handOutInput) {
        return taskService.updateHandOut(handOutInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public void deleteHandOut(@Argument String handOutId) {
        taskService.deleteHandOut(UUID.fromString(handOutId));
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public void deleteLearningObject(@Argument LearningObjectInput learningObjectInput) {
        taskService.deleteLearningObject(learningObjectInput);
    }
}
