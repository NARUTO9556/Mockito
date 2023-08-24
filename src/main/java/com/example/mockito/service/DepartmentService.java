package com.example.mockito.service;

import com.example.mockito.model.Employee;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Map<Integer, List<Employee>> getEmployeeByDepartment(Integer department);
    double getSalarySumByDepartment(Integer departmentId);
    Employee getEmployeeWithMaxSalary(Integer department);
    Employee getEmployeeWithMinSalary(Integer department);

    List<Employee> getEmployeeGroupedByDepartment();
}
