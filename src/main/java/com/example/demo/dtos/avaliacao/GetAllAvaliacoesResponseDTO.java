package com.example.demo.dtos.avaliacao;

import com.example.demo.entities.Avaliacao;

import java.util.List;

public record GetAllAvaliacoesResponseDTO(List<GetAvaliacaoResponseDTO> avaliacoes) {

    public static GetAllAvaliacoesResponseDTO from(List<Avaliacao> avaliacoes) {
        return new GetAllAvaliacoesResponseDTO(
                avaliacoes.stream()
                        .map(GetAvaliacaoResponseDTO::from)
                        .toList()
        );
    }
}
