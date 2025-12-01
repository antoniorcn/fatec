package br.gov.fatec.comparador.api.dto

data class DisciplineRelationView(
    val disciplineAId: Long,
    val disciplineATitle: String,
    val disciplineBId: Long,
    val disciplineBTitle: String,
    val similarityScore: Double,
    val sharedKeywords: Set<String>
)

data class CourseComparisonResponse(
    val courseAId: Long,
    val courseBId: Long,
    val overlapScore: Double,
    val sharedKeywords: Set<String>,
    val exclusiveToCourseA: Set<String>,
    val exclusiveToCourseB: Set<String>,
    val relatedDisciplines: List<DisciplineRelationView>
)

