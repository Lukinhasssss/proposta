package br.com.lukinhasssss.proposta.dto.request

import br.com.lukinhasssss.proposta.models.Proposal
import br.com.lukinhasssss.proposta.validations.CheckIfAlreadyExists
import br.com.lukinhasssss.proposta.validations.ValidDocument
import java.math.BigDecimal
import javax.validation.constraints.*

data class NewProposalRequest(

    @field:NotBlank(message = "Required field")
    @field:Size(max = 50, message = "Must be no longer than 50 characters")
    val name: String,

    @field:NotBlank(message = "Required field")
    @field:Email(message = "Must be a valid email")
    @field:Size(max = 50, message = "Must be no longer than 50 characters")
    @CheckIfAlreadyExists(domainClass = "Proposal", fieldName = "email")
    val email: String,

    @field:ValidDocument
    @field:Pattern(regexp = "[0-9]+", message = "Document must have only numbers")
    @CheckIfAlreadyExists(domainClass = "Proposal", fieldName = "document")
    val document: String,

    @field:NotNull(message = "Required field")
    @field:Positive(message = "Salary cannot be zero or negative")
    val salary: BigDecimal,

    @field:NotBlank(message = "Required field")
    @field:Size(max = 150, message = "Must be no longer than 150 characters")
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

/*
    Regex para permitir somente números: "[0-9]+"
    Regex para remover caracteres especiais de CPF/CNPJ: "[^0-9]"
 */