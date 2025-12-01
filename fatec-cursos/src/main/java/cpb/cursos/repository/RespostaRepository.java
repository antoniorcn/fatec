package cpb.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cpb.cursos.model.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long>{

}
