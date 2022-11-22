package com.enigma.rest.controller;

import com.enigma.rest.exception.InvalidSearchQueryException;
import com.enigma.rest.model.Task;
import com.enigma.rest.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}")
    public void editTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        taskService.editTask(taskId, updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @PutMapping("/{taskId}/employee/{employeeId}")
    public Task assignTask(@PathVariable Long taskId, @PathVariable Long employeeId) {
        return taskService.assignTask(taskId, employeeId);
    }

    @PatchMapping(value = "/{taskId}")
    public void updateTaskStatus(@PathVariable Long taskId, @RequestBody String taskStatus) {
        taskService.updateTaskStatus(taskId, taskStatus);
    }

    @GetMapping("/search")
    public List<Task> search(@RequestParam(value = "q") String searchCriteria) throws InvalidSearchQueryException {
        return taskService.search(searchCriteria);
    }

}