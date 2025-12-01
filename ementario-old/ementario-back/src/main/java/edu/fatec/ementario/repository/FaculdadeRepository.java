package edu.fatec.ementario.repository;

import edu.fatec.ementario.model.Faculdade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaculdadeRepository extends JpaRepository<Faculdade, Long> {

//    @Query("SELECT c FROM Faculdade c WHERE c.categoriaPai is null")
//    List<Faculdade> retrieveMainCategories();

    // @Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
    // public List<Person> find(@Param("lastName") String lastName);
}