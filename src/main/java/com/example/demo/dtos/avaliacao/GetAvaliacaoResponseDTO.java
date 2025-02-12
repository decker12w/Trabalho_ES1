package com.example.demo.dtos.avaliacao;

import com.example.demo.entities.Avaliacao;

public record GetAvaliacaoResponseDTO(
        String id,
        String titulo,
        String dataAvaliacao,
        int pontuacaoMaxima
) {
    public static GetAvaliacaoResponseDTO from(Avaliacao avaliacao) {
        return new GetAvaliacaoResponseDTO(
                avaliacao.getId().toString(),
                avaliacao.getTitulo(),
                avaliacao.getDataAvaliacao().toString(),
                avaliacao.getPontuacaoMaxima()
        );
    }
}
