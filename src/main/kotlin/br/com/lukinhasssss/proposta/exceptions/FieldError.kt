package br.com.lukinhasssss.proposta.exceptions

import com.fasterxml.jackson.annotation.JsonInclude

data class FieldError(

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val field: String?,
    val message: String
)