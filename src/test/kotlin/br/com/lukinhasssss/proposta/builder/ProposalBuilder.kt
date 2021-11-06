package br.com.lukinhasssss.proposta.builder

import br.com.lukinhasssss.proposta.models.Proposal
import br.com.lukinhasssss.proposta.models.enums.ProposalStatus
import java.math.BigDecimal
import java.util.*

fun saveProposalBuilder(): Proposal {
    return Proposal(
        id = UUID.randomUUID().toString(),
        name = "Monkey D. Luffy",
        email = "mugiwara@gmail.com",
        document = "73854034091",
        salary = BigDecimal("400000.00"),
        address = "Dressrosa",
        proposalStatus = ProposalStatus.ELIGIBLE
    )
}

fun eligible(): Proposal {
    return Proposal(
        id = UUID.randomUUID().toString(),
        name = "Monkey D. Luffy",
        email = "mugiwara@gmail.com",
        document = "73854034091",
        salary = BigDecimal("400000.00"),
        address = "Dressrosa",
        proposalStatus = ProposalStatus.ELIGIBLE
    )
}

fun notEligible(): Proposal {
    return Proposal(
        id = UUID.randomUUID().toString(),
        name = "Monkey D. Luffy",
        email = "mugiwara@gmail.com",
        document = "73854034091",
        salary = BigDecimal("400000.00"),
        address = "Dressrosa",
        proposalStatus = ProposalStatus.NOT_ELIGIBLE
    )
}