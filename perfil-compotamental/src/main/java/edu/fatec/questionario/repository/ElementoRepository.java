package edu.fatec.questionario.repository;

import java.util.List;

import edu.fatec.questionario.model.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.fatec.questionario.model.Secao;
import edu.fatec.questionario.model.Elemento;

@Repository
public interface ElementoRepository extends JpaRepository<Elemento, Long>{

	List<Elemento> findBySecao(Secao secao);

	@Query("select e from Elemento e where e.questionario = :quest")
	List<Elemento> getAllElementosByQuestionarios(@Param("quest") Questionario quest);


}
