package br.gov.fatec.comparador.api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class DisciplineRequest(
    @field:NotBlank(message = "O título da disciplina é obrigatório.")
    @field:Size(max = 255, message = "O título da disciplina deve ter no máximo 255 caracteres.")
    val title: String,

    @field:Size(max = 120, message = "O código da disciplina deve ter no máximo 120 caracteres.")
    val code: String? = null,

    @field:Size(max = 4000, message = "A ementa deve ter no máximo 4000 caracteres.")
    val syllabus: String? = null,

    @field:Size(max = 4000, message = "O conteúdo complementar deve ter no máximo 4000 caracteres.")
    val complementaryContent: String? = null,

    val keywords: Set<String> = emptySet(),

    @field:NotNull(message = "O identificador do curso é obrigatório.")
    val courseId: Long
)

data class DisciplineResponse(
    val id: Long,
    val title: String,
    val code: String?,
    val syllabus: String?,
    val complementaryContent: String?,
    val keywords: Set<String>,
    val courseId: Long?
)

