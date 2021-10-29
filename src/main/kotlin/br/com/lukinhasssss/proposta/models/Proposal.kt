package br.com.lukinhasssss.proposta.models

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_proposal")
data class Proposal(

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = null,

    val name: String,

    val email: String,

    val document: String,

    val salary: BigDecimal,

    val address: String

)
