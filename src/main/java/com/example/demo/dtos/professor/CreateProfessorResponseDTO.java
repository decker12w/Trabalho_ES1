package com.example.demo.dtos.professor;

import com.example.demo.entities.Professor;

import java.util.UUID;

public record CreateProfessorResponseDTO (UUID id, String nome) {
    public static CreateProfessorResponseDTO from(Professor professor) {
        return new CreateProfessorResponseDTO(
                professor.getId(),
                professor.getNome()
        );
    }

}
