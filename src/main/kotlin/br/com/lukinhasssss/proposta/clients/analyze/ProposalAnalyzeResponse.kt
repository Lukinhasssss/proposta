package br.com.lukinhasssss.proposta.clients.analyze

import com.fasterxml.jackson.annotation.JsonProperty

data class ProposalAnalyzeResponse(

    @JsonProperty(value = "resultadoSolicitacao")
    val solicitationResult: String

)