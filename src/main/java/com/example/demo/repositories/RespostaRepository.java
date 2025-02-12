package com.example.demo.repositories;

import com.example.demo.entities.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, UUID> {

    List<Resposta> findByAlunoIdAndQuestao_AvaliacaoId(UUID alunoId, UUID avaliacaoId);
}
