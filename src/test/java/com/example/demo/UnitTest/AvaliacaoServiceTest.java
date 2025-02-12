package com.example.demo.ServiceUnitTest;

import com.example.demo.dtos.avaliacao.CalculateAlunoGradeResponseDTO;
import com.example.demo.dtos.avaliacao.CreateAvaliacaoRequestDTO;
import com.example.demo.dtos.avaliacao.CreateAvaliacaoResponseDTO;
import com.example.demo.entities.Aluno;
import com.example.demo.entities.Avaliacao;
import com.example.demo.entities.Resposta;
import com.example.demo.entities.Alternativa;
import com.example.demo.repositories.AlunoRepository;
import com.example.demo.repositories.AvaliacaoRepository;
import com.example.demo.repositories.RespostaRepository;
import com.example.demo.services.AvaliacaoService;
import com.example.demo.services.exceptions.AlunoUserNotFoundException;
import com.example.demo.services.exceptions.AvaliacaoNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private RespostaRepository respostaRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Test
    @DisplayName("It should be able to create a avaliacao")
    public void ItShouldBeAbleToCreateAAvaliacao() {
        String titulo = "Prova Final";
        int pontuacaoMaxima = 100;
        Date dataAvaliacao = new Date();
        CreateAvaliacaoRequestDTO requestDTO = new CreateAvaliacaoRequestDTO(titulo, dataAvaliacao, pontuacaoMaxima);

        Avaliacao avaliacaoToSave = new Avaliacao();
        avaliacaoToSave.setTitulo(titulo);
        avaliacaoToSave.setPontuacaoMaxima(pontuacaoMaxima);
        avaliacaoToSave.setDataAvaliacao(dataAvaliacao);

        Avaliacao savedAvaliacao = new Avaliacao();
        UUID avaliacaoId = UUID.randomUUID();
        savedAvaliacao.setId(avaliacaoId);
        savedAvaliacao.setTitulo(titulo);
        savedAvaliacao.setPontuacaoMaxima(pontuacaoMaxima);
        savedAvaliacao.setDataAvaliacao(dataAvaliacao);

        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(savedAvaliacao);


        CreateAvaliacaoResponseDTO responseDTO = avaliacaoService.create(requestDTO);


        assertNotNull(responseDTO);
        assertEquals(avaliacaoId, responseDTO.id());
        assertEquals(titulo, responseDTO.titulo());
        assertEquals(pontuacaoMaxima, responseDTO.pontuacaoMaxima());
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    @DisplayName("It should be able to calculate the grade of a aluno in an avaliacao")
    public void ItShouldBeAbleToCalculateAlunoGrade() {
        UUID avaliacaoId = UUID.randomUUID();
        UUID alunoId = UUID.randomUUID();

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(avaliacaoId);
        avaliacao.setTitulo("Prova Final");
        avaliacao.setPontuacaoMaxima(100);
        avaliacao.setDataAvaliacao(new Date());

        Aluno aluno = new Aluno();
        aluno.setId(alunoId);
        aluno.setNome("João da Silva");
        aluno.setRA("2021001");

        Alternativa alternativa1 = new Alternativa();
        alternativa1.setCorreta(true);
        Alternativa alternativa2 = new Alternativa();
        alternativa2.setCorreta(false);

        Resposta resposta1 = new Resposta();
        resposta1.setAlternativa(alternativa1);

        Resposta resposta2 = new Resposta();
        resposta2.setAlternativa(alternativa2);

        List<Resposta> respostas = Arrays.asList(resposta1, resposta2);

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.of(avaliacao));
        when(alunoRepository.findById(alunoId)).thenReturn(Optional.of(aluno));
        when(respostaRepository.findByAlunoIdAndQuestao_AvaliacaoId(alunoId, avaliacaoId))
                .thenReturn(respostas);

        CalculateAlunoGradeResponseDTO gradeResponse = avaliacaoService.calculateAlunoGrade(avaliacaoId, alunoId);


        assertNotNull(gradeResponse);
        assertEquals(0.1, gradeResponse.grade());
        assertEquals(aluno.getNome(), gradeResponse.nome());
        assertEquals(aluno.getRA(), gradeResponse.RA());
        verify(avaliacaoRepository, times(1)).findById(avaliacaoId);
        verify(alunoRepository, times(1)).findById(alunoId);
    }

    @Test
    @DisplayName("It should throw AvaliacaoNotFound when avaliacao is not found")
    public void ItShouldThrowExceptionIfAvaliacaoNotFound() {

        UUID avaliacaoId = UUID.randomUUID();
        UUID alunoId = UUID.randomUUID();

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.empty());


        assertThrows(AvaliacaoNotFoundException.class, () -> {
            avaliacaoService.calculateAlunoGrade(avaliacaoId, alunoId);
        });
        verify(avaliacaoRepository, times(1)).findById(avaliacaoId);
        verify(alunoRepository, never()).findById(any(UUID.class));
    }

    @Test
    @DisplayName("It should throw AlunoUserNotFoundException when aluno is not found")
    public void ItShouldThrowExceptionIfAlunoNotFound() {

        UUID avaliacaoId = UUID.randomUUID();
        UUID alunoId = UUID.randomUUID();

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(avaliacaoId);
        avaliacao.setTitulo("Prova Final");
        avaliacao.setPontuacaoMaxima(100);
        avaliacao.setDataAvaliacao(new Date());

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.of(avaliacao));
        when(alunoRepository.findById(alunoId)).thenReturn(Optional.empty());


        assertThrows(AlunoUserNotFoundException.class, () -> {
            avaliacaoService.calculateAlunoGrade(avaliacaoId, alunoId);
        });
        verify(avaliacaoRepository, times(1)).findById(avaliacaoId);
        verify(alunoRepository, times(1)).findById(alunoId);
    }
}
