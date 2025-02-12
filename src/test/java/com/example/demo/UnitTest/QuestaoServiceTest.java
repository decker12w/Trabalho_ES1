package com.example.demo.UnitTest;

import com.example.demo.dtos.questao.CreateQuestaoRequestDTO;
import com.example.demo.repositories.AvaliacaoRepository;
import com.example.demo.repositories.QuestaoRepository;
import com.example.demo.services.RespostaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.dtos.alternativa.CreateAlternativaRequestDTO;
import com.example.demo.dtos.questao.CreateQuestaoResponseDTO;
import com.example.demo.entities.Avaliacao;
import com.example.demo.entities.Questao;
import com.example.demo.services.QuestaoService;
import com.example.demo.services.exceptions.AvaliacaoNotFoundException;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestaoServiceTest {

    @InjectMocks
    private RespostaService respostaService;

    @Mock
    private QuestaoRepository questaoRepository;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @InjectMocks
    private QuestaoService questaoService;

    @Test
    @DisplayName("It should be able to create a questao")
    public void ItShouldBeAbleToCreateAQuestao(){
        UUID avaliacaoId = UUID.randomUUID();
        CreateAlternativaRequestDTO alternativa1 = new CreateAlternativaRequestDTO("Brasília", true);
        CreateAlternativaRequestDTO alternativa2 = new CreateAlternativaRequestDTO("Alternativa B", false);
        List<CreateAlternativaRequestDTO> alternativas = List.of(alternativa1, alternativa2);

        CreateQuestaoRequestDTO request = new CreateQuestaoRequestDTO(
                "Qual é a capital do Brasil?",
                avaliacaoId,
                alternativas
        );

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(avaliacaoId);

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.of(avaliacao));

        when(questaoRepository.save(any(Questao.class))).thenAnswer(invocation -> {
            Questao questao = invocation.getArgument(0);
            questao.setId(UUID.randomUUID());
            return questao;
        });

        CreateQuestaoResponseDTO response = questaoService.create(request);

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(request.enunciado(), response.enunciado());
        assertEquals(avaliacaoId, response.avaliacaoId());
        assertEquals(2, response.alternativas().size());
    }

    @Test
    @DisplayName("It should not be able to create a questao if avaliacao does not exist")
    public void ItShouldNotBeAbleToCreateAQuestaoIfAvaliacaoDoesNotExist(){
        UUID avaliacaoId = UUID.randomUUID();
        CreateAlternativaRequestDTO alternativa1 = new CreateAlternativaRequestDTO("Brasília", true);
        CreateAlternativaRequestDTO alternativa2 = new CreateAlternativaRequestDTO("Alternativa B", false);
        List<CreateAlternativaRequestDTO> alternativas = List.of(alternativa1, alternativa2);

        CreateQuestaoRequestDTO request = new CreateQuestaoRequestDTO(
                "Qual é a capital do Brasil?",
                avaliacaoId,
                alternativas
        );

        when(avaliacaoRepository.findById(avaliacaoId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AvaliacaoNotFoundException.class, () -> {
            questaoService.create(request);
        });
        assertEquals("Avaliação não encontrada", exception.getMessage());
    }
}
