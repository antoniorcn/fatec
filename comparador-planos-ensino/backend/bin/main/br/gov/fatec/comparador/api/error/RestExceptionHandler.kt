package br.gov.fatec.comparador.api.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.OffsetDateTime

data class ApiError(
    val status: Int,
    val error: String,
    val message: String?,
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val details: Map<String, String?>? = null
)

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<ApiError> =
        buildResponse(HttpStatus.NOT_FOUND, ex)

    @ExceptionHandler(BusinessValidationException::class)
    fun handleBusinessValidation(ex: BusinessValidationException): ResponseEntity<ApiError> =
        buildResponse(HttpStatus.BAD_REQUEST, ex)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ApiError> {
        val fieldErrors = ex.bindingResult
            .allErrors
            .filterIsInstance<FieldError>()
            .associate { it.field to (it.defaultMessage ?: "Valor inválido.") }

        val body = ApiError(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "Erro de validação.",
            details = fieldErrors
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ApiError> =
        buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, "Erro interno não esperado.")

    private fun buildResponse(
        status: HttpStatus,
        ex: Exception,
        customMessage: String? = null
    ): ResponseEntity<ApiError> {
        val body = ApiError(
            status = status.value(),
            error = status.reasonPhrase,
            message = customMessage ?: ex.message
        )
        return ResponseEntity(body, status)
    }
}

