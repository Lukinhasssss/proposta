package br.com.lukinhasssss.proposta.repositories

import br.com.lukinhasssss.proposta.models.Fingerprint
import org.springframework.data.jpa.repository.JpaRepository

interface FingerprintRepository : JpaRepository<Fingerprint, String>