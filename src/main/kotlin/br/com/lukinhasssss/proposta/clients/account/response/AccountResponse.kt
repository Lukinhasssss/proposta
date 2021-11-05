package br.com.lukinhasssss.proposta.clients.account.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountResponse(

    @JsonProperty(value = "id")
    val cardNumber: String,

    @JsonProperty(value = "idProposta")
    val proposalId: String
)