package com.example.demo.dtos.questao;

import com.example.demo.dtos.alternativa.CreateAlternativaRequestDTO;

import java.util.List;
import java.util.UUID;

public record CreateQuestaoRequestDTO(
        String enunciado,
        UUID avaliacaoId,
        List<CreateAlternativaRequestDTO> alternativas
) { }
