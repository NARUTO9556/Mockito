package com.example.mockito.service;

import com.example.mockito.exception.EmployeeAlreadyAddedException;
import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.exception.EmployeeStorageIsFullException;
import com.example.mockito.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpStatusCodeException;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {
    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    @Test
    void add_success() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        double salary = 22.2;
        int departmentId = 1;

        String expectedResult = String.valueOf(new Employee("Ivan", "Ivanov",22.2,1));
        String actualResult = String.valueOf(employeeService.add(firstName, lastName, salary, departmentId));
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void add_exceptionFullStorage() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        double salary = 22.2;
        int departmentId = 1;

        String expectedErrorMessage = "400 Массив сотрудников переполнен";
        HttpStatusCodeException exception = assertThrows(EmployeeStorageIsFullException.class,
                () -> employeeService.add(firstName, lastName, salary, departmentId));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }
    @Test
    void add_exceptionAlreadyAdded() {
        String firstName = "Иван";
        String lastName = "Иванов";
        double salary = 1000.0;
        int departmentId = 1;

        String expectedErrorMessage = "400 Такой сотрудник уже есть";
        HttpStatusCodeException exception = assertThrows(EmployeeAlreadyAddedException.class,
                () -> employeeService.add(firstName, lastName, salary, departmentId));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }

    @Test
    void remove_success() {
        String firstName = "Иван";
        String lastName = "Иванов";
        double salary = 1000.0;
        int departmentId = 1;

        String expectedResult = String.valueOf(new Employee("Иван", "Иванов",1000.0,1));
        String actualResult = String.valueOf(employeeService.remove(firstName, lastName, salary, departmentId));
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void remove_exceptionNotFound() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        double salary = 22.2;
        int departmentId = 1;

        String expectedErrorMessage = "404 Сотрудник не был удален - не найден в базе";
        HttpStatusCodeException exception = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.remove(firstName, lastName, salary, departmentId));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }

    @Test
    void find_success() {
        String firstName = "Иван";
        String lastName = "Иванов";
        double salary = 1000.0;
        int departmentId = 1;

        String expectedResult = String.valueOf(new Employee("Иван", "Иванов",1000.0,1));
        String actualResult = String.valueOf(employeeService.find(firstName, lastName, salary, departmentId));
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void find_exceptionNotFound() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        double salary = 22.2;
        int departmentId = 1;

        String expectedErrorMessage = "404 Такого сотрудника нет в базе";
        HttpStatusCodeException exception = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.find(firstName, lastName, salary, departmentId));
        assertEquals(expectedErrorMessage,exception.getMessage());
    }

    @Test
    void findAll_success() {
        String expectedResult = "[Имя сотрудника = 'Иван', Фамилия сотрудника = 'Иванов', " +
                "Имя сотрудника = 'Иван1', Фамилия сотрудника = 'Иванов1', " +
                "Имя сотрудника = 'Иван2', Фамилия сотрудника = 'Иванов2', " +
                "Имя сотрудника = 'Пётр', Фамилия сотрудника = 'Петров', " +
                "Имя сотрудника = 'Илья', Фамилия сотрудника = 'Ильин', " +
                "Имя сотрудника = 'Илья1', Фамилия сотрудника = 'Ильин1']";
        String actualResult = employeeService.findAll().toString();
        assertEquals(expectedResult, actualResult);
    }
}