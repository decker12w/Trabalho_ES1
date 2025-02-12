package com.example.demo.services.exceptions;

public class AlternativaNotFoundException extends  RuntimeException {
    public AlternativaNotFoundException(String message) {
        super(message);
    }
}
