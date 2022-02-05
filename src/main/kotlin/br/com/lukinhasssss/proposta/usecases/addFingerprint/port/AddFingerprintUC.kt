package br.com.lukinhasssss.proposta.usecases.addFingerprint.port

import br.com.lukinhasssss.proposta.models.Fingerprint

interface AddFingerprintUC {
    fun execute(fingerprint: Fingerprint): Fingerprint
}
