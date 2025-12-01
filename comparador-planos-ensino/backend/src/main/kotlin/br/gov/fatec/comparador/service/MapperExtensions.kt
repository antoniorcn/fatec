package br.gov.fatec.comparador.service

import br.gov.fatec.comparador.api.dto.CourseResponse
import br.gov.fatec.comparador.api.dto.DisciplineResponse
import br.gov.fatec.comparador.domain.Course
import br.gov.fatec.comparador.domain.Discipline

fun Course.toResponse(includeDisciplines: Boolean = true): CourseResponse =
    CourseResponse(
        id = id,
        name = name,
        description = description,
        disciplines = if (includeDisciplines) {
            disciplines
                .sortedBy { it.title.lowercase() }
                .map { it.toResponse(includeCourse = false) }
        } else {
            emptyList()
        }
    )

fun Discipline.toResponse(includeCourse: Boolean = true): DisciplineResponse =
    DisciplineResponse(
        id = id,
        title = title,
        code = code,
        syllabus = syllabus,
        complementaryContent = complementaryContent,
        keywords = keywords.sorted().toSet(),
        courseId = if (includeCourse) course?.id else null
    )

