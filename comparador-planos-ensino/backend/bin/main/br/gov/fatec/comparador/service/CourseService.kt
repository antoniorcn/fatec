package br.gov.fatec.comparador.service

import br.gov.fatec.comparador.api.dto.CourseRequest
import br.gov.fatec.comparador.api.dto.CourseResponse
import br.gov.fatec.comparador.api.error.BusinessValidationException
import br.gov.fatec.comparador.api.error.ResourceNotFoundException
import br.gov.fatec.comparador.domain.Course
import br.gov.fatec.comparador.repository.CourseRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val courseRepository: CourseRepository
) {

    @Transactional
    fun createCourse(request: CourseRequest): CourseResponse {
        val normalizedName = request.name.trim()
        if (courseRepository.existsByNameIgnoreCase(normalizedName)) {
            throw BusinessValidationException("Já existe um curso com o nome '${request.name}'.")
        }
        val entity = Course(
            name = normalizedName,
            description = request.description?.trim()
        )
        return courseRepository.save(entity).toResponse()
    }

    fun listCourses(): List<CourseResponse> =
        courseRepository.findAll()
            .sortedBy { it.name.lowercase() }
            .map { it.toResponse() }

    fun findCourse(id: Long): CourseResponse = getCourseEntity(id).toResponse()

    @Transactional
    fun updateCourse(id: Long, request: CourseRequest): CourseResponse {
        val entity = getCourseEntity(id)
        val normalizedName = request.name.trim()

        if (!entity.name.equals(normalizedName, ignoreCase = true) &&
            courseRepository.existsByNameIgnoreCase(normalizedName)
        ) {
            throw BusinessValidationException("Já existe um curso com o nome '${request.name}'.")
        }

        entity.updateBasicData(normalizedName, request.description?.trim())
        return entity.toResponse()
    }

    @Transactional
    fun deleteCourse(id: Long) {
        val entity = getCourseEntity(id)
        courseRepository.delete(entity)
    }

    fun getCourseEntity(id: Long): Course =
        courseRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Curso $id não encontrado.") }
}

