package br.gov.fatec.comparador.repository

import br.gov.fatec.comparador.domain.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long> {
    fun existsByNameIgnoreCase(name: String): Boolean
}

