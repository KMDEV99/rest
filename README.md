# Enigma REST App
___
Tested on Java 17
### Sample usage:

Employee Service:

```[GET] getAllEmployees: http://localhost:8080/employees```

```[DELETE] deleteEmployee: http://localhost:8080/employees/1```

```
[POST] postEmployee: http://localhost:8080/employees
{
    "name": "Kajko3",
    "surname": "Kokosz5",
    "email": "poczta@yahoo.com"
}
```

```
getEmployeeSearch: 

[GET] http://localhost:8080/employees/search?q=name:Konrad&sort=id:desc
[GET] http://localhost:8080/employees/search?q=id>2
[GET] http://localhost:8080/employees/search?sort=id:asc
```
Task Service:


```[GET] getAllTasks: http://localhost:8080/tasks```

```[GET] getAllTasks: http://localhost:8080/tasks/search?q=title:First Task```

```[PUT] assignTaskToEmployee: http://localhost:8080/tasks/4/employee/1```

```[DELETE] deleteTask: http://localhost:8080/tasks/2```

```
[PATCH] changeTaskStatus: http://localhost:8080/tasks/4/employee/1
body = NOT_started
```
& Other
___