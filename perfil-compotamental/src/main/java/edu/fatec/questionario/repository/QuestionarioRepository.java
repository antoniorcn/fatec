package edu.fatec.questionario.repository;

import edu.fatec.questionario.model.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionarioRepository extends JpaRepository<Questionario, Long>{

    Questionario findByNome(String nome);

}
