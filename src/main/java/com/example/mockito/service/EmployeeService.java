package com.example.mockito.service;

import com.example.mockito.model.Employee;

import java.util.Collection;
import java.util.List;

public interface EmployeeService {
    Employee add(String firstName, String lastName, double salary, int department);

    Employee remove(String firstName, String lastName, double salary, int department);

    Employee find(String firstName, String lastName, double salary, int department);

    Collection<Employee> findAll();

}
