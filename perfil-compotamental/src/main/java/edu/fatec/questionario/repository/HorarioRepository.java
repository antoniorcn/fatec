package edu.fatec.questionario.repository;

import edu.fatec.questionario.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Agendamento, Long>{

}
