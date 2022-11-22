package com.enigma.rest.service;

import com.enigma.rest.exception.InvalidSearchQueryException;
import com.enigma.rest.model.Employee;
import com.enigma.rest.repository.EmployeeRepository;
import com.enigma.rest.util.EmployeeSpecification;
import com.enigma.rest.util.SortSearchUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    public ResponseEntity<List<Employee>> search(String searchCriteria, String sortCriteria) {
        EmployeeSpecification employeeSpecification = EmployeeSpecification.validateSpecification(searchCriteria);
        Sort sortBy = SortSearchUtils.validateSortCriteria(sortCriteria);

        if (employeeSpecification != null && sortBy != null) {
            return new ResponseEntity<>(employeeRepository.findAll(Specification.where(employeeSpecification), sortBy), HttpStatus.OK);
        } else if (employeeSpecification != null) {
            return new ResponseEntity<>(employeeRepository.findAll(Specification.where(employeeSpecification)), HttpStatus.OK);
        } else if (sortBy != null) {
            return new ResponseEntity<>(employeeRepository.findAll(sortBy), HttpStatus.OK);
        }

        return new ResponseEntity<>(new LinkedList<>(), HttpStatus.OK);
    }

}
