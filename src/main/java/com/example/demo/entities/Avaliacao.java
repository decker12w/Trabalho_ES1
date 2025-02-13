package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity(name = "avaliacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;

    private Date dataAvaliacao;

    @Setter(AccessLevel.NONE)
    private int pontuacaoMaxima;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;


    public void setPontuacaoMaxima(int pontuacaoMaxima) {
        if (pontuacaoMaxima < 0 || pontuacaoMaxima > 100) {
            throw new IllegalArgumentException("Pontuação máxima deve ser entre 0 e 100");
        }
        this.pontuacaoMaxima = pontuacaoMaxima;
    }
}
