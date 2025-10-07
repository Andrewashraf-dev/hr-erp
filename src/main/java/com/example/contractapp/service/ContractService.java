package com.example.contractapp.service;

import com.example.contractapp.model.Employee;
import com.example.contractapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllByOrderByCreatedDateDesc();
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    public boolean employeeExists(String employeeId) {
        return employeeRepository.existsByEmployeeId(employeeId);
    }
}