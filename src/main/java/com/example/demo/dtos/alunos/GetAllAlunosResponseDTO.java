package com.example.demo.dtos.alunos;

import com.example.demo.entities.Aluno;
import java.util.List;

public record GetAllAlunosResponseDTO (List<GetAlunoResponseDTO> alunos) {

    public static GetAllAlunosResponseDTO from(List<Aluno> alunos) {
        return new GetAllAlunosResponseDTO(
                alunos.stream()
                        .map(GetAlunoResponseDTO::from)
                        .toList()
        );
    }
}
