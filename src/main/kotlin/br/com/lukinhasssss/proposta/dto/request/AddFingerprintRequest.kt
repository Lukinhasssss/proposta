package br.com.lukinhasssss.proposta.dto.request

import br.com.lukinhasssss.proposta.models.Fingerprint
import br.com.lukinhasssss.proposta.validations.Base64
import java.util.*
import javax.validation.constraints.NotBlank

data class AddFingerprintRequest(

    @field:NotBlank(message = "Required field")
    @field:Base64(domainClass = "Fingerprint", fieldName = "fingerprint", message = "Fingerprint must be base64 encoded")
    val fingerprint: String

) {

    fun toModel(cardId: String): Fingerprint {
        return Fingerprint(
            id = UUID.randomUUID().toString(),
            fingerprint = this.fingerprint,
            cardId = cardId
        )
    }

}
