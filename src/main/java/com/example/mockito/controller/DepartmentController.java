package com.example.mockito.controller;

import com.example.mockito.model.Employee;
import com.example.mockito.service.DepartmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @ExceptionHandler({HttpStatusCodeException.class})
    public String handleException(HttpStatusCodeException e) {
        return "Code: " + e.getStatusCode() + ". Error: " + e.getMessage();
    }

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping("{id}/employee")
    public Map<Integer, List<Employee>> getEmployeeByDepartment(@PathVariable("id") Integer departmentId) {
        return departmentService.getEmployeeByDepartment(departmentId);
    }
    @GetMapping("{id}/sum")
    public double getSalarySumByDepartment(@PathVariable("id") Integer departmentId) {
        return departmentService.getSalarySumByDepartment(departmentId);
    }
    @GetMapping("{id}/salary/max")
    public Employee getEmployeeWithMaxSalary(@PathVariable("id") Integer departmentId) {
        return departmentService.getEmployeeWithMaxSalary(departmentId);
    }
    @GetMapping("{id}/salary/min")
    public Employee getEmployeeWithMinSalary(@PathVariable("id") Integer departmentId) {
        return departmentService.getEmployeeWithMinSalary(departmentId);
    }
    @GetMapping("/employee")
    public List<Employee> getEmployeeGroupedByDepartment() {
        return departmentService.getEmployeeGroupedByDepartment();
    }
}
