package edu.fatec.ementario.repository;

import edu.fatec.ementario.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("SELECT e FROM Curso e WHERE e.faculdade.id = :faculdadeId")
    List<Curso> retrieveCursosByFaculdade(@Param("faculdadeId") Long faculdadeId);

    // @Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
    // public List<Person> find(@Param("lastName") String lastName);
}