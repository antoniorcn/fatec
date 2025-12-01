package br.gov.fatec.comparador.api.controller

import br.gov.fatec.comparador.api.dto.CourseRequest
import br.gov.fatec.comparador.api.dto.CourseResponse
import br.gov.fatec.comparador.service.CourseService
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/courses")
class CourseController(
    private val courseService: CourseService
) {

    @PostMapping
    fun createCourse(@Valid @RequestBody request: CourseRequest): ResponseEntity<CourseResponse> {
        val response = courseService.createCourse(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun listCourses(): List<CourseResponse> = courseService.listCourses()

    @GetMapping("/{id}")
    fun findCourse(@PathVariable id: Long): CourseResponse = courseService.findCourse(id)

    @PutMapping("/{id}")
    fun updateCourse(
        @PathVariable id: Long,
        @Valid @RequestBody request: CourseRequest
    ): CourseResponse = courseService.updateCourse(id, request)

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable id: Long): ResponseEntity<Void> {
        courseService.deleteCourse(id)
        return ResponseEntity.noContent().build()
    }
}

