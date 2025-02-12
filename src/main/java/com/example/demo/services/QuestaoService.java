package com.example.demo.services;

import com.example.demo.dtos.questao.CreateQuestaoRequestDTO;
import com.example.demo.dtos.questao.CreateQuestaoResponseDTO;
import com.example.demo.entities.Alternativa;
import com.example.demo.entities.Avaliacao;
import com.example.demo.entities.Questao;
import com.example.demo.repositories.AvaliacaoRepository;
import com.example.demo.repositories.QuestaoRepository;
import com.example.demo.services.exceptions.AvaliacaoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestaoService {

    @Autowired
    private final QuestaoRepository questaoRepository;

    @Autowired
    private final AvaliacaoRepository avaliacaoRepository;

    public QuestaoService(QuestaoRepository questaoRepository,
                          AvaliacaoRepository avaliacaoRepository) {
        this.questaoRepository = questaoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public CreateQuestaoResponseDTO create(CreateQuestaoRequestDTO dto) throws AvaliacaoNotFoundException {
        Avaliacao avaliacao = avaliacaoRepository.findById(dto.avaliacaoId())
                .orElseThrow(() -> new  AvaliacaoNotFoundException("Avaliação não encontrada"));

        Questao questao = new Questao();
        questao.setEnunciado(dto.enunciado());
        questao.setAvaliacao(avaliacao);

        List<Alternativa> alternativas = dto.alternativas().stream().map(alternativaDTO -> {
            Alternativa alternativa = new Alternativa();
            alternativa.setEnunciado(alternativaDTO.descricao());
            alternativa.setCorreta(alternativaDTO.correta());
            alternativa.setQuestao(questao);
            return alternativa;
        }).collect(Collectors.toList());

        questao.setAlternativas(alternativas);

        Questao savedQuestao = questaoRepository.save(questao);

        return CreateQuestaoResponseDTO.from(savedQuestao);
    }
}
