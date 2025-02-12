package com.example.demo.services.exceptions;

public class DisciplinaNotFoundException extends  RuntimeException{
    public DisciplinaNotFoundException(String message) {
        super(message);
    }
}
