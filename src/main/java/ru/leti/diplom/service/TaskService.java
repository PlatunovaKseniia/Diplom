package ru.leti.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.leti.diplom.domain.inputdto.materials.HandOutInput;
import ru.leti.diplom.domain.inputdto.materials.LearningObjectInput;
import ru.leti.diplom.domain.materials.CustomerHandOut;
import ru.leti.diplom.domain.materials.HandOut;
import ru.leti.diplom.domain.materials.LearningObject;
import ru.leti.diplom.domain.materials.Task;
import ru.leti.diplom.domain.outputdto.customer.CustomerOutput;
import ru.leti.diplom.domain.outputdto.materials.HandOutOutput;
import ru.leti.diplom.domain.outputdto.materials.LearningObjectOutput;
import ru.leti.diplom.exception.BusinessException;
import ru.leti.diplom.repository.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Value("${learning-material.path}")
    private String defaultFilePath;
    private final HandOutRepository handOutRepository;
    private final LearningObjectRepository learningObjectRepository;
    private final CustomerRepository customerRepository;
    private final CustomerHandOutRepository customerHandOutRepository;
    private final TaskRepository taskRepository;

    public HandOut addHandOut(HandOutInput handOutInput) {
        if(handOutRepository.findHandOutByName(handOutInput.name()).isEmpty()) {
            HandOut handOut = new HandOut(
                    UUID.randomUUID(),
                    handOutInput.name(),
                    LocalDateTime.parse(handOutInput.startTime()),
                    LocalDateTime.parse(handOutInput.endTime()), null, null);
            List<Task> tasks = new ArrayList<>();
            handOutInput
                    .nameLearningObject()
                    .forEach(task -> tasks.add(Task
                            .builder()
                            .learningObject(learningObjectRepository.findLearningObjectByName(task)
                            .orElseThrow(() -> new BusinessException("Learning object not found")))
                                    .handOut(handOut)
                            .build()));
            List<CustomerHandOut> customers = new ArrayList<>();
            handOutInput
                    .email()
                    .forEach(c -> customers.add(CustomerHandOut.builder()
                            .customer(customerRepository.findCustomerByEmail(c)
                            .orElseThrow(() -> new BusinessException("Customer not found")))
                                    .handOut(handOut)
                            .build()));
            handOut.setCustomerHandOuts(customers);
            handOut.setCustomerTask(tasks);
            return handOutRepository.save(handOut);
        }
        else {
            throw new BusinessException("Event already exists");
        }
    }

    public HandOut updateHandOut(HandOutInput handOutInput) {
        if(handOutRepository.findHandOutById(handOutInput.id()).isPresent()) {
            HandOut handOut = handOutRepository
                    .findHandOutById(handOutInput.id()).get();

            handOut.setName(handOutInput.name());
            handOut.setStartTime(LocalDateTime.parse(handOutInput.startTime()));
            handOut.setEndTime(LocalDateTime.parse(handOutInput.endTime()));
            List<Task> tasks = new ArrayList<>();
            handOutInput
                    .nameLearningObject()
                    .forEach(task -> tasks.add(Task
                            .builder()
                            .learningObject(learningObjectRepository.findLearningObjectByName(task)
                                    .orElseThrow(() -> new BusinessException("Learning object not found")))
                            .handOut(handOut)
                            .build()));
            List<CustomerHandOut> customers = new ArrayList<>();
            handOutInput
                    .email()
                    .forEach(c -> customers.add(CustomerHandOut.builder()
                            .customer(customerRepository.findCustomerByEmail(c)
                                    .orElseThrow(() -> new BusinessException("Customer not found")))
                            .handOut(handOut)
                            .build()));
            handOut.setCustomerHandOuts(customers);
            handOut.setCustomerTask(tasks);
            return handOutRepository.save(handOut);
        }
        else {
            throw new BusinessException("Hand-out not found");
        }
    }

    public LearningObject addLearningObject(LearningObjectInput learningObjectInput) {
        if(learningObjectRepository.findLearningObjectByName(learningObjectInput.name()).isEmpty()) {
            String path = "%s%s.%s".formatted(defaultFilePath, learningObjectInput.name(), learningObjectInput.kind());
            File file = new File(path);
            LearningObject learningObject = new LearningObject(
                    UUID.randomUUID(),
                    learningObjectInput.name(),
                    learningObjectInput.kind(),
                    path, null);

            decode(learningObjectInput, file);
            return learningObjectRepository.save(learningObject);
        }
        else {
            throw new BusinessException("Learning object already exists");
        }
    }

    public LearningObjectOutput getLearningObjectByName(String name) {
        LearningObject learningObject = learningObjectRepository
                .findLearningObjectByName(name)
                .orElseThrow(() -> new BusinessException("Learning object with name %s doesn't exist".formatted(name)));

        return new LearningObjectOutput(
                learningObject.getId(),
                learningObject.getName(),
                learningObject.getKind(),
                learningObject.getPath(),
                encodeFile(learningObject.getPath())
        );
    }

    private String encodeFile(String filePath) {
        try {
            byte[] fileContent = Files.readAllBytes(Path.of(filePath));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new BusinessException("Can't read file");
        }
    }

    public HandOutOutput getHandOutByName(String name) {
        HandOut handOut = handOutRepository
                .findHandOutByName(name)
                .orElseThrow(() -> new BusinessException("Hand-out with name %s doesn't exist".formatted(name)));

        List<CustomerHandOut> customerHandOuts = customerHandOutRepository
                .findCustomerHandOutByHandOut(handOut)
                .orElseThrow(() -> new BusinessException("Error in line 131"));

        List<Task> tasks = taskRepository
                .findTaskByHandOut(handOut)
                .orElseThrow(() -> new BusinessException("Error in line 133"));
        return new HandOutOutput(
                handOut.getId(),
                handOut.getName(),
                handOut.getStartTime(),
                handOut.getEndTime(),
                customerHandOuts.stream().map(customerHandOut -> new CustomerOutput(
                        customerHandOut.getCustomer().getId(),
                        customerHandOut.getCustomer().getEmail(),
                        customerHandOut.getCustomer().getCustomerInfo().getFirstName(),
                        customerHandOut.getCustomer().getCustomerInfo().getLastName(),
                        customerHandOut.getCustomer().getRole().toString())).toList(),
                tasks.stream().map(Task::getLearningObject).toList()
        );
    }

    public List<HandOutOutput> getHandOutByDate() {
        LocalDateTime start = LocalDateTime.now();
        List<HandOut> handOuts = handOutRepository.findAll();

        List<HandOut> hOs = handOuts
                .stream()
                .filter(handOut -> handOut
                        .getStartTime()
                        .compareTo(start) < 0)
                .filter(handOut -> handOut
                        .getEndTime()
                        .compareTo(start) > 0).toList();

        if(hOs.isEmpty())
            throw new BusinessException("Hand-out not found");

        List<CustomerHandOut> customerHandOuts = new ArrayList<>();
        handOuts.forEach(handOut -> customerHandOuts.addAll(customerHandOutRepository.findCustomerHandOutByHandOut(handOut).get()));

        List<Task> tasks = new ArrayList<>();
        handOuts.forEach(handOut -> tasks.addAll(taskRepository.findTaskByHandOut(handOut).get()));
        return handOuts.stream().map(handOut -> new HandOutOutput(
                handOut.getId(),
                handOut.getName(),
                handOut.getStartTime(),
                handOut.getEndTime(),
                customerHandOuts.stream().map(customerHandOut -> new CustomerOutput(
                        customerHandOut.getCustomer().getId(),
                        customerHandOut.getCustomer().getEmail(),
                        customerHandOut.getCustomer().getCustomerInfo().getFirstName(),
                        customerHandOut.getCustomer().getCustomerInfo().getLastName(),
                        customerHandOut.getCustomer().getRole().toString())).toList(),
                tasks.stream().map(Task::getLearningObject).toList())).toList();
    }

    public List<LearningObject> getAllLearningObject() {
        return learningObjectRepository.findAll();
    }

    public List<HandOut> getAllHandOut() {
        return handOutRepository.findAll();
    }

    public LearningObject updateLearningObject(LearningObjectInput learningObjectInput) {
        if(learningObjectRepository.findLearningObjectById(learningObjectInput.id()).isPresent()) {
            LearningObject learningObject = learningObjectRepository
                    .findLearningObjectById(learningObjectInput.id()).get();
            try {
                Files.deleteIfExists(Paths.get("%s%s.%s".formatted(defaultFilePath, learningObject.getName(), learningObject.getKind())));
            } catch (IOException e) {
                System.err.println(e);
            }
            learningObject.setName(learningObjectInput.name());
            learningObject.setKind(learningObjectInput.kind());
            String path = "%s%s.%s".formatted(defaultFilePath, learningObjectInput.name(), learningObjectInput.kind());
            learningObject.setPath(path);
            File file = new File(path);

            decode(learningObjectInput, file);
            return learningObjectRepository.save(learningObject);
        }
        else {
            throw new BusinessException("Learning object not found");
        }
    }

    private void decode(LearningObjectInput learningObjectInput, File file) {
        try (FileOutputStream fos = new FileOutputStream(file); ) {
            String b64 = learningObjectInput.uploadFile();
            byte[] decoder = Base64.getDecoder().decode(b64);
            fos.write(decoder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteHandOut(UUID id) {
        if(handOutRepository.findHandOutById(id).isPresent()) {
            HandOut handOut = handOutRepository
                    .findHandOutById(id).get();
            handOutRepository.delete(handOut);
        } else {
            throw new BusinessException("Hand-out not found");
        }
    }

    public void deleteLearningObject(UUID learningObjectInput) {
        if(learningObjectRepository.findLearningObjectById(learningObjectInput).isPresent()) {
            LearningObject learningObject = learningObjectRepository
                    .findLearningObjectById(learningObjectInput).get();
            try {
                Files.deleteIfExists(Paths.get("%s%s.%s".formatted(defaultFilePath, learningObject.getName(), learningObject.getKind())));
            } catch (IOException e) {
                System.err.println(e);
            }
            learningObjectRepository.delete(learningObject);
        } else {
            throw new BusinessException("Learning object not found");
        }
    }
}

