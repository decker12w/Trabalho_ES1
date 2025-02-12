package com.example.demo.services.exceptions;

public class QuestaoNotFoundException extends RuntimeException {
    public QuestaoNotFoundException(String message) {
        super(message);
    }
}
