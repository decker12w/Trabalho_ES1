package com.example.demo.dtos.resposta;

import java.util.UUID;

public record CreateRespostaRequestDTO(
        UUID alunoId,
        UUID questaoId,
        UUID alternativaId,
        UUID avaliacaoId

) {
}
