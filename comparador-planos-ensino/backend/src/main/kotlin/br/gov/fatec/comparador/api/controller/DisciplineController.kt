package br.gov.fatec.comparador.api.controller

import br.gov.fatec.comparador.api.dto.DisciplineRequest
import br.gov.fatec.comparador.api.dto.DisciplineResponse
import br.gov.fatec.comparador.service.DisciplineService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/disciplines")
class DisciplineController(
    private val disciplineService: DisciplineService
) {

    @PostMapping
    fun createDiscipline(@Valid @RequestBody request: DisciplineRequest): ResponseEntity<DisciplineResponse> {
        val response = disciplineService.createDiscipline(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun listDisciplines(@RequestParam(required = false) courseId: Long?): List<DisciplineResponse> =
        if (courseId != null) {
            disciplineService.listByCourse(courseId)
        } else {
            disciplineService.listAll()
        }

    @GetMapping("/{id}")
    fun findDiscipline(@PathVariable id: Long): DisciplineResponse = disciplineService.findById(id)

    @PutMapping("/{id}")
    fun updateDiscipline(
        @PathVariable id: Long,
        @Valid @RequestBody request: DisciplineRequest
    ): DisciplineResponse = disciplineService.updateDiscipline(id, request)

    @DeleteMapping("/{id}")
    fun deleteDiscipline(@PathVariable id: Long): ResponseEntity<Void> {
        disciplineService.deleteDiscipline(id)
        return ResponseEntity.noContent().build()
    }
}

