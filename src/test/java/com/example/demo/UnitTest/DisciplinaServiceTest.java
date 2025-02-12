package com.example.demo.UnitTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.demo.repositories.AlunoRepository;
import com.example.demo.repositories.DisciplinaRepository;

import com.example.demo.dtos.disciplina.CreateDisciplinaRequestDTO;
import com.example.demo.dtos.disciplina.CreateDisciplinaResponseDTO;
import com.example.demo.dtos.alunos.GetAllAlunosResponseDTO;
import com.example.demo.entities.Aluno;
import com.example.demo.entities.Disciplina;
import com.example.demo.entities.Professor;
import com.example.demo.repositories.ProfessorRepository;
import com.example.demo.services.DisciplinaService;
import com.example.demo.services.exceptions.ProfessorNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    @Test
    @DisplayName("It should be able to create a disciplina")
    public void ItShouldBeAbleToCreateADisciplina(){
        UUID professorId = UUID.randomUUID();
        CreateDisciplinaRequestDTO requestDTO = new CreateDisciplinaRequestDTO("Matemática", "MAT101", professorId);
        Professor professor = new Professor();
        professor.setId(professorId);
        professor.setNome("Dr. Silva");

        when(professorRepository.findById(professorId)).thenReturn(Optional.of(professor));

        Disciplina disciplinaToSave = new Disciplina();
        disciplinaToSave.setNome(requestDTO.nome());
        disciplinaToSave.setCodigo(requestDTO.descricao());
        disciplinaToSave.setProfessor(professor);

        Disciplina savedDisciplina = new Disciplina();
        savedDisciplina.setId(UUID.randomUUID());
        savedDisciplina.setNome(requestDTO.nome());
        savedDisciplina.setCodigo(requestDTO.descricao());
        savedDisciplina.setProfessor(professor);

        when(disciplinaRepository.save(any(Disciplina.class))).thenReturn(savedDisciplina);

        CreateDisciplinaResponseDTO response = disciplinaService.create(requestDTO);

        assertNotNull(response);
        assertEquals(savedDisciplina.getId(), response.id());
        assertEquals("Matemática", response.nome());
        assertEquals("Dr. Silva", response.professorNome());
        verify(professorRepository, times(1)).findById(professorId);
        verify(disciplinaRepository, times(1)).save(any(Disciplina.class));
    }

    @Test
    @DisplayName("It should throw exception when professor is not found for disciplina creation")
    public void ItShouldThrowExceptionForMissingProfessor(){

        UUID professorId = UUID.randomUUID();
        CreateDisciplinaRequestDTO requestDTO = new CreateDisciplinaRequestDTO("Matemática", "MAT101", professorId);

        when(professorRepository.findById(professorId)).thenReturn(Optional.empty());

        assertThrows(ProfessorNotFoundException.class, () -> disciplinaService.create(requestDTO));
        verify(professorRepository, times(1)).findById(professorId);
        verify(disciplinaRepository, never()).save(any(Disciplina.class));
    }

    @Test
    @DisplayName("It should be able to get all alunos from disciplina")
    public void ItShouldBeAbleToGetAllAlunosFromDisciplina(){
      
        UUID disciplinaId = UUID.randomUUID();
        Aluno aluno1 = new Aluno();
        aluno1.setId(UUID.randomUUID());
        aluno1.setNome("Aluno 1");

        Aluno aluno2 = new Aluno();
        aluno2.setId(UUID.randomUUID());
        aluno2.setNome("Aluno 2");

        List<Aluno> alunos = Arrays.asList(aluno1, aluno2);

        when(alunoRepository.findByDisciplinaId(disciplinaId)).thenReturn(alunos);

        GetAllAlunosResponseDTO response = disciplinaService.getAllStudentsFromDisciplina(disciplinaId);

        assertNotNull(response);
        assertEquals(2, response.alunos().size());
        verify(alunoRepository, times(1)).findByDisciplinaId(disciplinaId);
    }

    @Test
    @DisplayName("It should return empty list when no alunos are found for disciplina")
    public void ItShouldReturnEmptyListWhenNoAlunosFound(){
    
        UUID disciplinaId = UUID.randomUUID();

        when(alunoRepository.findByDisciplinaId(disciplinaId)).thenReturn(Collections.emptyList());

        GetAllAlunosResponseDTO response = disciplinaService.getAllStudentsFromDisciplina(disciplinaId);

        assertNotNull(response);
        assertTrue(response.alunos().isEmpty());
        verify(alunoRepository, times(1)).findByDisciplinaId(disciplinaId);
    }
}
