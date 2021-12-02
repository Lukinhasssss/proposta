package br.com.lukinhasssss.proposta.models

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_fingerprint")
data class Fingerprint(

    @Id
    val id: String,

    val fingerprint: String,

    val cardId: String,

    val associationDate: LocalDateTime = LocalDateTime.now()

)
