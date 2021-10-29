package br.com.lukinhasssss.proposta.repositories

import br.com.lukinhasssss.proposta.models.Proposal
import org.springframework.data.jpa.repository.JpaRepository

interface ProposalRepository : JpaRepository<Proposal, String>
