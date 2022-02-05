package br.com.lukinhasssss.proposta.usecases.trackProposalStatus.port

import br.com.lukinhasssss.proposta.dto.response.ProposalStatusResponse

interface TrackProposalStatusUC {
    fun getProposalStatus(proposalId: String): ProposalStatusResponse
}
