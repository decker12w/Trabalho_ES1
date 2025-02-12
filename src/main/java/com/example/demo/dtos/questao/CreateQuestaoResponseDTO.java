package com.example.demo.dtos.questao;

import com.example.demo.dtos.alternativa.CreateAlternativaResponseDTO;
import com.example.demo.entities.Questao;
import java.util.UUID;
import java.util.stream.Collectors;

public record CreateQuestaoResponseDTO(
        UUID id,
        String enunciado,
        UUID avaliacaoId,
        java.util.List<CreateAlternativaResponseDTO> alternativas
) {
    public static CreateQuestaoResponseDTO from(Questao questao) {
        return new CreateQuestaoResponseDTO(
                questao.getId(),
                questao.getEnunciado(),
                questao.getAvaliacao().getId(),
                questao.getAlternativas().stream()
                        .map(a -> new CreateAlternativaResponseDTO(a.getId(), a.getEnunciado(), a.isCorreta()))
                        .collect(Collectors.toList())
        );
    }
}
