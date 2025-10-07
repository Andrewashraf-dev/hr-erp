package com.example.contractapp.repository;

import com.example.contractapp.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByEmployeeNameInEnglishContainingIgnoreCase(String name);
    List<Contract> findByCompanyNameInEnglishContainingIgnoreCase(String companyName);
}