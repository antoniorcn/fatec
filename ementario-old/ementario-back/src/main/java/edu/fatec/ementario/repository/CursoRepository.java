package edu.fatec.ementario.repository;

import edu.fatec.ementario.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

//    @Query("SELECT c FROM Faculdade c WHERE c.categoriaPai is null")
//    List<Faculdade> retrieveMainCategories();

    // @Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
    // public List<Person> find(@Param("lastName") String lastName);
}