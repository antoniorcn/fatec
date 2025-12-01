package cpb.cursos.repository;

import java.util.List;

import cpb.cursos.model.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cpb.cursos.model.Secao;
import cpb.cursos.model.Elemento;

@Repository
public interface ElementoRepository extends JpaRepository<Elemento, Long>{

	List<Elemento> findBySecao(Secao secao);

	@Query("select e from Elemento e where e.questionario = :quest")
	List<Elemento> getAllElementosByQuestionarios(@Param("quest") Questionario quest);


}
