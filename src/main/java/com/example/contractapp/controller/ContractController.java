package com.example.contractapp.controller;

import com.example.contractapp.dto.ContractData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ContractController {

    // Add these fields for storing employees and generating IDs
    private List<ContractData> savedEmployees = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    // 1. Landing Page
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    // 2. Show contract form
    @GetMapping("/exact-contract-form")
    public String showExactContractForm(Model model) {
        model.addAttribute("contractData", new ContractData());
        return "exact-contract-form";
    }

    // 3. Generate contract
    @PostMapping("/generate-exact-contract")
    public String generateExactContract(@ModelAttribute ContractData contractData, Model model) {
        model.addAttribute("contractData", contractData);
        return "exact-contract";
    }

    // 4. Save and show employee details
    @PostMapping("/save-and-view-employee")
    public String saveAndViewEmployee(@ModelAttribute ContractData contractData, Model model) {
        contractData.setId(idCounter.getAndIncrement());
        savedEmployees.add(contractData);
        model.addAttribute("employee", contractData);
        return "employee-details";
    }

    // 5. List all employees
    @GetMapping("/view-employees")
    public String viewEmployees(Model model) {
        model.addAttribute("employees", savedEmployees);
        return "view-employees";
    }

    // 6. View a specific employee
    @GetMapping("/employees/view/{id}")
    public String viewEmployee(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        ContractData employee = findEmployeeById(id);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "employee-details";
        } else {
            redirectAttributes.addFlashAttribute("error", "Employee not found");
            return "redirect:/view-employees";
        }
    }

    // 7. Edit a specific employee
    @GetMapping("/employees/edit/{id}")
    public String editEmployee(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        ContractData employee = findEmployeeById(id);
        if (employee != null) {
            model.addAttribute("contractData", employee);
            return "edit-employee";
        } else {
            redirectAttributes.addFlashAttribute("error", "Employee not found");
            return "redirect:/view-employees";
        }
    }

    // 8. Update employee after edit
    @PostMapping("/employees/update/{id}")
    public String updateEmployee(@PathVariable int id, @ModelAttribute ContractData updatedData, RedirectAttributes redirectAttributes) {
        int index = getEmployeeIndexById(id);
        if (index != -1) {
            updatedData.setId(id);
            savedEmployees.set(index, updatedData);
            redirectAttributes.addFlashAttribute("success", "Employee updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Employee not found");
        }
        return "redirect:/view-employees";
    }

    // 9. Delete an employee
    @PostMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable int id, RedirectAttributes redirectAttributes) {
        int index = getEmployeeIndexById(id);
        if (index != -1) {
            savedEmployees.remove(index);
            redirectAttributes.addFlashAttribute("success", "Employee deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Employee not found");
        }
        return "redirect:/view-employees";
    }

    // 10. Generate contract for specific employee
    @GetMapping("/employees/generate-contract/{id}")
    public String generateEmployeeContract(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        ContractData employee = findEmployeeById(id);
        if (employee != null) {
            model.addAttribute("contractData", employee);
            return "exact-contract";
        } else {
            redirectAttributes.addFlashAttribute("error", "Employee not found");
            return "redirect:/view-employees";
        }
    }

    // 11. Generate insurance paper for specific employee
    @GetMapping("/employees/generate-insurance/{id}")
    public String generateInsurancePaper(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        ContractData employee = findEmployeeById(id);
        if (employee != null) {
            model.addAttribute("contractData", employee);
            return "insurance-paper";
        } else {
            redirectAttributes.addFlashAttribute("error", "Employee not found");
            return "redirect:/view-employees";
        }
    }

    // Helper method to find employee by ID
    private ContractData findEmployeeById(int id) {
        return savedEmployees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Helper method to get employee index by ID
    private int getEmployeeIndexById(int id) {
        for (int i = 0; i < savedEmployees.size(); i++) {
            if (savedEmployees.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}