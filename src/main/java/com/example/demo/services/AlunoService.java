package com.example.demo.services;

import com.example.demo.dtos.alunos.CreateAlunoRequestDTO;
import com.example.demo.dtos.alunos.CreateAlunoResponseDTO;
import com.example.demo.entities.Aluno;
import com.example.demo.repositories.AlunoRepository;
import com.example.demo.services.exceptions.RaAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    private final AlunoRepository alunoRepository;

  
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public CreateAlunoResponseDTO create(CreateAlunoRequestDTO createAlunoRequestDTO) throws RaAlreadyRegisteredException{
        if (alunoRepository.findByRA(createAlunoRequestDTO.matricula()).isPresent()) {
            throw new RaAlreadyRegisteredException("RA já cadastrado.");
        }

        Aluno aluno = new Aluno();
        aluno.setNome(createAlunoRequestDTO.nome());
        aluno.setRA(createAlunoRequestDTO.matricula());

        aluno = alunoRepository.save(aluno);

        return CreateAlunoResponseDTO.from(aluno);
    }

}
