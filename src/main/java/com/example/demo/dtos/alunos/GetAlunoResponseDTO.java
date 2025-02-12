package com.example.demo.dtos.alunos;

import com.example.demo.entities.Aluno;

import java.util.UUID;

public record GetAlunoResponseDTO (
        UUID id, String nome, String matricula
) {

    public static GetAlunoResponseDTO from(Aluno aluno){
        return new GetAlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getRA()
        );
    }
}
