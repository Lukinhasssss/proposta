package br.com.lukinhasssss.proposta.usecases.addFingerprint

import br.com.lukinhasssss.proposta.clients.account.AccountClient
import br.com.lukinhasssss.proposta.exceptions.IntegrationErrorException
import br.com.lukinhasssss.proposta.exceptions.NotFoundException
import br.com.lukinhasssss.proposta.models.Fingerprint
import br.com.lukinhasssss.proposta.repositories.FingerprintRepository
import br.com.lukinhasssss.proposta.usecases.addFingerprint.port.AddFingerprintUC
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AddFingerprintUCImpl(
    private val fingerprintRepository: FingerprintRepository,
    private val accountClient: AccountClient
) : AddFingerprintUC {

    private val logger = LoggerFactory.getLogger(AddFingerprintUCImpl::class.java)

    override fun execute(fingerprint: Fingerprint): Fingerprint {
        try {
            logger.info("Sending request for account client to verify if card {} exists", fingerprint.cardId)
            accountClient.findCardById(fingerprint.cardId)
            logger.info("Card {} found", fingerprint.cardId)

            return fingerprintRepository.save(fingerprint)
        } catch (exception: FeignException.FeignClientException) {
            if (exception.status() == 404) {
                logger.error("Card {} not found")
                throw NotFoundException("There is no card with the entered id")
            }

            logger.error("Error to send request for account api, status: {}, body: {}", exception.status(), exception.responseBody())
            throw IntegrationErrorException("Error to send request for account api")
        }
    }

}