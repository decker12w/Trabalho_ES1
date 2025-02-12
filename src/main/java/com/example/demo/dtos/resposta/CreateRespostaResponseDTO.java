package com.example.demo.dtos.resposta;

import com.example.demo.entities.Resposta;

import java.util.UUID;

public record CreateRespostaResponseDTO(
        UUID id,
        UUID alunoId,
        UUID questaoId,
        UUID alternativaId
) {

    public static CreateRespostaResponseDTO from(Resposta resposta) {
        return new CreateRespostaResponseDTO(
                resposta.getId(),
                resposta.getAluno().getId(),
                resposta.getQuestao().getId(),
                resposta.getAlternativa().getId()
        );
    }
}
