package com.example.demo.services.exceptions;

public class RaAlreadyRegisteredException extends RuntimeException{
    public RaAlreadyRegisteredException(String message) {
        super(message);
    }
}
