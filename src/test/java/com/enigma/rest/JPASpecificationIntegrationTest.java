package com.enigma.rest;

import com.enigma.rest.model.Employee;
import com.enigma.rest.model.Task;
import com.enigma.rest.model.TaskStatusEnum;
import com.enigma.rest.repository.EmployeeRepository;
import com.enigma.rest.repository.TaskRepository;

import com.enigma.rest.util.EmployeeSpecification;
import com.enigma.rest.util.SearchCriteria;
import com.enigma.rest.util.TaskSpecification;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsNot.not;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Log4j2
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPASpecificationIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Task task1;
    private Task task2;

    private Employee employee;

    @Before
    public void init() {
        log.info("Preloading Data");

        task1 = new Task();
        task1.setTitle("First Task");
        task1.setDescription("Finish the application");
        task1.setTaskStatus(TaskStatusEnum.IN_PROGRESS);
        task1.setDueDate(LocalDate.now().plusDays(7));

        task2 = new Task();
        task2.setTitle("Second Task");
        task2.setDescription("Pass tests");
        task2.setTaskStatus(TaskStatusEnum.FINISHED);
        task2.setDueDate(LocalDate.now().plusDays(7));

        log.info("Adding task: " + taskRepository.save(task1));
        log.info("Adding task: " + taskRepository.save(task2));

        employee = new Employee();
        employee.setName("Konrad");
        employee.setSurname("Matuszewski");
        employee.setEmail("matkonrad99@gmail.com");

        log.info("Adding employee: " + employeeRepository.save(employee));
    }

    @Test
    public void givenTitleAndDescription_whenGettingListOfTasks_thenCorrect() {
        TaskSpecification specificationName = new TaskSpecification(new SearchCriteria("title", ":", "First Task"));
        TaskSpecification specificationDescription = new TaskSpecification(new SearchCriteria("description", ":", "Finish the application"));

        List<Task> tasks = taskRepository.findAll(Specification.where(specificationName).and(specificationDescription));

        assertThat(tasks, contains(task1));
        assertThat(tasks, not(contains(task2)));
    }

    @Test
    public void givenTitle_whenGettingListOfTasks_thenIncorrect() {
        TaskSpecification specificationName = new TaskSpecification(new SearchCriteria("title", ":", "Third Task"));

        List<Task> tasks = taskRepository.findAll(Specification.where(specificationName));

        assertThat(tasks, not(contains(task1)));
    }

    @Test
    public void givenName_whenGettingListOfEmployees_thenCorrect() {
        EmployeeSpecification employeeSpecification = new EmployeeSpecification(new SearchCriteria("name", ";", "Konrad"));

        List<Employee> employees = employeeRepository.findAll(employeeSpecification);

        assertThat(employees, contains(employee));
    }

}