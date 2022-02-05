package br.com.lukinhasssss.proposta.exceptions

import java.time.LocalDateTime

data class AlreadyExistsExceptionMessage(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val path: String,
    val message: FieldError
)
