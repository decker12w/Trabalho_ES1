package com.example.demo.services;

import com.example.demo.dtos.resposta.CreateRespostaRequestDTO;
import com.example.demo.dtos.resposta.CreateRespostaResponseDTO;
import com.example.demo.entities.Alternativa;
import com.example.demo.entities.Aluno;
import com.example.demo.entities.Questao;
import com.example.demo.entities.Resposta;
import com.example.demo.repositories.*;
import com.example.demo.services.exceptions.AlternativaNotFoundException;
import com.example.demo.services.exceptions.AlunoUserNotFoundException;
import com.example.demo.services.exceptions.QuestaoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespostaService {

    @Autowired
    private final RespostaRepository respostaRepository;

    @Autowired
    private final AlunoRepository alunoRepository;

    @Autowired
    private final QuestaoRepository questaoRepository;

    @Autowired
    private final AlternativaRepository alternativaRepository;

    RespostaService(RespostaRepository respostaRepository, AlunoRepository alunoRepository,
                    QuestaoRepository questaoRepository, AlternativaRepository alternativaRepository) {
        this.respostaRepository = respostaRepository;
        this.alunoRepository = alunoRepository;
        this.questaoRepository = questaoRepository;
        this.alternativaRepository = alternativaRepository;

    }


    public CreateRespostaResponseDTO create(CreateRespostaRequestDTO createRespostaRequestDTO) throws AlunoUserNotFoundException, QuestaoNotFoundException, AlternativaNotFoundException {
        Resposta resposta = new Resposta();

        Aluno aluno = alunoRepository.findById(createRespostaRequestDTO.alunoId())
                .orElseThrow(() -> new AlunoUserNotFoundException("Aluno não encontrado"));

        Questao questao = questaoRepository.findById(createRespostaRequestDTO.questaoId())
                .orElseThrow(() -> new QuestaoNotFoundException("Questão não encontrada"));

        Alternativa alternativa = alternativaRepository.findById(createRespostaRequestDTO.alternativaId())
                .orElseThrow(() -> new AlternativaNotFoundException("Alternativa não encontrada"));

        resposta.setAluno(aluno);
        resposta.setQuestao(questao);
        resposta.setAlternativa(alternativa);

        resposta = respostaRepository.save(resposta);
        return CreateRespostaResponseDTO.from(resposta);
    }

}
