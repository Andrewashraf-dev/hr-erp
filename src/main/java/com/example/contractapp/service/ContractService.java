package com.example.contractapp.service;

import com.example.contractapp.model.Contract;
import com.example.contractapp.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }

    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }

    public List<Contract> searchByEmployeeName(String name) {
        return contractRepository.findByEmployeeNameInEnglishContainingIgnoreCase(name);
    }

    public List<Contract> searchByCompanyName(String companyName) {
        return contractRepository.findByCompanyNameInEnglishContainingIgnoreCase(companyName);
    }
}