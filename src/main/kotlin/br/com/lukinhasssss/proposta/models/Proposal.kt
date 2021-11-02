package br.com.lukinhasssss.proposta.models

import br.com.lukinhasssss.proposta.models.enums.ProposalStatus
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import javax.persistence.*

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

    @Enumerated(value = EnumType.STRING)
    val proposalStatus: ProposalStatus? = null

)
