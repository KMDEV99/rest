package com.enigma.rest.service;

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

import java.util.List;
import java.util.NoSuchElementException;

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
        try {
            employeeRepository.deleteById(employeeId);
        } catch (Exception e) {
            throw new NoSuchElementException(String.format("Employee with id: `%d` does not exist", employeeId));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Employee>> getEmployees() {
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Employee>> search(String searchCriteria, String sortCriteria) {
        EmployeeSpecification employeeSpecification = EmployeeSpecification.validateSpecification(searchCriteria);
        Sort sortBy = SortSearchUtils.validateSortCriteria(sortCriteria);

        return new ResponseEntity<>(employeeRepository.findAll(Specification.where(employeeSpecification),
                sortBy != null ? sortBy : Sort.by(Sort.Direction.ASC, "id")), HttpStatus.OK);
    }

}
