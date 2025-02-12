package com.example.demo.dtos.alternativa;

import java.util.UUID;

public record CreateAlternativaResponseDTO(
        UUID id,
        String descricao,
        boolean correta
) { }
