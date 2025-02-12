package com.example.demo.dtos.alternativa;

public record CreateAlternativaRequestDTO(
        String descricao,
        boolean correta
) { }
