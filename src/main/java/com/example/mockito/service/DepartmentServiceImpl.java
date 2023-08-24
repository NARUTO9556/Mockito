package com.example.mockito.service;

import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentServiceImpl(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Map<Integer, List<Employee>> getEmployeeByDepartment(Integer departmentId) {
        return employeeService.findAll().stream()
                .filter(e -> departmentId == null|| e.getDepartmentId() == departmentId)
                .collect(groupingBy(Employee::getDepartmentId, toList()));
    }

    @Override
    public double getSalarySumByDepartment(Integer departmentId) {
        double sum = 0;
        for (Employee e: employeeService.findAll()) {
            if (e != null && e.getDepartmentId() == departmentId) {
                sum += e.getSalary();
            }
        }
        return sum;
    }

    @Override
    public Employee getEmployeeWithMaxSalary(Integer departmentId) {
        return employeeService.findAll().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник с максимальной зарплатой не найден"));
    }

    @Override
    public Employee getEmployeeWithMinSalary(Integer departmentId) {
        return employeeService.findAll().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник с минимальной зарплатой не найден"));
    }

    @Override
    public Map<Integer, List<Employee>> getEmployeeGroupedByDepartment() {
        return null;
    }
}
