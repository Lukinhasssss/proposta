package br.com.lukinhasssss.proposta.usecases.port

import br.com.lukinhasssss.proposta.models.Proposal

interface AnalyzeProposalUC {

    fun analyzeProposal(proposal: Proposal): Proposal

}