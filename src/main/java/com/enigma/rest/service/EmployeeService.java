package com.enigma.rest.service;

import com.enigma.rest.exception.InvalidSearchQueryException;
import com.enigma.rest.model.Employee;
import com.enigma.rest.repository.EmployeeRepository;
import com.enigma.rest.util.EmployeeSpecification;
import com.enigma.rest.util.SearchCriteria;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<Employee> createEmployee(Employee employee) {
        return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
    }

    public ResponseEntity<Employee> removeEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Employee>> getEmployees() {
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    public List<Employee> search(String searchCriteria) throws InvalidSearchQueryException {
        Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+)", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchCriteria);

        if (!matcher.find()) {
            throw new InvalidSearchQueryException(String.format("Proper query syntax: %s", "?q=name:Konrad"));
        }
        EmployeeSpecification employeeSpecification = new EmployeeSpecification(
                new SearchCriteria(
                        "name",":","Konrad"
                ));
            return employeeRepository.findAll(Specification.where(employeeSpecification));
    }

}
