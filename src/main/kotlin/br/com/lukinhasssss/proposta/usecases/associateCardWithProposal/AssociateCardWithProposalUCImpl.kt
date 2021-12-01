package br.com.lukinhasssss.proposta.usecases.associateCardWithProposal

import br.com.lukinhasssss.proposta.clients.account.AccountClient
import br.com.lukinhasssss.proposta.exceptions.IntegrationErrorException
import br.com.lukinhasssss.proposta.models.enums.ProposalStatus
import br.com.lukinhasssss.proposta.repositories.ProposalRepository
import br.com.lukinhasssss.proposta.usecases.associateCardWithProposal.port.AssociateCardWithProposalUC
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableAsync
@EnableScheduling
class AssociateCardWithProposalUCImpl(
    private val proposalRepository: ProposalRepository,
    private val accountClient: AccountClient
): AssociateCardWithProposalUC {

    private val logger = LoggerFactory.getLogger(AssociateCardWithProposalUCImpl::class.java)

    @Scheduled(fixedDelay = 300000)
    override fun associate() {
        try {
            logger.info("Starting card association with proposal")
            val accounts = accountClient.getAllAccounts().body

            logger.info("Searching for proposals without associated card")
            val proposals = proposalRepository.findByProposalStatusAndCardNumberIsNull(ProposalStatus.ELIGIBLE)
            logger.info("${proposals.size} proposals found")

            if (proposals.isNotEmpty()) {
                proposals.forEach { proposal ->
                    logger.info("Associating card with proposal {}", proposal.id)
                    val account = accounts?.find { it.proposalId == proposal.id } ?: return
                    proposalRepository.save(proposal.copy(cardNumber = account.cardNumber))
                    logger.info("Card successfully associated with proposal {}", proposal.id)
                }
            }
        }
        catch (ex: FeignException) {
            logger.error("Error to send request for analyze api, url: {}, status: {}, body: {}", ex.request().url(), ex.status(), ex.responseBody())
            throw IntegrationErrorException("Error to send request for analyze api")
        }
    }

}