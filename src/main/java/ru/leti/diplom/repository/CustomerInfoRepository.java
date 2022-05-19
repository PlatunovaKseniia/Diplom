package ru.leti.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leti.diplom.domain.customer.CustomerInfo;

import java.util.List;
import java.util.Optional;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, String> {
    Optional<List<CustomerInfo>> findCustomerInfoBySchoolNumber(String schoolNumber);
    Optional<List<CustomerInfo>> findCustomerInfoByStudyGroup(String studyGroup);
}
