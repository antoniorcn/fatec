package edu.fatec.questionario.repository;

import edu.fatec.questionario.model.Secao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Secao, Long>{

}
