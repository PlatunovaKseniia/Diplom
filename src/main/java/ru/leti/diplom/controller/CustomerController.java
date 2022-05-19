package ru.leti.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import ru.leti.diplom.domain.customer.Customer;
import ru.leti.diplom.domain.inputdto.customer.SchoolchildInfoInput;
import ru.leti.diplom.domain.inputdto.customer.StudentInfoInput;
import ru.leti.diplom.domain.inputdto.customer.TeacherInfoInput;
import ru.leti.diplom.domain.outputdto.customer.CustomerOutput;
import ru.leti.diplom.service.CustomerService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    final private CustomerService customerService;

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Customer addAdditionalInfoForStudent(@Argument StudentInfoInput studentInfoInput) {
        return customerService.addAdditionalInfoForStudent(studentInfoInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Customer addAdditionalInfoForTeacher(@Argument TeacherInfoInput teacherInfoInput) {
        return customerService.addAdditionalInfoForTeacher(teacherInfoInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Customer addAdditionalInfoForSchoolchild(@Argument SchoolchildInfoInput schoolchildInfoInput) {
        return customerService.addAdditionalInfoForSchoolchild(schoolchildInfoInput);
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public List<CustomerOutput> getCustomersByRole(@Argument String role) {
        return customerService.getCustomersByRole(role);
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public List<CustomerOutput> getCustomersBySchoolNumber(@Argument String schoolNumber) {
        return customerService.getCustomersBySchoolNumber(schoolNumber);
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public List <CustomerOutput> getCustomersByStudyGroup(@Argument String studyGroup) {
        return customerService.getCustomersByStudyGroup(studyGroup);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Customer updateAdditionalInfoForStudent(@Argument StudentInfoInput studentInfoInput) {
        return customerService.updateAdditionalInfoForStudent(studentInfoInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Customer updateAdditionalInfoForTeacher(@Argument TeacherInfoInput teacherInfoInput) {
        return customerService.updateAdditionalInfoForTeacher(teacherInfoInput);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Customer updateAdditionalInfoForSchoolchild(@Argument SchoolchildInfoInput schoolchildInfoInput) {
        return customerService.updateAdditionalInfoForSchoolchild(schoolchildInfoInput);
    }
}
