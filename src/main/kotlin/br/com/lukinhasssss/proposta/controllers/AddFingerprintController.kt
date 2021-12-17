package br.com.lukinhasssss.proposta.controllers

import br.com.lukinhasssss.proposta.dto.request.AddFingerprintRequest
import br.com.lukinhasssss.proposta.usecases.addFingerprint.port.AddFingerprintUC
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/v1/cards/{cardId}/fingerprints")
class AddFingerprintController(
    private val addFingerprintUC: AddFingerprintUC
) {

    private val logger = LoggerFactory.getLogger(AddFingerprintController::class.java)

    @PostMapping
    fun addFingerprint(@PathVariable cardId: String, @Valid @RequestBody request: AddFingerprintRequest): ResponseEntity<Unit> {
        logger.info("Starting association of fingerprint to card $cardId")

        val fingerprint = addFingerprintUC.execute(request.toModel(cardId))
        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{fingerprint}").buildAndExpand(fingerprint.id).toUri()

        logger.info("Ending association of fingerprint to card $cardId")
        return ResponseEntity.created(uri).build()
    }

}