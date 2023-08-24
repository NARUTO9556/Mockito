package com.example.mockito.controller;

import com.example.mockito.model.Employee;
import com.example.mockito.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Collection;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @ExceptionHandler({HttpStatusCodeException.class})
    public String handleException(HttpStatusCodeException e) {
        return "Code: " + e.getStatusCode() + ". Error: " + e.getMessage();
    }
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/add")
    public Employee addEmployee(@RequestParam String firstName, @RequestParam String lastName, @RequestParam double salary, @RequestParam int departmentId) {
        return employeeService.add(firstName, lastName, salary, departmentId);
    }
    @GetMapping("/remove")
    public Employee removeEmployee(@RequestParam String firstName, @RequestParam String lastName, @RequestParam double salary, @RequestParam int departmentId) {
        return employeeService.remove(firstName, lastName, salary, departmentId);
    }
    @GetMapping("/find")
    public Employee findEmployee(@RequestParam String firstName, @RequestParam String lastName, @RequestParam double salary, @RequestParam int departmentId) {
        return employeeService.find(firstName, lastName, salary, departmentId);
    }

    @GetMapping
    public Collection<Employee> findALl() {
        return employeeService.findAll();
    }
}
