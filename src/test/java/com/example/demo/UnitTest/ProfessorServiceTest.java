package com.example.demo.ServiceUnitTest;

import com.example.demo.dtos.avaliacao.GetAllAvaliacoesResponseDTO;
import com.example.demo.dtos.professor.CreateProfessorRequestDTO;
import com.example.demo.dtos.professor.CreateProfessorResponseDTO;
import com.example.demo.entities.Avaliacao;
import com.example.demo.entities.Disciplina;
import com.example.demo.entities.Professor;
import com.example.demo.repositories.AvaliacaoRepository;
import com.example.demo.repositories.ProfessorRepository;
import com.example.demo.services.ProfessorService;
import com.example.demo.services.exceptions.DisciplinaNotFoundException;
import com.example.demo.services.exceptions.ProfessorNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Test
    @DisplayName("It should be able to create a professor")
    public void ItShouldBeAbleToCreateAProfessor(){
        CreateProfessorRequestDTO request = new CreateProfessorRequestDTO("John Doe");
        Professor professorToSave = new Professor();
        professorToSave.setNome("John Doe");
        Professor savedProfessor = new Professor(UUID.randomUUID(), null, "John Doe");

        when(professorRepository.save(any(Professor.class))).thenReturn(savedProfessor);

        CreateProfessorResponseDTO response = professorService.create(request);

        assertNotNull(response);
        assertEquals("John Doe", response.nome());
        verify(professorRepository, times(1)).save(any(Professor.class));
    }

    @Test
    @DisplayName("It should be able to get all avaliacoes")
    public void ItShouldBeAbleToGetAllAvaliacoes(){
        UUID professorId = UUID.randomUUID();
        UUID disciplinaId = UUID.randomUUID();
        Disciplina disciplina = new Disciplina();
        disciplina.setId(disciplinaId);

        Professor professor = new Professor();
        professor.setId(professorId);
        professor.setNome("Jane Doe");
        professor.setDisciplina(disciplina);

        Avaliacao avaliacao1 = new Avaliacao(UUID.randomUUID(), "Avaliacao 1", new Date(), 50, disciplina);
        Avaliacao avaliacao2 = new Avaliacao(UUID.randomUUID(), "Avaliacao 2", new Date(), 75, disciplina);

        when(professorRepository.findById(professorId)).thenReturn(Optional.of(professor));
        when(avaliacaoRepository.findByDisciplinaId(disciplinaId)).thenReturn(Arrays.asList(avaliacao1, avaliacao2));

        GetAllAvaliacoesResponseDTO response = professorService.getAllAvaliacoes(professorId);

        assertNotNull(response);
        assertEquals(2, response.avaliacoes().size());
        verify(professorRepository, times(1)).findById(professorId);
        verify(avaliacaoRepository, times(1)).findByDisciplinaId(disciplinaId);
    }

    @Test
    @DisplayName("It not should be able to get all avaliacoes if professor does not exist")
    public void ItNotShouldBeAbleToGetAllAvaliacoesIfProfessorDoesNotExist(){
        UUID professorId = UUID.randomUUID();
        when(professorRepository.findById(professorId)).thenReturn(Optional.empty());

        assertThrows(ProfessorNotFoundException.class, () -> professorService.getAllAvaliacoes(professorId));
        verify(professorRepository, times(1)).findById(professorId);
        verify(avaliacaoRepository, never()).findByDisciplinaId(any(UUID.class));
    }

    @Test
    @DisplayName("It not should be able to get all avaliacoes if professor does not have disciplina")
    public void ItNotShouldBeAbleToGetAllAvaliacoesIfProfessorDoesNotHaveDisciplina(){
        UUID professorId = UUID.randomUUID();
        Professor professor = new Professor();
        professor.setId(professorId);
        professor.setNome("John Smith");
        professor.setDisciplina(null);

        when(professorRepository.findById(professorId)).thenReturn(Optional.of(professor));

        assertThrows(DisciplinaNotFoundException.class, () -> professorService.getAllAvaliacoes(professorId));
        verify(professorRepository, times(1)).findById(professorId);
        verify(avaliacaoRepository, never()).findByDisciplinaId(any(UUID.class));
    }
}
