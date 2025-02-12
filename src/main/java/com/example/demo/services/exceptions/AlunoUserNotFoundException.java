package com.example.demo.services.exceptions;

public class AlunoUserNotFoundException extends  RuntimeException{
    public AlunoUserNotFoundException(String message) {
        super(message);
    }
}
