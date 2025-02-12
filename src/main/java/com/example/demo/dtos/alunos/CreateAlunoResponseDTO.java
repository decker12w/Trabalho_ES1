package com.example.demo.dtos.alunos;

import com.example.demo.entities.Aluno;

import java.util.UUID;

public record CreateAlunoResponseDTO(UUID id, String nome, String matricula) {

    public static CreateAlunoResponseDTO from(Aluno aluno) {
        return new CreateAlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getRA()
        );
    }
}
