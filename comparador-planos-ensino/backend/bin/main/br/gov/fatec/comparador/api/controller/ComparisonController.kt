package br.gov.fatec.comparador.api.controller

import br.gov.fatec.comparador.api.dto.CourseComparisonResponse
import br.gov.fatec.comparador.api.dto.TextSimilarityRequest
import br.gov.fatec.comparador.api.dto.TextSimilarityResponse
import br.gov.fatec.comparador.service.ComparisonService
import br.gov.fatec.comparador.service.Word2VecTextSimilarityService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/comparisons")
class ComparisonController(
    private val comparisonService: ComparisonService,
    private val textSimilarityService: Word2VecTextSimilarityService
) {

    @GetMapping("/courses/{courseA}/vs/{courseB}")
    fun compareCourses(
        @PathVariable courseA: Long,
        @PathVariable courseB: Long
    ): CourseComparisonResponse =
        comparisonService.compareCourses(courseA, courseB)

    @PostMapping("/texts/similarity")
    fun compareTexts(
        @Valid @RequestBody request: TextSimilarityRequest
    ): TextSimilarityResponse =
        TextSimilarityResponse(
            similarityScore = textSimilarityService.similarity(request.textA, request.textB)
        )
}
