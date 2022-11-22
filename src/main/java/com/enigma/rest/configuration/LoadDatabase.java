package com.enigma.rest.configuration;

import com.enigma.rest.model.Employee;
import com.enigma.rest.model.Task;
import com.enigma.rest.model.TaskStatusEnum;
import com.enigma.rest.repository.EmployeeRepository;
import com.enigma.rest.repository.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Log4j2
@Component
public class LoadDatabase implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public LoadDatabase(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) {
        log.info("Preloading Data");

        Employee employee = new Employee();
        employee.setName("Konrad");
        employee.setSurname("Matuszewski");
        employee.setEmail("matkonrad99@gmail.com");

        Task task = new Task();
        task.setTitle("First Task");
        task.setDescription("Finish the application");
        task.setTaskStatus(TaskStatusEnum.IN_PROGRESS);
        task.setDueDate(LocalDate.now().plusDays(7));


        log.info("Adding employee: " + employeeRepository.save(employee));

        log.info("Adding task: " + taskRepository.save(task));

    }
}
