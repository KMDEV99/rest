package com.enigma.rest.controller;

import com.enigma.rest.exception.InvalidSearchQueryException;
import com.enigma.rest.model.Employee;
import com.enigma.rest.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        return employeeService.getEmployees();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Employee> removeEmployee(@PathVariable Long employeeId) {
        return employeeService.removeEmployee(employeeId);
    }

    @GetMapping("/search")
    public List<Employee> search(@RequestParam(value = "q") String searchCriteria) throws InvalidSearchQueryException {
        return employeeService.search(searchCriteria);
    }

}
