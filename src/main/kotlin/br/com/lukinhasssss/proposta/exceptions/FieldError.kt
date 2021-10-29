package br.com.lukinhasssss.proposta.exceptions

data class FieldError(
    val field: String,
    val message: String
)