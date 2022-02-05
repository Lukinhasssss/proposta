package br.com.lukinhasssss.proposta.models

import br.com.lukinhasssss.proposta.models.enums.ProposalStatus
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_proposal")
data class Proposal(

    @Id
    val id: String,

    val name: String,

    val email: String,

    val document: String,

    val salary: BigDecimal,

    val address: String,

    val cardNumber: String? = null,

    @Enumerated(value = EnumType.STRING)
    val proposalStatus: ProposalStatus? = null

)
