package br.com.lukinhasssss.proposta.exceptions

import java.time.LocalDateTime

data class StandardErrorMessage(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val path: String,
    val messages: Set<FieldError> = setOf()
)