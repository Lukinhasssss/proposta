package br.com.lukinhasssss.proposta.usecases

import br.com.lukinhasssss.proposta.clients.analyze.ProposalAnalyzeClient
import br.com.lukinhasssss.proposta.clients.analyze.ProposalAnalyzeRequest
import br.com.lukinhasssss.proposta.clients.analyze.ProposalAnalyzeResponse
import br.com.lukinhasssss.proposta.models.Proposal
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import java.math.BigDecimal
import java.util.*

internal class AnalyzeProposalUCImplTest {

    private val proposalAnalyzeClient = mockk<ProposalAnalyzeClient>()

    private val analizeProposalService = AnalyzeProposalUCImpl(proposalAnalyzeClient)

    @Test
    internal fun `should return an eligible proposal when proposal analyze client return status 201`() {
        // Arrange
        val proposal = Proposal(
            id = UUID.randomUUID().toString(),
            name = "Monkey D. Luffy",
            email = "mugiwara@gmail.com",
            document = "73854034091",
            salary = BigDecimal("400000.00"),
            address = "Dressrosa"
        )

        every {
            proposalAnalyzeClient.analyzeProposal(ProposalAnalyzeRequest(proposal.id, proposal.name, proposal.document))
        } answers { ResponseEntity.ok(ProposalAnalyzeResponse(solicitationResult = "SEM_RESTRICAO")) }

        // Act
        val response = analizeProposalService.analyzeProposal(proposal)

        // Assert
        with(response) {
            assertEquals("ELEGIVEL", proposalStatus?.name)
        }
    }
}