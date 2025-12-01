package br.gov.fatec.comparador.service

import br.gov.fatec.comparador.api.dto.CourseComparisonResponse
import br.gov.fatec.comparador.api.dto.DisciplineRelationView
import br.gov.fatec.comparador.domain.Course
import br.gov.fatec.comparador.domain.Discipline
import org.springframework.stereotype.Service
import kotlin.math.round

@Service
class ComparisonService(
    private val courseService: CourseService
) {

    fun compareCourses(firstCourseId: Long, secondCourseId: Long): CourseComparisonResponse {
        val first = courseService.getCourseEntity(firstCourseId)
        val second = courseService.getCourseEntity(secondCourseId)

        val firstKeywords = aggregateCourseKeywords(first)
        val secondKeywords = aggregateCourseKeywords(second)

        val shared = firstKeywords.intersect(secondKeywords)
        val exclusiveA = firstKeywords.minus(shared)
        val exclusiveB = secondKeywords.minus(shared)

        val overlapScore = if (firstKeywords.isEmpty() && secondKeywords.isEmpty()) {
            0.0
        } else {
            val ratio = (shared.size * 2.0) / (firstKeywords.size + secondKeywords.size)
            round(ratio * 100) / 100.0
        }

        val relations = buildDisciplineRelations(first, second)

        return CourseComparisonResponse(
            courseAId = first.id,
            courseBId = second.id,
            overlapScore = overlapScore,
            sharedKeywords = shared,
            exclusiveToCourseA = exclusiveA,
            exclusiveToCourseB = exclusiveB,
            relatedDisciplines = relations
        )
    }

    private fun aggregateCourseKeywords(course: Course): Set<String> =
        course.disciplines
            .flatMap { aggregateDisciplineKeywords(it).toList() }
            .toSet()

    private fun aggregateDisciplineKeywords(discipline: Discipline): Set<String> {
        val tokens = mutableSetOf<String>()
        tokens += tokenize(discipline.title)
        tokens += tokenize(discipline.syllabus)
        tokens += tokenize(discipline.complementaryContent)
        tokens += discipline.keywords.map { it.lowercase() }
        return tokens.filter { it.length > 2 }.toSet()
    }

    private fun buildDisciplineRelations(
        first: Course,
        second: Course,
        threshold: Double = 0.25
    ): List<DisciplineRelationView> {
        val relations = mutableListOf<DisciplineRelationView>()
        for (disciplineA in first.disciplines) {
            val keywordsA = aggregateDisciplineKeywords(disciplineA)
            if (keywordsA.isEmpty()) continue

            var bestMatch: DisciplineRelationView? = null
            for (disciplineB in second.disciplines) {
                val keywordsB = aggregateDisciplineKeywords(disciplineB)
                if (keywordsB.isEmpty()) continue

                val shared = keywordsA.intersect(keywordsB)
                val union = keywordsA.union(keywordsB)
                val similarity = if (union.isEmpty()) 0.0 else shared.size.toDouble() / union.size.toDouble()

                if (similarity >= threshold) {
                    val rounded = round(similarity * 100) / 100.0
                    val relation = DisciplineRelationView(
                        disciplineAId = disciplineA.id,
                        disciplineATitle = disciplineA.title,
                        disciplineBId = disciplineB.id,
                        disciplineBTitle = disciplineB.title,
                        similarityScore = rounded,
                        sharedKeywords = shared
                    )
                    if (bestMatch == null || relation.similarityScore > bestMatch.similarityScore) {
                        bestMatch = relation
                    }
                }
            }
            bestMatch?.let { relations += it }
        }
        return relations.sortedByDescending { it.similarityScore }
    }

    private fun tokenize(text: String?): Set<String> =
        text
            ?.lowercase()
            ?.split(Regex("\\W+"))
            ?.mapNotNull { token ->
                val cleaned = token.trim()
                cleaned.takeIf { it.length > 2 }
            }
            ?.toSet()
            ?: emptySet()
}

