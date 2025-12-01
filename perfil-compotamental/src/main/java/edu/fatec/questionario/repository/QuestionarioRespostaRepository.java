package edu.fatec.questionario.repository;

import edu.fatec.questionario.model.Questionario;
import edu.fatec.questionario.model.QuestionarioResposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionarioRespostaRepository extends JpaRepository<QuestionarioResposta, Long> {

    @Query("select e from QuestionarioResposta e where e.questionario = :quest")
    List<QuestionarioResposta> getAllQuestionarioRespostas(@Param("quest") Questionario questionario);
}
