package com.example.demo.dtos.disciplina;

import com.example.demo.entities.Disciplina;

import java.util.UUID;

public record CreateDisciplinaResponseDTO(
    UUID id,
    String nome,
    String professorNome
) {

    public static CreateDisciplinaResponseDTO from(Disciplina disciplina) {
        return new CreateDisciplinaResponseDTO(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getProfessor().getNome()
        );
    }
}
