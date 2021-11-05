package br.com.lukinhasssss.proposta.usecases

import br.com.lukinhasssss.proposta.clients.analyze.ProposalAnalyzeClient
import br.com.lukinhasssss.proposta.clients.analyze.request.ProposalAnalyzeRequest
import br.com.lukinhasssss.proposta.clients.analyze.response.ProposalAnalyzeResponse
import br.com.lukinhasssss.proposta.exceptions.IntegrationErrorException
import br.com.lukinhasssss.proposta.models.Proposal
import br.com.lukinhasssss.proposta.usecases.analyzeProposal.AnalyzeProposalUCImpl
import feign.FeignException
import feign.Request
import feign.RequestTemplate
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.ResponseEntity
import java.math.BigDecimal
import java.util.*

internal class AnalyzeProposalUCImplTest {

    private val proposalAnalyzeClient = mockk<ProposalAnalyzeClient>()

    private val analizeProposalService = AnalyzeProposalUCImpl(proposalAnalyzeClient)

    @Test
    fun `should return an eligible proposal when proposal analyze client return status 201`() {
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

    @Test
    fun `should return a not eligible proposal when proposal analyze client return status 422`() {
        // Arrange
        val proposal = Proposal(
            id = UUID.randomUUID().toString(),
            name = "Monkey D. Luffy",
            email = "mugiwara@gmail.com",
            document = "73854034091",
            salary = BigDecimal("400000.00"),
            address = "Dressrosa"
        )

        val feignRequest = Request.create(Request.HttpMethod.POST, "http://localhost:9999/analise", hashMapOf(), null, RequestTemplate())

        every {
            proposalAnalyzeClient.analyzeProposal(ProposalAnalyzeRequest(proposal.id, proposal.name, proposal.document))
        } throws FeignException.UnprocessableEntity(null, feignRequest, null)

        // Act
        val response = analizeProposalService.analyzeProposal(proposal)

        // Assert
        with(response) {
            assertEquals("NAO_ELEGIVEL", proposalStatus?.name)
        }
    }

    @Test
    fun `should throw IntegrationErrorException when proposal analyze client return status 5xx`() {
        // Arrange
        val proposal = Proposal(
            id = UUID.randomUUID().toString(),
            name = "Monkey D. Luffy",
            email = "mugiwara@gmail.com",
            document = "73854034091",
            salary = BigDecimal("400000.00"),
            address = "Dressrosa"
        )

        val feignRequest = Request.create(Request.HttpMethod.POST, "http://localhost:9999/analise", hashMapOf(), null, RequestTemplate())

        every {
            proposalAnalyzeClient.analyzeProposal(ProposalAnalyzeRequest(proposal.id, proposal.name, proposal.document))
        } throws FeignException.InternalServerError(null, feignRequest, null)

        // Act - Assert
        assertThrows<IntegrationErrorException> {
            analizeProposalService.analyzeProposal(proposal)
        }
    }

}