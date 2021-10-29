package br.com.lukinhasssss.proposta.controllers

import br.com.lukinhasssss.proposta.dto.request.NewProposalRequest
import br.com.lukinhasssss.proposta.repositories.ProposalRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/proposals")
class NewProposalController(
    private val proposalRepository: ProposalRepository
) {

    private val logger = LoggerFactory.getLogger(NewProposalController::class.java)

    @PostMapping
    @Transactional
    fun create(@Valid @RequestBody request: NewProposalRequest): ResponseEntity<Unit> {
        logger.info("Receiving new proposal of document={} and salary={}", request.document, request.salary)

        val proposal = proposalRepository.save(request.toModel())
        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposal.id).toUri()

        logger.info("Proposal created with success, id: {}", proposal.id)
        return ResponseEntity.created(uri).build()
    }

}