package ru.leti.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.leti.diplom.domain.customer.CustomerInfo;
import ru.leti.diplom.domain.customer.Role;
import ru.leti.diplom.domain.inputdto.customer.CustomerInput;
import ru.leti.diplom.domain.customer.Customer;
import ru.leti.diplom.domain.inputdto.customer.SchoolchildInfoInput;
import ru.leti.diplom.domain.inputdto.customer.StudentInfoInput;
import ru.leti.diplom.domain.inputdto.customer.TeacherInfoInput;
import ru.leti.diplom.domain.outputdto.customer.CustomerOutput;
import ru.leti.diplom.exception.BusinessException;
import ru.leti.diplom.repository.CustomerInfoRepository;
import ru.leti.diplom.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

import static ru.leti.diplom.domain.customer.Role.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerInfoRepository customerInfoRepository;

    public Customer signUpCustomer(CustomerInput customerInput) {
        if (customerRepository.findCustomerByEmail(customerInput.email()).isEmpty()) {
            Customer customer = new Customer(
                    UUID.randomUUID(),
                    customerInput.email(),
                    passwordEncoder.encode(customerInput.password()),null, null, null);
            return customerRepository.save(customer);
        } else {
            throw new BusinessException("User already exists");
        }
    }

    public Customer updateCustomer(CustomerInput customerInput) {
        if (customerRepository.findCustomerById(customerInput.id()).isPresent()) {
            Customer customer = customerRepository
                    .findCustomerById(customerInput.id()).get();
            customer.setEmail(customerInput.email());
            customer.setPassword(passwordEncoder.encode(customerInput.password()));
            return customerRepository.save(customer);
        } else {
            throw new BusinessException("User not found");
        }
    }

    public void deleteCustomer(UUID customerInput) {
        if (customerRepository.findCustomerById(customerInput).isPresent()) {
            Customer customer = customerRepository
                    .findCustomerById(customerInput).get();
            customerRepository.delete(customer);
        } else {
            throw new BusinessException("User not found");
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer addAdditionalInfoForStudent(StudentInfoInput studentInfoInput) {
        Customer customer = customerRepository
                .findCustomerByEmail(studentInfoInput.personInput().email())
                .orElseThrow(() -> new BusinessException("User not found"));


        customer.setRole(STUDENT);
        customer.setCustomerInfo(
                CustomerInfo
                        .builder()
                        .firstName(studentInfoInput.personInput().firstName())
                        .lastName(studentInfoInput.personInput().lastName())
                        .studyGroup(studentInfoInput.studyGroup())
                        .build()
        );

        return customerRepository.save(customer);
    }

    public Customer updateAdditionalInfoForStudent(StudentInfoInput studentInfoInput) {
        if (customerRepository.findCustomerById(studentInfoInput.personInput().id()).isPresent()) {
            Customer customer = customerRepository
                    .findCustomerById(studentInfoInput.personInput().id()).get();

            customer.setRole(STUDENT);
            customer.setCustomerInfo(
                    CustomerInfo
                            .builder()
                            .firstName(studentInfoInput.personInput().firstName())
                            .lastName(studentInfoInput.personInput().lastName())
                            .studyGroup(studentInfoInput.studyGroup())
                            .build()
            );

            return customerRepository.save(customer);
        }
        else {
                throw new BusinessException("Student not found");
        }
    }

    public Customer addAdditionalInfoForTeacher(TeacherInfoInput teacherInfoInput) {
        Customer customer = customerRepository
                .findCustomerByEmail(teacherInfoInput.personInput().email())
                .orElseThrow(() -> new BusinessException("User not found"));


        customer.setRole(TEACHER);
        customer.setCustomerInfo(
                CustomerInfo
                        .builder()
                        .firstName(teacherInfoInput.personInput().firstName())
                        .lastName(teacherInfoInput.personInput().lastName())
                        .build()
        );

        return customerRepository.save(customer);
    }

    public Customer updateAdditionalInfoForTeacher(TeacherInfoInput teacherInfoInput) {
        if (customerRepository.findCustomerById(teacherInfoInput.personInput().id()).isPresent()) {
            Customer customer = customerRepository
                    .findCustomerById(teacherInfoInput.personInput().id()).get();

            customer.setRole(TEACHER);
            customer.setCustomerInfo(
                    CustomerInfo
                            .builder()
                            .firstName(teacherInfoInput.personInput().firstName())
                            .lastName(teacherInfoInput.personInput().lastName())
                            .build()
            );

            return customerRepository.save(customer);
        }
        else {
            throw new BusinessException("Teacher not found");
        }
    }

    public Customer addAdditionalInfoForSchoolchild(SchoolchildInfoInput schoolchildInfoInput) {
        Customer customer = customerRepository
                .findCustomerByEmail(schoolchildInfoInput.personInput().email())
                .orElseThrow(() -> new BusinessException("User not found"));


        customer.setRole(SCHOOLCHILD);
        customer.setCustomerInfo(
                CustomerInfo
                        .builder()
                        .firstName(schoolchildInfoInput.personInput().firstName())
                        .lastName(schoolchildInfoInput.personInput().lastName())
                        .schoolNumber(schoolchildInfoInput.schoolNumber())
                        .build()
        );

        return customerRepository.save(customer);
    }

    public Customer updateAdditionalInfoForSchoolchild(SchoolchildInfoInput schoolchildInfoInput) {
        if (customerRepository.findCustomerById(schoolchildInfoInput.personInput().id()).isPresent()) {
            Customer customer = customerRepository
                    .findCustomerById(schoolchildInfoInput.personInput().id()).get();

            customer.setRole(SCHOOLCHILD);
            customer.setCustomerInfo(
                    CustomerInfo
                            .builder()
                            .firstName(schoolchildInfoInput.personInput().firstName())
                            .lastName(schoolchildInfoInput.personInput().lastName())
                            .schoolNumber(schoolchildInfoInput.schoolNumber())
                            .build()
            );

            return customerRepository.save(customer);
        }
        else {
            throw new BusinessException("Schoolchild not found");
        }
    }

    public List<CustomerOutput> getCustomersByRole(String role) {
        List<Customer> customers = customerRepository
                .findCustomersByRole(Role.valueOf(role))
                .orElseThrow(() -> new BusinessException("Customer with role %s doesn't exist".formatted(role)));
        return switch (Role.valueOf(role)) {
            case STUDENT -> customers.stream().map(customer-> new CustomerOutput(customer.getId(),
                    customer.getEmail(),
                    customer.getCustomerInfo().getFirstName(),
                    customer.getCustomerInfo().getLastName(),
                    customer.getCustomerInfo().getStudyGroup())).toList();
            case SCHOOLCHILD -> customers.stream().map(customer-> new CustomerOutput(customer.getId(),
                    customer.getEmail(),
                    customer.getCustomerInfo().getFirstName(),
                    customer.getCustomerInfo().getLastName(),
                    customer.getCustomerInfo().getSchoolNumber())).toList();
            case TEACHER -> customers.stream().map(customer-> new CustomerOutput(customer.getId(),
                    customer.getEmail(),
                    customer.getCustomerInfo().getFirstName(),
                    customer.getCustomerInfo().getLastName(),
                    customer.getRole().toString())).toList();
            default -> throw new BusinessException("Role not found");
        };
    }

    public List<CustomerOutput> getCustomersBySchoolNumber(String schoolNumber) {
        List<CustomerInfo> customersInfo = customerInfoRepository
                .findCustomerInfoBySchoolNumber(schoolNumber)
                .orElseThrow(() -> new BusinessException("Customer with school number %s doesn't exist".formatted(schoolNumber)));

        return customersInfo.stream().map(customerInfo -> new CustomerOutput(
                customerInfo.getCustomer().getId(),
                customerInfo.getCustomer().getEmail(),
                customerInfo.getFirstName(),
                customerInfo.getLastName(),
                customerInfo.getCustomer().getRole().toString()
        )).toList();
    }

    public List<CustomerOutput> getCustomersByStudyGroup(String studyGroup) {
        List<CustomerInfo> customersInfo = customerInfoRepository
                .findCustomerInfoByStudyGroup(studyGroup)
                .orElseThrow(() -> new BusinessException("Customer object with study group %s doesn't exist".formatted(studyGroup)));

        return customersInfo.stream().map(customerInfo -> new CustomerOutput(
                customerInfo.getCustomer().getId(),
                customerInfo.getCustomer().getEmail(),
                customerInfo.getFirstName(),
                customerInfo.getLastName(),
                customerInfo.getCustomer().getRole().toString()
        )).toList();
    }
}
