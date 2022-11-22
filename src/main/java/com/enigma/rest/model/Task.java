package com.enigma.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title may not be blank")
    private String title;

    @NotBlank(message = "Description may not be blank")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatusEnum taskStatus;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private LocalDate dueDate;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
    @ManyToMany
    @JoinTable(
            name = "task_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> assignedEmployees = new HashSet<>();

    public void assignEmployee(Employee employee) {
        assignedEmployees.add(employee);
    }
}


