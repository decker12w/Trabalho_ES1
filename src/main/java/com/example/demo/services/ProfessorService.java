package com.example.demo.services;

import com.example.demo.entities.Avaliacao;
import com.example.demo.entities.Disciplina;
import com.example.demo.dtos.avaliacao.GetAllAvaliacoesResponseDTO;
import com.example.demo.dtos.professor.CreateProfessorRequestDTO;
import com.example.demo.dtos.professor.CreateProfessorResponseDTO;
import com.example.demo.entities.Professor;
import com.example.demo.repositories.AvaliacaoRepository;
import com.example.demo.repositories.ProfessorRepository;
import com.example.demo.services.exceptions.DisciplinaNotFoundException;
import com.example.demo.services.exceptions.ProfessorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfessorService {

    @Autowired
    private final ProfessorRepository professorRepository;

    @Autowired
    private final AvaliacaoRepository avaliacaoRepository;

    public ProfessorService(ProfessorRepository professorRepository, AvaliacaoRepository avaliacaoRepository) {

        this.professorRepository = professorRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }


    public CreateProfessorResponseDTO create(CreateProfessorRequestDTO createProfessorRequestDTO) {

        Professor professor = new Professor();
        professor.setNome(createProfessorRequestDTO.nome());

        professor = professorRepository.save(professor);

        return CreateProfessorResponseDTO.from(professor);

    }

    public GetAllAvaliacoesResponseDTO getAllAvaliacoes(UUID id) throws ProfessorNotFoundException, DisciplinaNotFoundException {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado"));

        Disciplina disciplina = professor.getDisciplina();

        if (disciplina == null){
            throw new DisciplinaNotFoundException("Professor não possui disciplina");
        }

        List<Avaliacao> avaliacaoList = avaliacaoRepository.findByDisciplinaId(disciplina.getId());

        return GetAllAvaliacoesResponseDTO.from(avaliacaoList);
    }

}
