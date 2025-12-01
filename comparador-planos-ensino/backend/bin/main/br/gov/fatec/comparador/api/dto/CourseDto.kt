package br.gov.fatec.comparador.api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CourseRequest(
    @field:NotBlank(message = "O nome do curso é obrigatório.")
    @field:Size(max = 255, message = "O nome do curso deve ter no máximo 255 caracteres.")
    val name: String,

    @field:Size(max = 2000, message = "A descrição do curso deve ter no máximo 2000 caracteres.")
    val description: String? = null
)

data class CourseResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val disciplines: List<DisciplineResponse> = emptyList()
)

