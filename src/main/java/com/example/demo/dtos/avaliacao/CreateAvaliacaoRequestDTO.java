package com.example.demo.dtos.avaliacao;

import java.util.Date;
import java.util.UUID;

public record CreateAvaliacaoRequestDTO(
       String titulo,
       Date dataAvaliacao,
       int pontuacaoMaxima
) {


}
