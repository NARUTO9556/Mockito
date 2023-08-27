package com.example.mockito.service;

import com.example.mockito.exception.EmployeeAlreadyAddedException;
import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.exception.EmployeeStorageIsFullException;
import com.example.mockito.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpStatusCodeException;

import static org.junit.jupiter.api.Assertions.*;
import static com.example.mockito.Generator.EmployeeGenerator.*;

class EmployeeServiceImplTest {
    private final EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    @Test
    void add_success() {
        String firstName = IVAN_FIRST_NAME;
        String lastName = IVAN_LAST_NAME;
        double salary = IVAN_SALARY;
        int departmentId = FIRST_DEPARTMENT_ID;

        Employee expectedResult = getIvanFirstDep();
        Employee actualResult = employeeService.add(firstName, lastName, salary, departmentId);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void add_exceptionFullStorage() {
        String firstName = PETR_FIRST_NAME;
        String lastName = PETR_LAST_NAME;
        double salary = PETR_SALARY;
        int departmentId = FIRST_DEPARTMENT_ID;

        employeeService.add(IVAN_FIRST_NAME, IVAN_LAST_NAME, IVAN_SALARY, FIRST_DEPARTMENT_ID);
        employeeService.add(ILYA_FIRST_NAME, ILYA_LAST_NAME, ILYA_SALARY, SECOND_DEPARTMENT_ID);
        String expectedErrorMessage = "400 Массив сотрудников переполнен";
        HttpStatusCodeException exception = assertThrows(EmployeeStorageIsFullException.class,
                () -> employeeService.add(firstName, lastName, salary, departmentId));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }
    @Test
    void add_exceptionAlreadyAdded() {
        String firstName = IVAN_FIRST_NAME;
        String lastName = IVAN_LAST_NAME;
        double salary = IVAN_SALARY;
        int departmentId = FIRST_DEPARTMENT_ID;

        employeeService.add(IVAN_FIRST_NAME, IVAN_LAST_NAME, IVAN_SALARY, FIRST_DEPARTMENT_ID);
        String expectedErrorMessage = "400 Такой сотрудник уже есть";
        HttpStatusCodeException exception = assertThrows(EmployeeAlreadyAddedException.class,
                () -> employeeService.add(firstName, lastName, salary, departmentId));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }

    @Test
    void remove_success() {
        String firstName = IVAN_FIRST_NAME;
        String lastName = IVAN_LAST_NAME;
        double salary = IVAN_SALARY;
        int departmentId = FIRST_DEPARTMENT_ID;

        employeeService.add(IVAN_FIRST_NAME, IVAN_LAST_NAME, IVAN_SALARY, FIRST_DEPARTMENT_ID);

        Employee expectedResult = new Employee(IVAN_FIRST_NAME, IVAN_LAST_NAME,IVAN_SALARY,FIRST_DEPARTMENT_ID);
        Employee actualResult = employeeService.remove(firstName, lastName, salary, departmentId);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void remove_exceptionNotFound() {
        String firstName = IVAN_FIRST_NAME;
        String lastName = IVAN_LAST_NAME;
        double salary = IVAN_SALARY;
        int departmentId = FIRST_DEPARTMENT_ID;

        String expectedErrorMessage = "404 Сотрудник не был удален - не найден в базе";
        HttpStatusCodeException exception = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.remove(firstName, lastName, salary, departmentId));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }

    @Test
    void find_success() {
        String firstName = IVAN_FIRST_NAME;
        String lastName = IVAN_LAST_NAME;
        double salary = IVAN_SALARY;
        int departmentId = FIRST_DEPARTMENT_ID;

        employeeService.add(IVAN_FIRST_NAME, IVAN_LAST_NAME, IVAN_SALARY, FIRST_DEPARTMENT_ID);
        Employee expectedResult = new Employee(IVAN_FIRST_NAME, IVAN_LAST_NAME,IVAN_SALARY,FIRST_DEPARTMENT_ID);
        Employee actualResult = employeeService.find(firstName, lastName, salary, departmentId);
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void find_exceptionNotFound() {
        String firstName = ILYA_FIRST_NAME;
        String lastName = ILYA_LAST_NAME;
        double salary = ILYA_SALARY;
        int departmentId = SECOND_DEPARTMENT_ID;

        String expectedErrorMessage = "404 Такого сотрудника нет в базе";
        HttpStatusCodeException exception = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.find(firstName, lastName, salary, departmentId));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }

    @Test
    void findAll_success() {
        String expectedResult = "[]";
        String actualResult = employeeService.findAll().toString();
        assertEquals(expectedResult, actualResult);
    }
}