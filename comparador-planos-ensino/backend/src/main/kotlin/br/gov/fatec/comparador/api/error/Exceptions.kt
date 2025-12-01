package br.gov.fatec.comparador.api.error

open class ResourceNotFoundException(message: String) : RuntimeException(message)

open class BusinessValidationException(message: String) : RuntimeException(message)

