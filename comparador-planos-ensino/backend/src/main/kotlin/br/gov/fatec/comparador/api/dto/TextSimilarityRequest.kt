package br.gov.fatec.comparador.api.dto

import jakarta.validation.constraints.NotBlank

data class TextSimilarityRequest(
    @field:NotBlank
    val textA: String,
    @field:NotBlank
    val textB: String
)
