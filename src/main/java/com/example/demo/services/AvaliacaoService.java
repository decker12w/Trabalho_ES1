package com.example.demo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.avaliacao.CalculateAlunoGradeResponseDTO;
import com.example.demo.dtos.avaliacao.CreateAvaliacaoRequestDTO;
import com.example.demo.dtos.avaliacao.CreateAvaliacaoResponseDTO;
import com.example.demo.entities.Aluno;
import com.example.demo.entities.Avaliacao;
import com.example.demo.entities.Resposta;
import com.example.demo.repositories.AlunoRepository;
import com.example.demo.repositories.AvaliacaoRepository;
import com.example.demo.repositories.RespostaRepository;
import com.example.demo.services.exceptions.AlunoUserNotFoundException;
import com.example.demo.services.exceptions.AvaliacaoNotFoundException;


@Service
public class AvaliacaoService {


    @Autowired
    private final AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private final RespostaRepository respostaRepository;

    @Autowired
    private final AlunoRepository alunoRepository;

    AvaliacaoService(AvaliacaoRepository avaliacaoRepository ,RespostaRepository respostaRepository,
                     AlunoRepository alunoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.respostaRepository = respostaRepository;
        this.alunoRepository = alunoRepository;
    }

    public CreateAvaliacaoResponseDTO create(CreateAvaliacaoRequestDTO createAvaliacaoRequestDTO) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setTitulo(createAvaliacaoRequestDTO.titulo());
        avaliacao.setPontuacaoMaxima(createAvaliacaoRequestDTO.pontuacaoMaxima());
        avaliacao.setDataAvaliacao(createAvaliacaoRequestDTO.dataAvaliacao());

        avaliacao = avaliacaoRepository.save(avaliacao);
        return CreateAvaliacaoResponseDTO.from(avaliacao);
    }

    public CalculateAlunoGradeResponseDTO calculateAlunoGrade(UUID avalicaoId, UUID alunoId) throws AvaliacaoNotFoundException, AlunoUserNotFoundException {

        Avaliacao avaliacao = avaliacaoRepository.findById(avalicaoId).orElseThrow(() -> new AvaliacaoNotFoundException("Avaliação não encontrada"));
        Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new AlunoUserNotFoundException("Aluno não encontrado"));
        List<Resposta> respostas = respostaRepository.findByAlunoIdAndQuestao_AvaliacaoId(alunoId, avalicaoId);
        int count = 0;
        for (Resposta resposta : respostas) {
            if (resposta.getAlternativa().isCorreta()) {
                count+=1;
            }
        }

        double pontuacao = (double) (count * 10) / avaliacao.getPontuacaoMaxima();


        return new CalculateAlunoGradeResponseDTO(
               pontuacao,aluno.getNome(),aluno.getRA()
        );
    }
}
