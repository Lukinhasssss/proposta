package br.com.lukinhasssss.proposta.controllers

import br.com.lukinhasssss.proposta.dto.response.ProposalStatusResponse
import br.com.lukinhasssss.proposta.usecases.trackProposalStatus.TrackProposalStatusUCImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/proposals")
class TrackProposalStatusController(
    private val trackProposalStatus: TrackProposalStatusUCImpl
) {

    @GetMapping("/{proposalId}")
    fun getStatusProposal(@PathVariable proposalId: String): ResponseEntity<ProposalStatusResponse> {
        val proposalStatus = trackProposalStatus.getProposalStatus(proposalId)
        return ResponseEntity.ok(proposalStatus)
    }

}