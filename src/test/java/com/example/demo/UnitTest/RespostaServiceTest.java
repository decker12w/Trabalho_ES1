package com.example.demo.UnitTest;

import com.example.demo.entities.Alternativa;
import com.example.demo.entities.Aluno;
import com.example.demo.entities.Questao;
import com.example.demo.repositories.AlternativaRepository;
import com.example.demo.repositories.AlunoRepository;
import com.example.demo.repositories.QuestaoRepository;
import com.example.demo.services.RespostaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.dtos.resposta.CreateRespostaRequestDTO;
import com.example.demo.dtos.resposta.CreateRespostaResponseDTO;
import com.example.demo.entities.Resposta;
import com.example.demo.repositories.RespostaRepository;
import com.example.demo.services.exceptions.AlternativaNotFoundException;
import com.example.demo.services.exceptions.AlunoUserNotFoundException;
import com.example.demo.services.exceptions.QuestaoNotFoundException;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RespostaServiceTest {

    @Mock
    private RespostaRepository respostaRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private QuestaoRepository questaoRepository;

    @Mock
    private AlternativaRepository alternativaRepository;

    @InjectMocks
    private RespostaService respostaService;

    @Test
    @DisplayName("It should be able to create a resposta")
    public void ItShouldBeAbleToCreateAResposta() {
        UUID alunoId = UUID.randomUUID();
        UUID questaoId = UUID.randomUUID();
        UUID alternativaId = UUID.randomUUID();
        CreateRespostaRequestDTO request = new CreateRespostaRequestDTO(alunoId, questaoId, alternativaId);

        Aluno aluno = new Aluno();
        aluno.setId(alunoId);
        Questao questao = new Questao();
        questao.setId(questaoId);
        Alternativa alternativa = new Alternativa();
        alternativa.setId(alternativaId);

        when(alunoRepository.findById(alunoId)).thenReturn(Optional.of(aluno));
        when(questaoRepository.findById(questaoId)).thenReturn(Optional.of(questao));
        when(alternativaRepository.findById(alternativaId)).thenReturn(Optional.of(alternativa));

        Resposta resposta = new Resposta();
        resposta.setId(UUID.randomUUID());
        resposta.setAluno(aluno);
        resposta.setQuestao(questao);
        resposta.setAlternativa(alternativa);

        when(respostaRepository.save(any(Resposta.class))).thenReturn(resposta);

        CreateRespostaResponseDTO response = respostaService.create(request);

        assertNotNull(response);
        assertInstanceOf(CreateRespostaResponseDTO.class, response);
        assertEquals(alunoId, response.alunoId());
        assertEquals(questaoId, response.questaoId());
        assertEquals(alternativaId, response.alternativaId());

    }

    @Test
    @DisplayName("It should not be able to create a resposta if aluno does not exist")
    public void ItShouldNotBeAbleToCreateAAnswerIfAlunoDoesNotExist() {
        UUID alunoId = UUID.randomUUID();
        UUID questaoId = UUID.randomUUID();
        UUID alternativaId = UUID.randomUUID();
        CreateRespostaRequestDTO request = new CreateRespostaRequestDTO(alunoId, questaoId, alternativaId);

        when(alunoRepository.findById(alunoId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AlunoUserNotFoundException.class, () -> {
            respostaService.create(request);
        });
        assertEquals("Aluno não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("It should not be able to create a resposta if questao does not exist")
    public void ItShouldBeAbleToCreateARespostaIfQuestaoDoesNoExist() {
        UUID alunoId = UUID.randomUUID();
        UUID questaoId = UUID.randomUUID();
        UUID alternativaId = UUID.randomUUID();
        CreateRespostaRequestDTO request = new CreateRespostaRequestDTO(alunoId, questaoId, alternativaId);

        Aluno aluno = new Aluno();
        aluno.setId(alunoId);
        when(alunoRepository.findById(alunoId)).thenReturn(Optional.of(aluno));
        when(questaoRepository.findById(questaoId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(QuestaoNotFoundException.class, () -> {
            respostaService.create(request);
        });
        assertEquals("Questão não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("It should not be able to create a resposta if alternativa does not exist")
    public void ItShouldBeAbleToCreateARespostaIfAlternativaDoesNotExist() {
        UUID alunoId = UUID.randomUUID();
        UUID questaoId = UUID.randomUUID();
        UUID alternativaId = UUID.randomUUID();
        CreateRespostaRequestDTO request = new CreateRespostaRequestDTO(alunoId, questaoId, alternativaId);

        Aluno aluno = new Aluno();
        aluno.setId(alunoId);
        Questao questao = new Questao();
        questao.setId(questaoId);
        when(alunoRepository.findById(alunoId)).thenReturn(Optional.of(aluno));
        when(questaoRepository.findById(questaoId)).thenReturn(Optional.of(questao));
        when(alternativaRepository.findById(alternativaId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AlternativaNotFoundException.class, () -> {
            respostaService.create(request);
        });
        assertEquals("Alternativa não encontrada", exception.getMessage());
    }
}
