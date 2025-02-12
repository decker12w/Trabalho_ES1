package com.example.demo.dtos.avaliacao;

import com.example.demo.entities.Avaliacao;

import java.util.Date;
import java.util.UUID;

public record CreateAvaliacaoResponseDTO(
        UUID id,
        String titulo,
        Date dataAvaliacao,
        int pontuacaoMaxima
) {

    public static CreateAvaliacaoResponseDTO from(Avaliacao avaliacao) {
        return new CreateAvaliacaoResponseDTO(
                avaliacao.getId(),
                avaliacao.getTitulo(),
                avaliacao.getDataAvaliacao(),
                avaliacao.getPontuacaoMaxima()
        );
    }
}
