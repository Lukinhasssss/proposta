package br.com.lukinhasssss.proposta.exceptions

class AlreadyExistsException(
    val field: String,
    override val message: String
) : RuntimeException()