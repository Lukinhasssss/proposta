package br.com.lukinhasssss.proposta.controllers

import br.com.lukinhasssss.proposta.dto.request.NewProposalRequest
import br.com.lukinhasssss.proposta.repositories.ProposalRepository
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class NewProposalControllerTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    lateinit var repository: ProposalRepository

    @BeforeEach
    internal fun setUp() { repository.deleteAll() }

    @Test
    internal fun `should return 201 when request is valid`() {
        // Arrange
        val request = newProposalRequest

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(201)
            header("Location", matchesPattern("http://localhost:$port/v1/proposals/([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"))
        }
    }
    
    @Test
    internal fun `should return 400 with an error message when name is not provided`() {
        // Arrange
        val request = newProposalRequest.copy(name = "")
    
        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 with an error message when email is not provided`() {
        // Arrange
        val request = newProposalRequest.copy(email = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 with an error message when email is invalid`() {
        // Arrange
        val request = newProposalRequest.copy(email = "srgarnetn")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 422 with an error message when already exists a proposal with provided email`() {
        // Arrange
        repository.save(newProposalRequest.toModel())
        val request = newProposalRequest.copy(document = "51582582017")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(422)
        }
    }

    @Test
    internal fun `should return 400 with an error message when document (CPF) is invalid`() {
        // Arrange
        val request = newProposalRequest.copy(document = "12345678912")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 with an error message when document (CNPJ) is invalid`() {
        // Arrange
        val request = newProposalRequest.copy(document = "12345678912123")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 with an error message when document is valid but has special characters`() {
        // Arrange
        val request = newProposalRequest.copy(document = "123.456.789-12")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 422 with an error message when already exists a proposal with provided document`() {
        // Arrange
        repository.save(newProposalRequest.toModel())
        val request = newProposalRequest.copy(email = "luffy@gmail.com")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
            post("/v1/proposals")
        } Then {
            statusCode(422)
        }
    }

    @Test
    internal fun `should return 400 with an error message when salary is negative or zero`() {
        // Arrange
        val request = newProposalRequest.copy(salary = BigDecimal("0.0"))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 with an error message when address is not provided`() {
        // Arrange
        val request = newProposalRequest.copy(address = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/v1/proposals")
        } Then {
            statusCode(400)
        }
    }

    private val newProposalRequest = NewProposalRequest(
        name = "Monkey D. Luffy",
        email = "mugiwara@gmail.com",
        document = "73854034091",
        salary = BigDecimal("400000.00"),
        address = "Dressrosa"
    )

    /*
        Exemplos de validações:

        statusCode(400)
        body("timestamp", notNullValue())
        body("status", IsEqual(400))
        body("path", IsEqual("/api/v1/authors"))
        body("messages.size()", IsEqual(1))
        body("messages.get(0).fieldName", IsEqual("name"))
        body("messages.get(0).message", IsEqual("Required field"))
        body("messages.findAll { it.message == 'Required field' }.size()", IsEqual(1))
        body("messages.findAll { it.message == 'Invalid email' }.size()", IsEqual(1))
     */
}