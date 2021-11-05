package br.com.lukinhasssss.proposta.usecases.analyzeProposal.port

import br.com.lukinhasssss.proposta.models.Proposal

interface AnalyzeProposalUC {

    fun analyzeProposal(proposal: Proposal): Proposal

}