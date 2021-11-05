package br.com.lukinhasssss.proposta.clients.analyze.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ProposalAnalyzeRequest(

    @JsonProperty(value = "idProposta")
    val proposalId: String,

    @JsonProperty(value = "nome")
    val name: String,

    @JsonProperty(value = "documento")
    val document: String

)