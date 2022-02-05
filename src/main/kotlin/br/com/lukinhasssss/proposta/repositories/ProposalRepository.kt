package br.com.lukinhasssss.proposta.repositories

import br.com.lukinhasssss.proposta.models.Proposal
import br.com.lukinhasssss.proposta.models.enums.ProposalStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ProposalRepository : JpaRepository<Proposal, String> {

    fun findByProposalStatusAndCardNumberIsNull(proposalStatus: ProposalStatus): Set<Proposal>
}
