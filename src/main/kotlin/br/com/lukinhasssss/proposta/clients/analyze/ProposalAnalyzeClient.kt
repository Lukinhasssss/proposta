package br.com.lukinhasssss.proposta.clients.analyze

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "solicitationAnalyze", url = "\${clients.analyze.url}")
interface ProposalAnalyzeClient {

    @PostMapping
    fun analyzeProposal(
        @RequestBody solicitation: ProposalAnalyzeRequest
    ): ResponseEntity<ProposalAnalyzeResponse>

}