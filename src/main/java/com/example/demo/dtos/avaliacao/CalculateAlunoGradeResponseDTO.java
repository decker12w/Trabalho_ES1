package com.example.demo.dtos.avaliacao;

import jakarta.persistence.Column;

public record CalculateAlunoGradeResponseDTO (
        double grade,
        String nome,
        String RA
) {
}
