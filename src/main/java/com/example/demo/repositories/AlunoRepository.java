package com.example.demo.repositories;

import com.example.demo.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {


    Optional<Aluno> findByRA(String matricula);
    List<Aluno> findByDisciplinaId(UUID disciplinaId);
}
