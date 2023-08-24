package com.example.mockito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class EmployeeStorageIsFullException extends HttpStatusCodeException {
    public EmployeeStorageIsFullException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
