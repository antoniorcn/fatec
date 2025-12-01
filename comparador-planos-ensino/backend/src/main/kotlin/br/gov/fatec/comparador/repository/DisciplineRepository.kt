package br.gov.fatec.comparador.repository

import br.gov.fatec.comparador.domain.Discipline
import org.springframework.data.jpa.repository.JpaRepository

interface DisciplineRepository : JpaRepository<Discipline, Long> {
    fun findAllByCourseId(courseId: Long): List<Discipline>
}

