package br.com.lukinhasssss.proposta.dto.request

import br.com.lukinhasssss.proposta.models.Proposal
import br.com.lukinhasssss.proposta.validations.ValidDocument
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class NewProposalRequest(

    @NotEmpty(message = "Required field")
    @Size(max = 50, message = "Must be no longer than 50 characters")
    val name: String,

    @NotEmpty(message = "Required field")
    @Size(max = 50, message = "Must be no longer than 50 characters")
    val email: String,

    @ValidDocument
    @Size(max = 14, message = "Must be no longer than 14 characters")
    val document: String,

    @NotNull(message = "Required field")
    @Positive(message = "Salary cannot be negative")
    val salary: BigDecimal,

    @NotEmpty(message = "Required field")
    @Size(max = 150, message = "Must be no longer than 150 characters")
    val address: String

) {

    fun toModel(): Proposal {
        return Proposal(
            name = name,
            email = email,
            document = document,
            salary = salary,
            address = address
        )
    }

}
