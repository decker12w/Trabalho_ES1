package com.example.demo.dtos.disciplina;

import java.util.UUID;

public record CreateDisciplinaRequestDTO (String nome, String descricao, UUID professorId) {
}
