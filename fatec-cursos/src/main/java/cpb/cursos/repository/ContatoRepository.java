package cpb.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cpb.cursos.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>{

}
