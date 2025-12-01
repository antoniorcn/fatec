package br.gov.fatec.comparador.service

import br.gov.fatec.comparador.api.dto.DisciplineRequest
import br.gov.fatec.comparador.api.dto.DisciplineResponse
import br.gov.fatec.comparador.api.error.ResourceNotFoundException
import br.gov.fatec.comparador.domain.Discipline
import br.gov.fatec.comparador.repository.DisciplineRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class DisciplineService(
    private val disciplineRepository: DisciplineRepository,
    private val courseService: CourseService
) {

    @Transactional
    fun createDiscipline(request: DisciplineRequest): DisciplineResponse {
        val course = courseService.getCourseEntity(request.courseId)
        val normalizedKeywords = normalizeKeywords(request.keywords)

        val entity = Discipline(
            title = request.title.trim(),
            code = request.code?.trim(),
            syllabus = request.syllabus?.trim(),
            complementaryContent = request.complementaryContent?.trim(),
            keywords = normalizedKeywords.toMutableSet(),
            course = course
        )
        course.disciplines.add(entity)
        return disciplineRepository.save(entity).toResponse()
    }

    fun listAll(): List<DisciplineResponse> =
        disciplineRepository.findAll()
            .sortedBy { it.title.lowercase() }
            .map { it.toResponse() }

    fun listByCourse(courseId: Long): List<DisciplineResponse> =
        disciplineRepository.findAllByCourseId(courseId)
            .sortedBy { it.title.lowercase() }
            .map { it.toResponse() }

    fun findById(id: Long): DisciplineResponse = getDisciplineEntity(id).toResponse()

    @Transactional
    fun updateDiscipline(id: Long, request: DisciplineRequest): DisciplineResponse {
        val entity = getDisciplineEntity(id)
        val course = courseService.getCourseEntity(request.courseId)

        if (entity.course?.id != course.id) {
            entity.course?.disciplines?.removeIf { it.id == entity.id }
            course.disciplines.add(entity)
            entity.course = course
        }

        entity.updateContent(
            newTitle = request.title.trim(),
            newCode = request.code?.trim(),
            newSyllabus = request.syllabus?.trim(),
            newComplementaryContent = request.complementaryContent?.trim(),
            newKeywords = normalizeKeywords(request.keywords)
        )
        return entity.toResponse()
    }

    @Transactional
    fun deleteDiscipline(id: Long) {
        val entity = getDisciplineEntity(id)
        entity.course?.disciplines?.removeIf { it.id == entity.id }
        disciplineRepository.delete(entity)
    }

    fun getDisciplineEntity(id: Long): Discipline =
        disciplineRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Disciplina $id não encontrada.") }

    private fun normalizeKeywords(keywords: Collection<String>): Set<String> =
        keywords
            .mapNotNull { it.trim().lowercase().takeIf { trimmed -> trimmed.isNotEmpty() } }
            .toSet()
}

