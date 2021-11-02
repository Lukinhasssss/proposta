package br.com.lukinhasssss.proposta.usecases

import br.com.lukinhasssss.proposta.clients.analyze.ProposalAnalyzeClient
import br.com.lukinhasssss.proposta.clients.analyze.ProposalAnalyzeRequest
import br.com.lukinhasssss.proposta.exceptions.IntegrationErrorException
import br.com.lukinhasssss.proposta.models.Proposal
import br.com.lukinhasssss.proposta.models.enums.ProposalStatus
import br.com.lukinhasssss.proposta.usecases.port.AnalyzeProposalUC
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AnalyzeProposalUCImpl(
    private val proposalAnalyzeClient: ProposalAnalyzeClient
) : AnalyzeProposalUC {

    private val logger = LoggerFactory.getLogger(AnalyzeProposalUCImpl::class.java)

    override fun analyzeProposal(proposal: Proposal): Proposal {
        val proposalAnalyzeRequest = ProposalAnalyzeRequest(
            proposalId = proposal.id,
            name = proposal.name,
            document = proposal.document
        )

        logger.info("Sending request for analyze api, body: {}", proposalAnalyzeRequest)

        try {
            val proposalAnalyzeResponse = proposalAnalyzeClient.analyzeProposal(proposalAnalyzeRequest).body

            logger.info("Finish request for analyze api")
            return proposal.copy(proposalStatus = ProposalStatus.convert(proposalAnalyzeResponse!!.solicitationResult))
        }
        catch (ex: FeignException) {
            if (ex.status() == 422) {
                logger.info("Finish request for analyze api")
                return proposal.copy(proposalStatus = ProposalStatus.convert("COM_RESTRICAO"))
            }

            logger.error("Error to send request for anaçyze api, status: {}, body: {}", ex.status(), ex.responseBody())
            throw IntegrationErrorException("Error to send request for analyze api")
        }
    }

}