package com.enigma.rest.service;

import com.enigma.rest.exception.InvalidSearchQueryException;
import com.enigma.rest.exception.WrongDueDateDateException;
import com.enigma.rest.model.Employee;
import com.enigma.rest.model.Task;
import com.enigma.rest.model.TaskStatusEnum;
import com.enigma.rest.repository.EmployeeRepository;
import com.enigma.rest.repository.TaskRepository;
import com.enigma.rest.util.SearchCriteria;
import com.enigma.rest.util.TaskSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<List<Task>> getTasks() {
        return new ResponseEntity<>(taskRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Task> createTask(Task task) throws WrongDueDateDateException {
        if (!task.getDueDate().isAfter(LocalDate.now())) {
            throw new WrongDueDateDateException("Due date has to be after today");
        }
        return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
    }

    public void editTask(Long taskId, Task updatedTask) {
        taskRepository.findById(taskId).ifPresent(task -> {
            if (updatedTask.getTitle() != null && !updatedTask.getTitle().isBlank()) {
                task.setTitle(updatedTask.getTitle());
            }
            if (updatedTask.getDescription() != null && !updatedTask.getDescription().isBlank()) {
                task.setDescription(updatedTask.getDescription());
            }
            if (updatedTask.getDueDate() != null && updatedTask.getDueDate().isAfter(LocalDate.now())) {
                task.setDueDate(updatedTask.getDueDate());
            }
            if (updatedTask.getTaskStatus() != null) {
                task.setTaskStatus(updatedTask.getTaskStatus());
            }
            taskRepository.save(task);
        });
    }

    public ResponseEntity<Task> assignTask(Long taskId, Long employeeId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        task.assignEmployee(employee);
        return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void updateTaskStatus(Long taskId, String taskStatus) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        if (!task.getTaskStatus().toString().equalsIgnoreCase(taskStatus)) {
            task.setTaskStatus(TaskStatusEnum.valueOf(taskStatus.toUpperCase()));
            taskRepository.save(task);
        }
    }

    public ResponseEntity<List<Task>> search(String searchCriteria) throws InvalidSearchQueryException {
        Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+)", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchCriteria);

        if (!matcher.find()) {
            throw new InvalidSearchQueryException(String.format("Proper query syntax: %s", "?q=name:Konrad"));
        }
        TaskSpecification taskSpecification = new TaskSpecification(
                new SearchCriteria(
                        matcher.group(1),
                        matcher.group(2),
                        matcher.group(3)
                ));
        return new ResponseEntity<>(taskRepository.findAll(Specification.where(taskSpecification)), HttpStatus.OK);
    }
}
