package com.example.mockito.service;

import com.example.mockito.exception.EmployeeAlreadyAddedException;
import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.exception.EmployeeStorageIsFullException;
import com.example.mockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final List<Employee> employeeList = new ArrayList<>();
    private final static int MAX_SIZE = 7;

    public EmployeeServiceImpl() {
        employeeList.add(new Employee("Иван", "Иванов", 1000.0, 1));
        employeeList.add(new Employee("Иван1", "Иванов1", 999.9, 2));
        employeeList.add(new Employee("Иван2", "Иванов2", 8.50, 3));
        employeeList.add(new Employee("Пётр", "Петров", 8.50, 3));
        employeeList.add(new Employee("Илья", "Ильин", 777.50, 2));
        employeeList.add(new Employee("Илья1", "Ильин1", 88.8, 1));
    }
    @Override
    public Employee add(String firstName, String lastName, double salary, int departmentId) {
        if (employeeList.size() >= MAX_SIZE) {
            throw new EmployeeStorageIsFullException("Массив сотрудников переполнен");
        }
        Employee newEmployee = new Employee(firstName, lastName, salary, departmentId);
        if (employeeList.contains(newEmployee)) {
            throw new EmployeeAlreadyAddedException("Такой сотрудник уже есть");
        }
        employeeList.add(newEmployee);
        return newEmployee;
    }

    @Override
    public Employee remove(String firstName, String lastName, double salary, int departmentId) {
        Employee removeEmployee = new Employee(firstName, lastName, salary, departmentId);
        boolean removeResult = employeeList.remove(removeEmployee);
        if (removeResult) {
            return removeEmployee;
        } else {
            throw new EmployeeNotFoundException("Сотрудник не был удален - не найден в базе");
        }
    }

    @Override
    public Employee find(String firstName, String lastName, double salary, int departmentId) {
        Employee findEmployee = new Employee(firstName, lastName, salary, departmentId);
        for (Employee e : employeeList) {
            if (e.equals(findEmployee)) {
                return e;
            }
        }
        throw new EmployeeNotFoundException("Такого сотрудника нет в базе");
    }

    @Override
    public Collection<Employee> findAll() {
        return employeeList;
    }
}