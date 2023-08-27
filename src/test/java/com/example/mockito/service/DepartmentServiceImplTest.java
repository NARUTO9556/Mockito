package com.example.mockito.service;

import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.example.mockito.Generator.EmployeeGenerator.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;
    @Test
    void getEmployeeByDepartment_success() {
        Integer depId = null;

        Employee getPetrFirstDep = getPetrFirstDep();
        Employee getIvanFirstDep = getIvanFirstDep();
        Employee getIlyaSecondDep = getIlyaSecondDep();

        when(employeeService.findAll()).thenReturn(
                List.of(getPetrFirstDep, getIvanFirstDep, getIlyaSecondDep)
        );
        Map<Integer, List<Employee>> expectedEmployeeMap = new HashMap<>();
        expectedEmployeeMap.put(FIRST_DEPARTMENT_ID, List.of(getPetrFirstDep, getIvanFirstDep));
        expectedEmployeeMap.put(SECOND_DEPARTMENT_ID, List.of(getIlyaSecondDep));

        Map<Integer, List<Employee>> actualEmployeeMap = departmentService.getEmployeeByDepartment(depId);
        assertEquals(expectedEmployeeMap, actualEmployeeMap);
        verify(employeeService).findAll();
    }

    @Test
    void getEmployeeByDepartment_whenDepIdIsNull() {
        Integer depId = FIRST_DEPARTMENT_ID;

        Employee getPetrFirstDep = getPetrFirstDep();
        Employee getIvanFirstDep = getIvanFirstDep();
        Employee getIlyaSecondDep = getIlyaSecondDep();

        when(employeeService.findAll()).thenReturn(
                List.of(getPetrFirstDep, getIvanFirstDep, getIlyaSecondDep)
        );
        Map<Integer, List<Employee>> expectedEmployeeMap = new HashMap<>();
        expectedEmployeeMap.put(FIRST_DEPARTMENT_ID, List.of(getPetrFirstDep, getIvanFirstDep));

        Map<Integer, List<Employee>> actualEmployeeMap = departmentService.getEmployeeByDepartment(depId);
        assertEquals(expectedEmployeeMap, actualEmployeeMap);
        verify(employeeService).findAll();
    }

    @Test
    void getSalarySumByDepartment_success() {
        Employee getPetrFirstDep = getPetrFirstDep();
        Employee getIvanFirstDep = getIvanFirstDep();

        when(employeeService.findAll()).thenReturn(
                List.of(getPetrFirstDep, getIvanFirstDep)
        );
        double expected = 1777.0;
        double actual = departmentService.getSalarySumByDepartment(FIRST_DEPARTMENT_ID);
        assertEquals(expected, actual);

    }

    @Test
    void getEmployeeWithMaxSalary_success() {
        int depId = FIRST_DEPARTMENT_ID;

        Employee employeeWithMaxSalary = getIvanFirstDep();
        Employee getPetrFirstDep = getPetrFirstDep();
        Employee getIvanFirstDep = getIvanFirstDep();
        Employee getIlyaSecondDep = getIlyaSecondDep();

        when(employeeService.findAll()).thenReturn(
                List.of(getPetrFirstDep, getIvanFirstDep, getIlyaSecondDep)
        );

        Employee actualEmployeeWithMaxSalary = departmentService.getEmployeeWithMaxSalary(depId);
        assertEquals(employeeWithMaxSalary, actualEmployeeWithMaxSalary);
        assertEquals(depId, actualEmployeeWithMaxSalary.getDepartmentId());
        assertTrue(getIvanFirstDep.getSalary() > getPetrFirstDep.getSalary());
        verify(employeeService).findAll();
    }
    @Test
    void getEmployeeWithMaxSalary_withEmployeeNotFoundException() {
        int depId = FIRST_DEPARTMENT_ID;

        when(employeeService.findAll()).thenReturn(Collections.singletonList(getIlyaSecondDep()));
        String expectedMessage = "404 Сотрудник с максимальной зарплатой не найден";
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> departmentService.getEmployeeWithMaxSalary(depId));
        assertEquals(expectedMessage, exception.getMessage());
        verify(employeeService).findAll();
    }

    @Test
    void getEmployeeWithMinSalary_success() {
        int depId = FIRST_DEPARTMENT_ID;

        Employee employeeWithMinSalary = getPetrFirstDep();
        Employee getPetrFirstDep = getPetrFirstDep();
        Employee getIvanFirstDep = getIvanFirstDep();
        Employee getIlyaSecondDep = getIlyaSecondDep();

        when(employeeService.findAll()).thenReturn(
                List.of(getPetrFirstDep, getIvanFirstDep, getIlyaSecondDep)
        );

        Employee actualEmployeeWithMinSalary = departmentService.getEmployeeWithMinSalary(depId);
        assertEquals(employeeWithMinSalary, actualEmployeeWithMinSalary);
        assertEquals(depId, actualEmployeeWithMinSalary.getDepartmentId());
        assertTrue(getIvanFirstDep.getSalary() > getPetrFirstDep.getSalary());
        verify(employeeService).findAll();
    }
    @Test
    void getEmployeeWithMinSalary_withEmployeeNotFoundException() {
        int depId = FIRST_DEPARTMENT_ID;

        when(employeeService.findAll()).thenReturn(Collections.singletonList(getIlyaSecondDep()));
        String expectedMessage = "404 Сотрудник с минимальной зарплатой не найден";
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> departmentService.getEmployeeWithMinSalary(depId));
        assertEquals(expectedMessage, exception.getMessage());
        verify(employeeService).findAll();
    }
}