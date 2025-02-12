package com.example.demo.services;

import com.example.demo.dtos.alunos.GetAllAlunosResponseDTO;
import com.example.demo.dtos.disciplina.CreateDisciplinaRequestDTO;
import com.example.demo.dtos.disciplina.CreateDisciplinaResponseDTO;
import com.example.demo.entities.Aluno;
import com.example.demo.entities.Disciplina;
import com.example.demo.entities.Professor;
import com.example.demo.repositories.AlunoRepository;
import com.example.demo.repositories.DisciplinaRepository;
import com.example.demo.repositories.ProfessorRepository;
import com.example.demo.services.exceptions.ProfessorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DisciplinaService {


    @Autowired
    private final DisciplinaRepository disciplinaRepository;

    @Autowired
    private final AlunoRepository alunoRepository;

    @Autowired
    private final ProfessorRepository professorRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, ProfessorRepository professorRepository
            ,AlunoRepository alunoRepository
    ) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;

    }

    public CreateDisciplinaResponseDTO create(CreateDisciplinaRequestDTO createDisciplinaRequestDTO) throws ProfessorNotFoundException {

        Professor professor = professorRepository.findById(createDisciplinaRequestDTO.professorId()).orElseThrow(() ->
                new ProfessorNotFoundException("Professor não encontrado"));

        Disciplina disciplina = new Disciplina();
        disciplina.setNome(createDisciplinaRequestDTO.nome());
        disciplina.setCodigo(createDisciplinaRequestDTO.descricao());
        disciplina.setProfessor(professor);

        disciplina = disciplinaRepository.save(disciplina);
        return CreateDisciplinaResponseDTO.from(disciplina);
    }

    public GetAllAlunosResponseDTO getAllStudentsFromDisciplina(UUID id) {
        List<Aluno> alunos = alunoRepository.findByDisciplinaId(id);

        return GetAllAlunosResponseDTO.from(alunos);

    }
}
