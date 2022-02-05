package br.com.lukinhasssss.proposta.usecases.trackProposalStatus

import br.com.lukinhasssss.proposta.dto.response.ProposalStatusResponse
import br.com.lukinhasssss.proposta.exceptions.NotFoundException
import br.com.lukinhasssss.proposta.models.enums.ProposalStatus
import br.com.lukinhasssss.proposta.repositories.ProposalRepository
import br.com.lukinhasssss.proposta.usecases.trackProposalStatus.port.TrackProposalStatusUC
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TrackProposalStatusUCImpl(
    private val proposalRepository: ProposalRepository
) : TrackProposalStatusUC {

    private val logger = LoggerFactory.getLogger(TrackProposalStatusUCImpl::class.java)

    override fun getProposalStatus(proposalId: String): ProposalStatusResponse {
        logger.info("Tracking proposal {}", proposalId)

        val proposal = proposalRepository.findById(proposalId)

        if (proposal.isEmpty)
            throw NotFoundException("Proposal $proposalId not found")

        logger.info("Tracking of proposal {} finished")
        return ProposalStatusResponse(proposalStatus = ProposalStatus.convertToResponse(proposal.get().proposalStatus!!.name))
    }
}
